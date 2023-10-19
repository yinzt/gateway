package cn.com.xcsa.gateway.controller;

import cn.com.xcsa.gateway.domain.po.UserSpace;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.com.xcsa.gateway.service.UserSpaceService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
* <p>用户空间信息表 前端控制器</p>.
*
* @author huyu
* @since 2023-09-22
*/
@RestController
@RequestMapping("/api/v1/userSpace")
public class UserSpaceController {


    @Resource
    private UserSpaceService userSpaceService;

    /**
     * 变更用户空间配额.
     * @param userSpace
     * @return 变更结果.
     */
    @PostMapping("/update")
    public Boolean update(@RequestBody @Validated UserSpace userSpace) {
        return userSpaceService.update(new UpdateWrapper<UserSpace>()
                .lambda()
                .eq(UserSpace::getUserId, userSpace.getUserId())
                .set(UserSpace::getTotalSpaceQuota, userSpace.getTotalSpaceQuota()));
    }
}
