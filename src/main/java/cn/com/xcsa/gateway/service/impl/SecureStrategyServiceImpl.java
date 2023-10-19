package cn.com.xcsa.gateway.service.impl;

import cn.com.xcsa.framework.core.service.impl.BaseServiceImpl;
import cn.com.xcsa.gateway.domain.po.SecureStrategy;
import cn.com.xcsa.gateway.mapper.SecureStrategyMapper;
import cn.com.xcsa.gateway.service.SecureStrategyService;
import org.springframework.stereotype.Service;
/**
 *
 * <p>防护策略配置表 服务实现类</p>.
 *
 */
@Service
public class SecureStrategyServiceImpl extends BaseServiceImpl
        <SecureStrategyMapper, SecureStrategy>
        implements SecureStrategyService {
}
