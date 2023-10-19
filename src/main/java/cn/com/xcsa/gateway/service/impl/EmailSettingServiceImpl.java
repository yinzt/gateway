package cn.com.xcsa.gateway.service.impl;

import cn.com.xcsa.gateway.domain.po.EmailSettings;
import cn.com.xcsa.gateway.mapper.EmailSettingMapper;
import cn.com.xcsa.gateway.service.EmailSettingService;
import cn.com.xcsa.framework.core.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
* <p> 服务实现类</p>.
*
* @author wuhui
* @since 2023-09-17
*/
@Service
public class EmailSettingServiceImpl extends
        BaseServiceImpl<EmailSettingMapper, EmailSettings> implements EmailSettingService {

}
