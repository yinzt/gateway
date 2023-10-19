package cn.com.xcsa.gateway.controller;

import cn.com.xcsa.api.exception.ApiRuntimeException;
import cn.com.xcsa.api.framework.redis.RedisCache;
import cn.com.xcsa.api.framework.security.Credentials;
import cn.com.xcsa.api.framework.security.SecurityApi;
import cn.com.xcsa.api.util.InfoCode;
import cn.com.xcsa.gateway.domain.po.ResultMap;
import cn.com.xcsa.gateway.domain.po.User;
import cn.com.xcsa.gateway.domain.vo.TotpVo;
import cn.com.xcsa.gateway.service.UserService;
import cn.com.xcsa.gateway.util.Constants;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.concurrent.TimeUnit;


/**
 * <p>google验证相关</p>.
 *
 * @author huyu
 * @since 2023-09-11
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Resource
    private RedisCache redisCache;

    @Resource
    private UserService userService;

    @Resource
    private SecurityApi securityApi;

    private static final Integer TOTP_TOKEN_TIME = 10;

    /**
     * 验证totp正确性.
     *
     * @param totpVo
     * @return 验证totp正确性.
     */
    @PostMapping("/totp")
    public Object totp(@RequestBody @Validated TotpVo totpVo) {
        String str = redisCache.getCacheObject(totpVo.getAccessToken()).toString();
        if (StrUtil.isBlankIfStr(str)) {
            throw new ApiRuntimeException(InfoCode.USER_ERROR);
        }
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery(User.class)
                .eq(User::getId, totpVo.getId());
        User user = userService.getOne(wrapper);
        if (BeanUtil.isEmpty(user)) {
            throw new ApiRuntimeException(InfoCode.USER_IS_NULL);
        }
        boolean validate = securityApi.validate(user.getAuthSecret(),
                Integer.valueOf(totpVo.getCode()));
        if (!validate) {
            throw new ApiRuntimeException(InfoCode.ACCESS_TOKEN_EXPIRE);
        }
        String variable = SecureUtil.md5((UUID.randomUUID().toString()));
        String key =
                Constants.genTokenKey(Constants.BUSINESS_AUTH, Constants.TYPE_TOTP, variable);
        redisCache.setCacheObject(key, JSONObject.toJSONString(user),
                TOTP_TOKEN_TIME, TimeUnit.MINUTES);
        ResultMap resultMap = new ResultMap();
        resultMap.put("accessToken", key);
        return resultMap;

    }

    /**
     * 获取googleauth验证器二维码.
     * @param userId
     * @return 获取googleauth验证器二维码.
     */
    @GetMapping("/qrCode")
    public Object qrCode(@RequestParam("id")@NotNull Integer userId) {
        User user = userService.getOne(
                new LambdaQueryWrapper<>(User.class).eq(User::getId, userId));
        if (BeanUtil.isEmpty(user)) {
            throw new ApiRuntimeException(InfoCode.USER_IS_NULL);
        }
        Credentials credentials = securityApi.createCredentials(user.getName(),
                user.getLoginName());
        user.setAuthSecret(credentials.getSecretKey());
        userService.updateById(user);
        return credentials;
    }



}
