package cn.com.xcsa.gateway.service.impl;

import cn.com.xcsa.gateway.domain.po.UserHandover;
import cn.com.xcsa.gateway.mapper.UserHandoverMapper;
import cn.com.xcsa.gateway.service.UserHandoverService;
import cn.com.xcsa.framework.core.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 离职交接业务表.
 */
@Service
public class UserHandoverServiceImpl
		extends BaseServiceImpl<UserHandoverMapper, UserHandover>
		implements UserHandoverService {

}
