package cn.com.xcsa.gateway.service.impl;

import cn.com.xcsa.framework.core.service.impl.BaseServiceImpl;
import cn.com.xcsa.gateway.domain.po.LoginControlStrategy;
import cn.com.xcsa.gateway.mapper.LoginControlStrategyMapper;
import cn.com.xcsa.gateway.service.LoginControlStrategyService;
import org.springframework.stereotype.Service;
/**
 * <p>用于配置各终端是否支持登录  服务实现类</p>.
 *
 */
@Service
public class LoginControlStrategyServiceImpl extends BaseServiceImpl
        <LoginControlStrategyMapper, LoginControlStrategy>
        implements LoginControlStrategyService {
}
