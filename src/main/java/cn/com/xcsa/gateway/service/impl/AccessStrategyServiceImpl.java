package cn.com.xcsa.gateway.service.impl;

import cn.com.xcsa.framework.core.service.impl.BaseServiceImpl;
import cn.com.xcsa.gateway.domain.po.AccessStrategy;
import cn.com.xcsa.gateway.mapper.AccessStrategyMapper;
import cn.com.xcsa.gateway.service.AccessStrategyService;
import org.springframework.stereotype.Service;

/**
 * 安全策略->访问策略访问规则配置表.
 * <p>服务实现类<p/>
 *
 */
@Service
public class AccessStrategyServiceImpl extends BaseServiceImpl
        <AccessStrategyMapper, AccessStrategy>
        implements AccessStrategyService {
}
