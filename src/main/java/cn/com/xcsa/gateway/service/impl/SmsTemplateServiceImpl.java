package cn.com.xcsa.gateway.service.impl;

import cn.com.xcsa.gateway.domain.po.SmsTemplate;
import cn.com.xcsa.gateway.mapper.SmsTemplateMapper;
import cn.com.xcsa.gateway.service.SmsTemplateService;
import cn.com.xcsa.framework.core.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
* <p> 服务实现类</p>.
*
* @author wuhui
* @since 2023-09-17
*/
@Service
public class SmsTemplateServiceImpl extends
        BaseServiceImpl<SmsTemplateMapper, SmsTemplate> implements SmsTemplateService {

}
