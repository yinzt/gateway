package cn.com.xcsa.gateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import cn.com.xcsa.gateway.service.OrgUserService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
* <p>组织机构和用户信息关联表 前端控制器</p>.
*
* @author huyu
* @since 2023-09-22
*/
@RestController
@RequestMapping("/api/v1/orgUser")
public class OrgUserController {


     @Resource
    private OrgUserService orgUserService;
}
