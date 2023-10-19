package cn.com.xcsa.gateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import cn.com.xcsa.gateway.service.TenantSpaceService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
* <p>租户空间信息表 前端控制器</p>.
*
* @author huyu
* @since 2023-09-21
*/
@RestController
@RequestMapping("/api/v1/tenantSpace")
public class TenantSpaceController {


     @Resource
    private TenantSpaceService tenantSpaceService;
}
