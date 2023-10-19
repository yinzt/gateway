package cn.com.xcsa.gateway.service.impl;

import cn.com.xcsa.framework.core.service.impl.BaseServiceImpl;
import cn.com.xcsa.gateway.domain.po.ProcedureStrategy;
import cn.com.xcsa.gateway.mapper.ProcedureStrategyMapper;
import cn.com.xcsa.gateway.service.ProcedureStrategyService;
import org.springframework.stereotype.Service;
/**
 * <p>程序策略配置表 服务实现类</p>.
 */
@Service
public class ProcedureStrategyServiceImpl extends BaseServiceImpl
        <ProcedureStrategyMapper, ProcedureStrategy>
        implements ProcedureStrategyService {
}
