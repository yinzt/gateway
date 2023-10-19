package cn.com.xcsa.gateway.service.impl;

import cn.com.xcsa.gateway.domain.po.LoginRecord;
import cn.com.xcsa.gateway.mapper.LoginRecordMapper;
import cn.com.xcsa.gateway.service.LoginRecordService;
import cn.com.xcsa.framework.core.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
* <p> 服务实现类</p>.
*
* @author huyu
* @since 2023-09-13
*/
@Service
public class LoginRecordServiceImpl extends
        BaseServiceImpl<LoginRecordMapper, LoginRecord>
        implements LoginRecordService {

}
