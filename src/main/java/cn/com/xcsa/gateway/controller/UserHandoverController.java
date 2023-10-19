package cn.com.xcsa.gateway.controller;

import cn.com.xcsa.api.exception.ApiRuntimeException;
import cn.com.xcsa.api.util.InfoCode;
import cn.com.xcsa.gateway.domain.po.User;
import cn.com.xcsa.gateway.domain.po.UserHandover;
import cn.com.xcsa.gateway.domain.vo.UserHandoverVo;
import cn.com.xcsa.gateway.service.UserService;
import cn.com.xcsa.gateway.util.GroupAdd;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.com.xcsa.gateway.service.UserHandoverService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
* <p>离职交接业务表 前端控制器</p>.
*
* @author huyu
* @since 2023-09-26
*/
@RestController
@RequestMapping("/api/v1/userHandover")
public class UserHandoverController {


     @Resource
    private UserHandoverService userHandoverService;

    @Resource
    private UserService userService;

    //数据状态为删除状态
    private static  final Integer FLAG_DELETE = 1;

    private static final Integer FLAG_UN_DELETE = 0;

    /**
     * 添加离职交接信息.
     * @param userHandoverVo
     * @return 添加结果.
     */
    @PostMapping("/add")
    public Boolean add(@RequestBody @Validated(GroupAdd.class) UserHandoverVo userHandoverVo) {
        //不可以离职交接给自己
        if (userHandoverVo.getLeaveUserId().equals(userHandoverVo.getReceiveUserId())) {
            throw new ApiRuntimeException(InfoCode.PARAMS_ERROR);
        }
        Boolean flag = false;
        UserHandover userHandover = BeanUtil.copyProperties(userHandoverVo,
                UserHandover.class, "id");
        LocalDateTime now = LocalDateTime.now();
        //可以变更离职交接的接收人
        UserHandover user = userHandoverService.getOne(Wrappers.lambdaQuery(UserHandover.class)
                .eq(UserHandover::getLeaveUserId, userHandover.getLeaveUserId())
                .eq(UserHandover::getDeleted, FLAG_UN_DELETE));
        if (ObjectUtil.isNotEmpty(user)) {
            LambdaUpdateWrapper<UserHandover> updateWrapper = new UpdateWrapper<UserHandover>()
                    .lambda()
                    .eq(UserHandover::getLeaveUserId, userHandover.getLeaveUserId())
                    .set(UserHandover::getReceiveUserId, userHandover.getReceiveUserId())
                    .set(UserHandover::getUpdateTime, now);
            flag = userHandoverService.update(updateWrapper);
        } else {
            //不存在已有交接人的情况下新增数据
            userHandover.setCreateTime(now);
            flag = userHandoverService.save(userHandover);
        }
        //变更用户表的离职人的状态
        Boolean userFlag = userService.update(new UpdateWrapper<User>()
                .lambda()
                .eq(User::getId, userHandover.getLeaveUserId())
                .set(User::getUserStatus, User.UserStatus.RESIGNING.getValue()));
        if (!flag || !userFlag) {
            throw new ApiRuntimeException(InfoCode.FAIL);
        }
        //TODO 变更后续业务文件夹的状态
        return flag;
    }



}
