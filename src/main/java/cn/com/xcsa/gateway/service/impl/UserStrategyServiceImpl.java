package cn.com.xcsa.gateway.service.impl;

import cn.com.xcsa.framework.core.service.impl.BaseServiceImpl;
import cn.com.xcsa.gateway.domain.po.UserStrategy;
import cn.com.xcsa.gateway.mapper.UserStrategyMapper;
import cn.com.xcsa.gateway.service.UserStrategyService;
import org.springframework.stereotype.Service;
/**
 *
 * <p>用户策略配置表 服务实现类</p>.
 *
 */
@Service
public class UserStrategyServiceImpl extends BaseServiceImpl
        <UserStrategyMapper, UserStrategy>
        implements UserStrategyService {
}
