package cn.com.xcsa.gateway.controller;

import cn.com.xcsa.gateway.service.LicenseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
* <p> 前端控制器</p>.
*
* @author wuhui
* @since 2023-08-11
*/
@RestController
@RequestMapping("/api/v1/license")
public class LicenseController {


    @Resource
    private LicenseService licenseService;
}
