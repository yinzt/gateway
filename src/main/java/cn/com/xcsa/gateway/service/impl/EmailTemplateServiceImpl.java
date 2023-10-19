package cn.com.xcsa.gateway.service.impl;

import cn.com.xcsa.gateway.domain.po.EmailTemplate;
import cn.com.xcsa.gateway.mapper.EmailTemplateMapper;
import cn.com.xcsa.gateway.service.EmailTemplateService;
import cn.com.xcsa.framework.core.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
* <p> 服务实现类</p>.
*
* @author wuhui
* @since 2023-09-17
*/
@Service
public class EmailTemplateServiceImpl extends
        BaseServiceImpl<EmailTemplateMapper, EmailTemplate> implements EmailTemplateService {

}
