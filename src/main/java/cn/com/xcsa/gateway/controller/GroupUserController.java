package cn.com.xcsa.gateway.controller;

import cn.com.xcsa.api.exception.ApiRuntimeException;
import cn.com.xcsa.api.util.InfoCode;
import cn.com.xcsa.gateway.domain.po.GroupUser;
import cn.com.xcsa.gateway.domain.po.User;
import cn.com.xcsa.gateway.domain.vo.GroupUserVo;
import cn.com.xcsa.gateway.service.UserService;
import cn.com.xcsa.gateway.util.GroupAdd;
import cn.com.xcsa.gateway.util.GroupDelete;
import cn.com.xcsa.gateway.util.GroupList;
import cn.com.xcsa.gateway.service.GroupUserService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
* <p>用户组关联用户表 前端控制器</p>.
*
* @author huyu
* @since 2023-09-24
*/
@RestController
@RequestMapping("/api/v1/groupUser")
public class GroupUserController {


    @Resource
    private GroupUserService groupUserService;

    @Resource
    private UserService userService;

    //数据状态为删除状态
    private static final Integer FLAG_DELETE = 1;
    //数据状态为未删除状态
    private static final Integer FLAG_UN_DELETE = 0;

    /**
     * 获取用户组所属用户列表.
     * @param groupUserVo
     * @return 分页列表数据.
     */
    @GetMapping("/list")
    public Object list(@RequestBody @Validated(GroupList.class) GroupUserVo groupUserVo) {
        //获取用户组下全部用户list
        List<GroupUser> list = groupUserService.list(
                Wrappers.lambdaQuery(GroupUser.class)
                        .eq(GroupUser::getGroupId, groupUserVo.getGroupId())
                        .eq(GroupUser::getDeleted, FLAG_UN_DELETE));
        if (list.size() < 1) {
            throw new ApiRuntimeException(InfoCode.RESULT_NULL);
        }
        //获取用户的id集合
        List<Long> userIds = list.stream()
                .map(GroupUser::getUserId)
                .collect(Collectors.toList());
        //获取用户列表
        LambdaQueryWrapper<User> queryWrapper =
                Wrappers.lambdaQuery(User.class)
                        .in(User::getId, userIds)
                        .eq(User::getDeleted, FLAG_UN_DELETE)
                        .like(!StrUtil.isBlankIfStr(groupUserVo.getName()),
                                User::getName, groupUserVo.getName());
                return userService.searchPage(groupUserVo.getPageNo(),
                        groupUserVo.getPageSize(), queryWrapper);
    }


    /**
     * 移除用户所在分组.
     * @param groupUserVo
     * @return 移除结果.
     */
    @PostMapping("/delete")
    public Boolean delete(@RequestBody @Validated(GroupDelete.class) GroupUserVo groupUserVo) {
        return groupUserService.update(new UpdateWrapper<GroupUser>()
                .lambda()
                .eq(GroupUser::getGroupId, groupUserVo.getGroupId())
                .eq(GroupUser::getUserId, groupUserVo.getUserId())
                .set(GroupUser::getDeleted, FLAG_DELETE));
    }


    /**
     * 添加用户组内分组用户.
     * @param groupUserVo
     * @return 添加结果.
     */
    @PostMapping("/add")
    public Boolean add(@RequestBody @Validated(GroupAdd.class) GroupUserVo groupUserVo) {
        List<Long> userIds = groupUserVo.getUserIds();
        List<GroupUser> list = groupUserService.list(Wrappers.lambdaQuery(GroupUser.class)
                .in(GroupUser::getUserId, userIds)
                .eq(GroupUser::getDeleted, FLAG_UN_DELETE));
        //取出不存在的userIds
        List<Long> nonExistentUserIds  = userIds.stream()
                .filter(userId ->
                        list.stream().noneMatch(groupUser ->
                                groupUser.getUserId().equals(userId))).collect(Collectors.toList());
        //批量保存
        List<GroupUser> groupUsers = nonExistentUserIds.stream()
                .map(userId -> {
                    GroupUser groupUser = new GroupUser();
                    groupUser.setCreateTime(LocalDateTime.now());
                    groupUser.setGroupId(groupUserVo.getGroupId());
                    groupUser.setUserId(userId);
                    groupUser.setTenantId(groupUserVo.getTenantId());
                    return groupUser;
                })
                .collect(Collectors.toList());

        return groupUserService.saveBatch(groupUsers);
    }



}
