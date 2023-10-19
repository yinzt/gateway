package cn.com.xcsa.gateway.service.impl;

import cn.com.xcsa.gateway.domain.po.OrgUser;
import cn.com.xcsa.gateway.mapper.OrgUserMapper;
import cn.com.xcsa.gateway.service.OrgUserService;
import cn.com.xcsa.framework.core.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
* <p>组织机构和用户信息关联表 服务实现类</p>.
*
* @author huyu
* @since 2023-09-22
*/
@Service
public class OrgUserServiceImpl extends
        BaseServiceImpl<OrgUserMapper, OrgUser> implements OrgUserService {

}
