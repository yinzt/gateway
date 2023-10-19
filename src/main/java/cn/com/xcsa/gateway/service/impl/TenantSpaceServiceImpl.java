package cn.com.xcsa.gateway.service.impl;

import cn.com.xcsa.gateway.domain.po.TenantSpace;
import cn.com.xcsa.gateway.mapper.TenantSpaceMapper;
import cn.com.xcsa.gateway.service.TenantSpaceService;
import cn.com.xcsa.framework.core.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
* <p>租户空间信息表 服务实现类</p>.
*
* @author huyu
* @since 2023-09-21
*/
@Service
public class TenantSpaceServiceImpl
        extends BaseServiceImpl<TenantSpaceMapper, TenantSpace> implements TenantSpaceService {

}
