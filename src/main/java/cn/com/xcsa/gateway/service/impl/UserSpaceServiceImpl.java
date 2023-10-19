package cn.com.xcsa.gateway.service.impl;

import cn.com.xcsa.gateway.domain.po.UserSpace;
import cn.com.xcsa.gateway.mapper.UserSpaceMapper;
import cn.com.xcsa.gateway.service.UserSpaceService;
import cn.com.xcsa.framework.core.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
* <p>用户空间信息表 服务实现类</p>.
*
* @author huyu
* @since 2023-09-22
*/
@Service
public class UserSpaceServiceImpl extends
        BaseServiceImpl<UserSpaceMapper, UserSpace> implements UserSpaceService {

}
