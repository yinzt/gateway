package cn.com.xcsa.gateway.service.impl;

import cn.com.xcsa.framework.core.service.impl.BaseServiceImpl;
import cn.com.xcsa.gateway.domain.po.AccessScope;
import cn.com.xcsa.gateway.mapper.AccessScopeMapper;
import cn.com.xcsa.gateway.service.AccessScopeService;
import org.springframework.stereotype.Service;
/**
 *<p>访问安全配置中的网络访问区域配置表 服务实现类</p>.
 *
 */
@Service
public class AccessScopeServiceImpl extends BaseServiceImpl
        <AccessScopeMapper, AccessScope>
        implements AccessScopeService {
}
