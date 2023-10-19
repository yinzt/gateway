package cn.com.xcsa.gateway.service.impl;

import cn.com.xcsa.api.exception.ApiRuntimeException;
import cn.com.xcsa.api.framework.redis.RedisCache;
import cn.com.xcsa.api.util.InfoCode;
import cn.com.xcsa.framework.extra.MailSender;
import cn.com.xcsa.gateway.domain.po.ResultMap;
import cn.com.xcsa.gateway.service.CaptchaService;
import cn.com.xcsa.gateway.util.Constants;
import cn.hutool.core.util.RandomUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.concurrent.TimeUnit;



@Service
public class CaptchaServiceImpl implements CaptchaService {

    @Resource
    private RedisCache redisCache;

    //邮箱验证码过期时间
    private static final Integer EMAIL_CODE_TIMEOUT_MINUTES = 5;

    //短信验证码过期时间
    private static final Integer SMS_CODE_TIMEOUT_MINUTES = 5;

    //1小时转换成秒
    private static final long HOUR_IN_SECONDS = 3600;

    private static final String TYPE_LOGIN = "login";

    private static final String TYPE_AUTH = "auth";

    private static final String TYPE_PHONE = "phone";
    private static final String TYPE_IP = "ip";

    private static final Integer RANDOM_LENGTH = 6;

    /**
     * 发送短信.
     * @param clientIP
     * @param phone
     * @param type
     * @return 关键信息.
     */
    @Override
    public Object sms(String clientIP, String phone, String type) {
        String message = ""; //替换为华为短信模板号
        if (type.equals(TYPE_LOGIN)) { //发送登陆的短信模板
            //TODO
            message = "login_template";
        } else if (type.equals(TYPE_AUTH)) { //发送验证的短信模板
            //TODO
            message = "auth_template";
        }
        //生成${RANDOM_LENGTH}位随机验证码
        String randomCode = RandomUtil.randomNumbers(RANDOM_LENGTH);
        // 调用华为短信接口发送短信
        boolean flag = sendSmsToHuawei(phone, message, randomCode);
        if (!flag) {
            throw new ApiRuntimeException(InfoCode.SEND_TOO_MANY, "发送短信失败，请稍后重试");
        }
        // 更新手机号和IP的发送计数
        updatePhoneCount(phone);
        updateIpCount(clientIP);
        String redisKey = Constants.genTokenKey(Constants.BUSINESS_CAPTCHA,
                Constants.TYPE_SMS, String.valueOf(System.currentTimeMillis()));
        ResultMap map = new ResultMap();
        map.put("token", redisKey);
        redisCache.setCacheObject(redisKey, randomCode, SMS_CODE_TIMEOUT_MINUTES, TimeUnit.MINUTES);
        return map;

    }

    private boolean sendSmsToHuawei(String phoneNumber, String message, String randomCode) {

        // 调用华为短信接口发送短信
        // toda 实现发送短信的逻辑
        return true; // 假设发送成功
    }


    /**
     * 根据手机号或ip地址获取redis键.
     * @param value
     * @param type
     * @return 拼接后的redis键.
     */
    public String getKey(String value, String type) {
        int hour = LocalDateTime.now().getHour();
        return type + ":" + value + ":" + hour;
    }

    /**
     * 更新手机发送次数.
     * @param phoneNumber
     */
    private void updatePhoneCount(String phoneNumber) {
        // 更新手机号的发送计数
        String phoneKey = getKey(phoneNumber, TYPE_PHONE);
        long count = redisCache.increment(phoneKey);
        if (count == 1) {
            redisCache.expire(phoneKey, HOUR_IN_SECONDS);
        }
    }

    /**
     * 更新ip发送次数.
     * @param ipAddress
     */
    private void updateIpCount(String ipAddress) {
        // 更新IP地址的发送计数
        String ipKey = getKey(ipAddress, TYPE_IP);
        long count = redisCache.increment(ipKey);
        if (count == 1) {
            redisCache.expire(ipKey, HOUR_IN_SECONDS);
        }
    }


    /**
     * 发送邮件.
     * @param receiverEmail
     * @param type
     * @return 关键信息.
     */
    @Override
    public Object sendEmail(String receiverEmail, String type)  {
        //生成${RANDOM_LENGTH}位随机验证码
        String randomCode = RandomUtil.randomNumbers(RANDOM_LENGTH);
        //根据type值的不同传递不同的text邮箱正文内容
        String content = ""; //替换为华为短信模板号
        if (type.equals(TYPE_LOGIN)) { //发送登陆的短信模板
            //TODO
            content = "login_template";
        } else if (type.equals(TYPE_AUTH)) { //发送验证的短信模板
            //TODO
            content = "auth_template";
        }
        String subject = "";
        MailSender.send(null, Collections.singleton(receiverEmail),
                subject, content, false, null);

        String key = Constants.genTokenKey(Constants.BUSINESS_CAPTCHA,
                Constants.TYPE_EMAIL, String.valueOf(System.currentTimeMillis()));
        ResultMap map = new ResultMap();
        map.put("token", key);
        redisCache.setCacheObject(key, randomCode, EMAIL_CODE_TIMEOUT_MINUTES, TimeUnit.MINUTES);
        return map;
    }




}
