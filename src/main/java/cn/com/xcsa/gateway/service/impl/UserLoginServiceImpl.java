package cn.com.xcsa.gateway.service.impl;

import cn.com.xcsa.gateway.domain.po.UserLogin;
import cn.com.xcsa.gateway.mapper.UserLoginMapper;
import cn.com.xcsa.gateway.service.UserLoginService;
import cn.com.xcsa.framework.core.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
* <p> 服务实现类</p>.
*
* @author wuhui
* @since 2023-09-03
*/
@Service
public class UserLoginServiceImpl extends BaseServiceImpl<UserLoginMapper, UserLogin>
        implements UserLoginService {

}
