package cn.com.xcsa.gateway.service.impl;

import cn.com.xcsa.gateway.domain.po.Group;
import cn.com.xcsa.gateway.mapper.GroupMapper;
import cn.com.xcsa.gateway.service.GroupService;
import cn.com.xcsa.framework.core.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
* <p>用户组表 服务实现类</p>.
*
* @author huyu
* @since 2023-09-24
*/
@Service
public class GroupServiceImpl extends BaseServiceImpl<GroupMapper, Group> implements GroupService {

}
