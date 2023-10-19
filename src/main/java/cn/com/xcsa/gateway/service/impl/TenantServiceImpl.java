package cn.com.xcsa.gateway.service.impl;

import cn.com.xcsa.gateway.domain.po.Tenant;
import cn.com.xcsa.gateway.mapper.TenantMapper;
import cn.com.xcsa.gateway.service.TenantService;
import cn.com.xcsa.framework.core.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
* <p>租户信息表 服务实现类</p>.
*
* @author huyu
* @since 2023-09-21
*/
@Service
public class TenantServiceImpl
        extends BaseServiceImpl<TenantMapper, Tenant> implements TenantService {

}
