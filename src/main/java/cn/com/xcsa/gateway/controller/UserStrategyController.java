package cn.com.xcsa.gateway.controller;

import cn.com.xcsa.api.framework.security.ClaimInfo;
import cn.com.xcsa.api.framework.security.SecurityApi;
import cn.com.xcsa.api.util.Constants;
import cn.com.xcsa.framework.annotation.Auth;
import cn.com.xcsa.gateway.domain.po.UserStrategy;
import cn.com.xcsa.gateway.domain.vo.UserStrategyVo;
import cn.com.xcsa.gateway.service.UserStrategyService;
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
 *
 * <p>用户策略配置表</p>.
 *
 */
@Auth
@RestController
@RequestMapping("/api/v1/userStrategy")
public class UserStrategyController {
    @Resource
    private UserStrategyService userStrategyService;
    @Resource
    private SecurityApi securityApi;

    /**
     * 查询用户策略配置表.
     * @param token
     * @return 结果
     */
    @GetMapping("/query")
    public Object query(@RequestHeader(Constants.AUTHORIZATION)String token) {
        ClaimInfo claimInfo = securityApi.getClaimInfo(token);
        Long tenantId = claimInfo.getTenantId();
        LambdaQueryWrapper<UserStrategy> lqw = Wrappers.lambdaQuery(UserStrategy.class)
                .eq(UserStrategy::getTenantId, tenantId);
        return userStrategyService.getOne(lqw);
    }

    /**
     * 修改用户策略配置表.
     * @param token
     * @param userStrategyVo
     * @return 结果
     */
    @PostMapping("/update")
    public Boolean update(@RequestHeader(Constants.AUTHORIZATION)String token,
                          @RequestBody UserStrategyVo userStrategyVo) {
        ClaimInfo claimInfo = securityApi.getClaimInfo(token);
        Long tenantId = claimInfo.getTenantId();
        LambdaQueryWrapper<UserStrategy> lqw = Wrappers.lambdaQuery(UserStrategy.class)
                .eq(UserStrategy::getTenantId, tenantId);
        UserStrategy userStrategy = userStrategyService.getOne(lqw);
        if (userStrategy == null) {
            userStrategy = new UserStrategy();
        }
        BeanUtil.copyProperties(userStrategyVo, userStrategy, CopyOptions.create()
                .setIgnoreNullValue(true));
        return userStrategyService.saveOrUpdate(userStrategy);

    }
}
