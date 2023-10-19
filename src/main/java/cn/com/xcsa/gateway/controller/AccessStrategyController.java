package cn.com.xcsa.gateway.controller;

import cn.com.xcsa.api.framework.security.ClaimInfo;
import cn.com.xcsa.api.framework.security.SecurityApi;
import cn.com.xcsa.api.util.Constants;
import cn.com.xcsa.framework.annotation.Auth;
import cn.com.xcsa.gateway.domain.po.AccessStrategy;
import cn.com.xcsa.gateway.domain.vo.AccessStrategyVo;
import cn.com.xcsa.gateway.service.AccessStrategyService;
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
 * <P>安全策略->访问策略访问规则配置表</P>.
 */

@Auth
@RestController
@RequestMapping("/api/v1/accessStrategy")
public class AccessStrategyController {
    @Resource
    private AccessStrategyService accessStrategyService;

    @Resource
    private SecurityApi securityApi;

    /**
     * 查询策略访问规则配置表.
     * @param token
     * @return 结果
     */
    @GetMapping("/query")
    public Object query(@RequestHeader(Constants.AUTHORIZATION)String token) {
        ClaimInfo claimInfo = securityApi.getClaimInfo(token);
        Long tenantId = claimInfo.getTenantId();
        LambdaQueryWrapper<AccessStrategy> lqw = Wrappers.lambdaQuery(AccessStrategy.class)
                .eq(AccessStrategy::getTenantId, tenantId);
        return accessStrategyService.getOne(lqw);
    }

    /**
     * 修改策略访问规则配置表.
     * @param token
     * @param accessStrategyVo
     * @return 结果
     */
    @PostMapping("/update")
    public Boolean update(@RequestHeader(Constants.AUTHORIZATION)String token,
            @RequestBody AccessStrategyVo accessStrategyVo) {
        ClaimInfo claimInfo = securityApi.getClaimInfo(token);
        Long tenantId = claimInfo.getTenantId();
        LambdaQueryWrapper<AccessStrategy> lqw = Wrappers.lambdaQuery(AccessStrategy.class)
                .eq(AccessStrategy::getTenantId, tenantId);
        AccessStrategy accessStrategy = accessStrategyService.getOne(lqw);
        if (accessStrategy == null) {
            accessStrategy = new AccessStrategy();
        }
        BeanUtil.copyProperties(accessStrategyVo, accessStrategy, CopyOptions.create()
                .setIgnoreNullValue(true));
        return accessStrategyService.saveOrUpdate(accessStrategy);

    }
}

