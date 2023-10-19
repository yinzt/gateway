package cn.com.xcsa.gateway.service.impl;

import cn.com.xcsa.gateway.domain.po.GroupUser;
import cn.com.xcsa.gateway.mapper.GroupUserMapper;
import cn.com.xcsa.gateway.service.GroupUserService;
import cn.com.xcsa.framework.core.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
* <p>用户组关联用户表 服务实现类</p>.
*
* @author huyu
* @since 2023-09-24
*/
@Service
public class GroupUserServiceImpl extends BaseServiceImpl<GroupUserMapper, GroupUser>
        implements GroupUserService {

}
