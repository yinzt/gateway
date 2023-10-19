package cn.com.xcsa.gateway.controller;

import cn.com.xcsa.gateway.domain.po.Group;
import cn.com.xcsa.gateway.domain.po.GroupUser;
import cn.com.xcsa.gateway.domain.vo.GroupVo;
import cn.com.xcsa.gateway.service.GroupUserService;
import cn.com.xcsa.gateway.util.GroupAdd;
import cn.com.xcsa.gateway.util.GroupUpdate;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.validation.annotation.Validated;
import cn.com.xcsa.gateway.service.GroupService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
* <p>用户组表 前端控制器</p>.
*
* @author huyu
* @since 2023-09-24
*/
@RestController
@RequestMapping("/api/v1/group")
public class GroupController {


    @Resource
    private GroupService groupService;

    @Resource
    private GroupUserService groupUserService;


    //数据状态为删除状态
    private static final Integer FLAG_DELETE = 1;
    //数据状态为未删除状态
    private static final Integer FLAG_UN_DELETE = 0;

    /**
     * 获取用户组列表.
     * @return 列表数据.
     */
     @GetMapping("/list")
    public Object list() {
         return groupService.list(Wrappers.lambdaQuery(Group.class)
                 .eq(Group::getDeleted, FLAG_UN_DELETE));
    }

    /**
     * 增加用户组.
     * @param groupVo
     * @return 结果.
     */
    @PostMapping("/add")
    public Boolean add(@RequestBody @Validated(GroupAdd.class) GroupVo groupVo) {
        Group group = BeanUtil.copyProperties(groupVo, Group.class, "id");
        group.setCreateTime(LocalDateTime.now());
        return groupService.save(group);
    }

    /**
     * 修改用户组.
     * @param groupVo
     * @return 修改结果.
     */
    @PostMapping("/update")
    public Boolean update(@RequestBody @Validated(GroupUpdate.class) GroupVo groupVo) {
        Group group = BeanUtil.copyProperties(groupVo, Group.class);
        return groupService.update(group, new UpdateWrapper<Group>()
                .lambda()
                .eq(Group::getId, group.getId()));
    }

    /**
     * 删除用户组.
     * @param id
     * @return 删除结果.
     */
    @PostMapping("delete")
    public Boolean delete(@RequestParam("id") Long id) {
        Boolean flag = groupService.update(new UpdateWrapper<Group>()
                .lambda()
                .eq(Group::getId, id)
                .set(Group::getDeleted, FLAG_DELETE));
        Boolean guserFlage = groupUserService.update(new UpdateWrapper<GroupUser>()
                .lambda()
                .eq(GroupUser::getGroupId, id)
                .set(GroupUser::getDeleted, FLAG_DELETE));
        if (!flag || !guserFlage) {
            return false;
        }
        return true;
    }


}
