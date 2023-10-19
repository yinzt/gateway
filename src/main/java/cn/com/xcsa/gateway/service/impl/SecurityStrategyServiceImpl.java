package cn.com.xcsa.gateway.service.impl;

import cn.com.xcsa.framework.core.service.impl.BaseServiceImpl;
import cn.com.xcsa.gateway.domain.po.SecurityStrategy;
import cn.com.xcsa.gateway.mapper.SecurityStrategyMapper;
import cn.com.xcsa.gateway.service.SecurityStrategyService;
import org.springframework.stereotype.Service;
/**
 *
 * <P>服务实现类</P>.
 */
@Service
public class SecurityStrategyServiceImpl extends BaseServiceImpl
        <SecurityStrategyMapper, SecurityStrategy>
        implements SecurityStrategyService {
}
