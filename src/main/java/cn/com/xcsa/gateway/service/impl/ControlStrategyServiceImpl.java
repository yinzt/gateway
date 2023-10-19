package cn.com.xcsa.gateway.service.impl;

import cn.com.xcsa.framework.core.service.impl.BaseServiceImpl;
import cn.com.xcsa.gateway.domain.po.ControlStrategy;
import cn.com.xcsa.gateway.mapper.ControlStrategyMapper;
import cn.com.xcsa.gateway.service.ControlStrategyService;
import org.springframework.stereotype.Service;
/**
 *
 * <p>登录控制策略规则配置表  服务实现类</p>.
 *
 */
@Service
public class ControlStrategyServiceImpl extends BaseServiceImpl
        <ControlStrategyMapper, ControlStrategy>
        implements ControlStrategyService {
}
