package cn.com.xcsa.gateway.controller;

import cn.com.xcsa.api.framework.security.ClaimInfo;
import cn.com.xcsa.api.framework.security.SecurityApi;
import cn.com.xcsa.api.util.Constants;
import cn.com.xcsa.framework.annotation.Auth;
import cn.com.xcsa.gateway.domain.po.LoginControlStrategy;
import cn.com.xcsa.gateway.domain.vo.LoginControlStrategyVo;
import cn.com.xcsa.gateway.service.LoginControlStrategyService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PostMapping;
import javax.annotation.Resource;

/**
 * <p>用于配置各终端是否支持登录</p>.
 *
 */

@Auth
@RestController
@RequestMapping("/api/v1/loginControlStrategy")
public class LoginControlStrategyController {
    @Resource
    private LoginControlStrategyService loginControlStrategyService;

    @Resource
    private SecurityApi securityApi;

    /**
     * 查询用于配置各终端是否支持登录.
     * @param token
     * @return 结果
     */
    @GetMapping("/query")
    public Object query(@RequestHeader(Constants.AUTHORIZATION)String token) {
        ClaimInfo claimInfo = securityApi.getClaimInfo(token);
        Long tenantId = claimInfo.getTenantId();
        LambdaQueryWrapper<LoginControlStrategy> lqw = Wrappers
                .lambdaQuery(LoginControlStrategy.class)
                .eq(LoginControlStrategy::getTenantId, tenantId);
        return loginControlStrategyService.getOne(lqw);
    }

    /**
     * 修改用于配置各终端是否支持登录.
     * @param token
     * @param loginControlStrategyVo
     * @return 结果
     */
    @PostMapping("/update")
    public Boolean update(@RequestHeader(Constants.AUTHORIZATION)String token,
                          @RequestBody LoginControlStrategyVo loginControlStrategyVo) {

        ClaimInfo claimInfo = securityApi.getClaimInfo(token);
        Long tenantId = claimInfo.getTenantId();
        LambdaQueryWrapper<LoginControlStrategy> lambdaQueryWrapper = Wrappers
                .lambdaQuery(LoginControlStrategy.class)
                .eq(LoginControlStrategy::getTenantId, tenantId);

        LoginControlStrategy lcs = loginControlStrategyService.getOne(lambdaQueryWrapper);
        if (lcs == null) {
            lcs = new LoginControlStrategy();
        }
        BeanUtil.copyProperties(loginControlStrategyVo, lcs, CopyOptions.create()
                .setIgnoreNullValue(true));
        return loginControlStrategyService.saveOrUpdate(lcs);
    }
}
