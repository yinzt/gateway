package cn.com.xcsa.gateway.service.impl;


import cn.com.xcsa.gateway.domain.po.User;
import cn.com.xcsa.gateway.mapper.UserMapper;
import cn.com.xcsa.gateway.service.UserService;
import cn.com.xcsa.framework.core.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
* <p> 服务实现类</p>.
*
* @author hy
* @since 2023-07-24
*/
@Service
public class UserServiceImpl extends
        BaseServiceImpl<UserMapper, User> implements UserService {


}
