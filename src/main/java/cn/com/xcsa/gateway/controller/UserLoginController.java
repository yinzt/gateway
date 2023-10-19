package cn.com.xcsa.gateway.controller;

import cn.com.xcsa.gateway.domain.vo.LoginVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.validation.annotation.Validated;
import cn.com.xcsa.gateway.domain.po.UserLogin;
import cn.com.xcsa.gateway.service.UserLoginService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;


/**
* <p> 前端控制器</p>.
*
* @author wuhui
* @since 2023-09-03
*/
@RestController
@RequestMapping("/api/v1/userLogin")
public class UserLoginController {


    @Resource
    private UserLoginService userLoginService;

    /**
     * 返回List.
     * @param loginVo
     * @return 结果
     */
    @PostMapping("/list")
    public Object list(@RequestBody @Validated LoginVo loginVo) {
        LambdaQueryWrapper<UserLogin> qw = Wrappers.lambdaQuery(UserLogin.class)
                .eq(UserLogin::getUserId, loginVo.getUserId());
        return userLoginService.list(qw);
    }

}
