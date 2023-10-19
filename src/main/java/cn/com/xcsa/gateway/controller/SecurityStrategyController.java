package cn.com.xcsa.gateway.controller;

import cn.com.xcsa.api.framework.security.ClaimInfo;
import cn.com.xcsa.api.framework.security.SecurityApi;
import cn.com.xcsa.api.util.Constants;
import cn.com.xcsa.framework.annotation.Auth;
import cn.com.xcsa.gateway.domain.po.SecurityStrategy;
import cn.com.xcsa.gateway.domain.vo.SecurityStrategyVo;
import cn.com.xcsa.gateway.service.SecurityStrategyService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.annotation.Resource;
/**
 * <p>安全策略->访问策略->访问安全配置表</p>.
 *
 */

@Auth
@RestController
@RequestMapping("/api/v1/securityStrategy")
public class SecurityStrategyController {
    @Resource
    private SecurityStrategyService securityStrategyService;
    @Resource
    private SecurityApi securityApi;

    /**
     * 查询访问安全配置表.
     * @param token
     * @return 结果
     */
    @GetMapping ("/query")
    public Object query(@RequestHeader(Constants.AUTHORIZATION)String token) {
        ClaimInfo claimInfo = securityApi.getClaimInfo(token);
        Long tenantId = claimInfo.getTenantId();
        LambdaQueryWrapper<SecurityStrategy> lambdaQueryWrapper = Wrappers
                .lambdaQuery(SecurityStrategy.class)
                .eq(SecurityStrategy::getTenantId, tenantId);
        return securityStrategyService.getOne(lambdaQueryWrapper);
    }

    /**
     * 修改访问安全配置表.
     * @param token
     * @param securityStrategyVo
     * @return 结果
     *
     */
    @PostMapping("/update")
    public Boolean update(@RequestHeader(Constants.AUTHORIZATION)String token,
                          @RequestBody  SecurityStrategyVo securityStrategyVo) {
        ClaimInfo claimInfo = securityApi.getClaimInfo(token);
        Long tenantId = claimInfo.getTenantId();
        LambdaQueryWrapper<SecurityStrategy> lambdaQueryWrapper = Wrappers
                .lambdaQuery(SecurityStrategy.class)
                .eq(SecurityStrategy::getTenantId, tenantId);

        SecurityStrategy strategy = securityStrategyService.getOne(lambdaQueryWrapper);
        if (strategy == null) {
            strategy = new SecurityStrategy();
        }
        BeanUtil.copyProperties(securityStrategyVo, strategy, CopyOptions.create()
                .setIgnoreNullValue(true));
        return securityStrategyService.saveOrUpdate(strategy);

    }

}
