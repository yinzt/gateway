package cn.com.xcsa.gateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import cn.com.xcsa.gateway.service.EmailSettingService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
* <p> 前端控制器</p>.
*
* @author wuhui
* @since 2023-09-17
*/
@RestController
@RequestMapping("/api/v1/emailSetting")
public class EmailSettingController {


    @Resource
    private EmailSettingService emailSettingService;
}
