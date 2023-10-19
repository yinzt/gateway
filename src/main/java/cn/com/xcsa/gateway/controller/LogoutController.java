package cn.com.xcsa.gateway.controller;


import cn.com.xcsa.api.framework.security.SecurityApi;
import cn.com.xcsa.api.util.Constants;
import cn.hutool.core.util.StrUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 退出登录接口.
 */
@RestController
@RequestMapping("/api/v1/logout")
public class LogoutController {

    @Resource
    private SecurityApi securityApi;

    /**
     * 退出登录.
     * @param token
     * @return 退出登录状态.
     */
    @PostMapping
    public Boolean logout(@RequestHeader(name = Constants.AUTHORIZATION, required = false)
                              String token) {
        if (StrUtil.isBlank(token)) {
            return false;
        }
        return securityApi.logout(token);
    }
}
