package cn.com.xcsa.gateway.controller;


import cn.com.xcsa.api.exception.ApiRuntimeException;
import cn.com.xcsa.api.framework.redis.RedisCache;
import cn.com.xcsa.api.util.InfoCode;
import cn.com.xcsa.gateway.service.CaptchaService;
import cn.com.xcsa.gateway.util.Constants;
import cn.com.xcsa.gateway.util.PictureUtils;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Slf4j
@RestController
@RequestMapping("/api/v1/captcha")
public class CaptchaController {


    @Resource
    private CaptchaService captchaService;

    @Resource
    private RedisCache redisCache;

    private static final String BASE64_PREFIX = "data:image/png;base64,";

    //1小时转换成秒
    private static final long HOUR_IN_SECONDS = 3600;

    //同一手机号一小时限制发送次数
    private static final Integer ONE_PHONE_SEND_NUM = 5;
    //同一ip一小时限制发送次数
    private static final Integer ONE_IP_SEND_NUM = 100;
    //参数类型手机号
    private static final String TYPE_PHONE = "phone";
    //参数类型ip
    private static final String TYPE_IP = "ip";

    private static final Integer IMAGE_COUNT = 3;

    private static final Integer TIME_OUT = 5;

    private static final Integer CODE_TIME_OUT = 30; //验证码超时时间


    /**
     * 生成滑动验证码.
     * @return 返回图片信息.
     */
    @GetMapping("/generate")
    public Object generate() {
        try {
            // 切图
            Map<String, Object> pictureMap =
                    PictureUtils.pictureTemplatesCut(IMAGE_COUNT, "jpg");
            // 获取参数
            String bigPic = BASE64_PREFIX
                    + Base64.encodeBase64String((byte[]) pictureMap.get("bigPic"));
            String smallPic =
                    BASE64_PREFIX + Base64.encodeBase64String((byte[]) pictureMap.get("smallPic"));
            String axisX = String.valueOf(pictureMap.get("axisX"));
            String axisY = String.valueOf(pictureMap.get("axisY"));
            Map<String, Object> result = Maps.newHashMap();
            String token = SecureUtil.md5("" + System.currentTimeMillis());
            String key = getTokenKey(token);
            redisCache.setCacheObject(key, axisX, TIME_OUT, TimeUnit.MINUTES);
            result.put("token", token);
            result.put("axisY", axisY);
            result.put("background", BASE64_PREFIX + bigPic);
            result.put("slider", BASE64_PREFIX + smallPic);
            return result;
        } catch (ApiRuntimeException e) {
            throw e;
        } catch (Exception e) {
            log.error("获取图片验证失败.", e);
        }
        throw new ApiRuntimeException(InfoCode.SERVICE_UNAVAILABLE);
    }

    /**
     * 验证验证码值是否正确.
     * @param token
     * @param axisX
     * @return boolean.
     */
    @PostMapping("/check")
    public Object check(String token, String axisX) {
        if (StrUtil.isBlankIfStr(token) || StrUtil.isBlankIfStr(axisX)) {
            throw new ApiRuntimeException(InfoCode.PARAMS_ERROR);
        }
        String key = getTokenKey(token);
        if (!redisCache.hasKey(key)) {
            throw new ApiRuntimeException(InfoCode.PARAMS_ERROR, "验证码不正确或已失效");
        }
        String xv = redisCache.getCacheObject(key);
        if (axisX.equalsIgnoreCase(xv)) {
            throw new ApiRuntimeException(InfoCode.PARAMS_ERROR, "验证码不正确或已失效");
        }

        token = SecureUtil.md5(UUID.fastUUID().toString());
        key = Constants.genTokenKey(Constants.BUSINESS_AUTH, Constants.CAPTCHA, token);
        redisCache.setCacheObject(key, token, CODE_TIME_OUT, TimeUnit.MINUTES);
        return true;
    }


    /**
     * 发送短信验证码.
     * @param request
     * @param phone
     * @param type
     * @return 返回发送短信处理结果.
     */
    @GetMapping("/sms")
    public Object sms(HttpServletRequest request, String phone, String type) {
        //检查手机号是否合法
        if (!Validator.isMobile(phone)) {
            throw new ApiRuntimeException(InfoCode.PHONE_TYPE_ERROR);
        }
        // 先检查手机号限制
        if (!checkPhoneLimit(phone)) {
            throw new ApiRuntimeException(InfoCode.SEND_TOO_MANY, "发送短信过于频繁");
        }
        String clientIP = ServletUtil.getClientIP(request);
        // 再检查IP限制
        if (!checkIpLimit(clientIP)) {
            throw new ApiRuntimeException(InfoCode.SEND_TOO_MANY, "发送短信过于频繁");
        }
        return captchaService.sms(clientIP, phone, type);
    }

    /**
     * 发送邮箱验证码.
     *
     * @param email
     * @param type
     * @return 发送邮件处理结果.
     */
    @GetMapping("/email")
    public Object email(String email, String type) {
        if (StrUtil.isEmptyIfStr(email) || StrUtil.isEmptyIfStr(type)) {
            throw new ApiRuntimeException(InfoCode.PARAMS_ERROR);
        }
        if (!Validator.isEmail(email)) {
            throw new ApiRuntimeException(InfoCode.PARAMS_ERROR, "邮箱地址不合法");
        }
        return captchaService.sendEmail(email, type);
    }


    private boolean checkPhoneLimit(String phoneNumber) {
        // 检查手机号是否已达到限制
        String phoneKey = captchaService.getKey(phoneNumber, TYPE_PHONE);
        long count = redisCache.increment(phoneKey);
        if (count > ONE_PHONE_SEND_NUM) {
            redisCache.expire(phoneKey, HOUR_IN_SECONDS);
            return false;
        }
        return true;
    }

    private boolean checkIpLimit(String ipAddress) {
        // 检查IP地址是否已达到限制
        String ipKey = captchaService.getKey(ipAddress, TYPE_IP);
        long count = redisCache.increment(ipKey);
        if (count > ONE_IP_SEND_NUM) {
            redisCache.expire(ipKey, HOUR_IN_SECONDS);
            return false;
        }
        return true;
    }

    private String getTokenKey(String token) {
        String key = "captcha:info:" + token;
        return key;
    }







}
