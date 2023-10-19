package cn.com.xcsa.gateway.controller;

import cn.com.xcsa.api.exception.ApiRuntimeException;
import cn.com.xcsa.api.framework.redis.RedisCache;
import cn.com.xcsa.api.framework.security.SecurityApi;
import cn.com.xcsa.api.util.InfoCode;
import cn.com.xcsa.api.util.Page;
import cn.com.xcsa.gateway.domain.po.OrgUser;
import cn.com.xcsa.gateway.domain.po.User;
import cn.com.xcsa.gateway.domain.po.UserSpace;
import cn.com.xcsa.gateway.domain.vo.UserVo;
import cn.com.xcsa.gateway.service.OrgUserService;
import cn.com.xcsa.gateway.service.UserService;
import cn.com.xcsa.gateway.service.UserSpaceService;
import cn.com.xcsa.gateway.util.GroupAdd;
import cn.com.xcsa.gateway.util.GroupDelete;
import cn.com.xcsa.gateway.util.GroupList;
import cn.com.xcsa.gateway.util.GroupUpdate;
import cn.com.xcsa.gateway.util.SourceEnum;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
* <p> 前端控制器</p>.
*
* @author wuhui
* @since 2023-07-24
*/
@RestController
@RequestMapping("/api/v1/user")
public class UserController {


    @Resource
    private SecurityApi securityApi;

    @Resource
    private RedisCache redisCache;

    @Resource
    private UserService userService;

    @Resource
    private UserSpaceService userSpaceService;

    @Resource
    private OrgUserService orgUserService;

    //数据状态为删除状态
    private static final Integer FLAG_DELETE = 1;
    //数据状态为未删除状态
    private static final Integer FLAG_UN_DELETE = 0;
    //激活状态
    private static final String ACTIVATE_STATUS = "activateStatus";
    //启/禁用状态
    private static final String USER_STATUS = "userStatus";

    //默认密码
    private  static final String DEFAULT_PASSWORD = "xcsa123456";


    /**
     * 根据临时token获取用户信息.
     * @param accessToken
     * @return 返回user.
     */
    @GetMapping("/info")
    public Object info(@RequestParam("accessToken") String accessToken) {
        if (!redisCache.hasKey(accessToken)) {
            throw new ApiRuntimeException(InfoCode.PARAMS_NOT_EXIST);
        }
        String userJson = redisCache.getCacheObject(accessToken);
        User user = JSONObject.parseObject(userJson, User.class);
        user.setToken(securityApi.login(
                user.getTenantId(), user.getId(), user.getLoginName(), user.getSource()));
        return user;
    }

    /**
     * 获取用户列表信息.
     *
     * @param userVo
     * @return 列表数据.
     */
    @GetMapping("/list")
    public Object list(@RequestBody @Validated(GroupList.class) UserVo userVo) {
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery(User.class)
                .orderBy(true, false, User::getSortNo)
                .eq(User::getDeleted, userVo.getDeleted());
        Page<User> page = userService.searchPage(userVo.getPageNo(),
                userVo.getPageSize(), wrapper);
        if (ObjectUtil.isEmpty(page.getContent())) {
            return page;
        }
        //获取用户的id集合
        List<Long> userIds = page.getContent().stream()
                .map(User::getId)
                .collect(Collectors.toList());
        //获取userIds里的空间list数据
        LambdaQueryWrapper<UserSpace> queryWrapper =
                Wrappers.lambdaQuery(UserSpace.class).in(UserSpace::getUserId, userIds);
        List<UserSpace> spaces = userSpaceService.list(queryWrapper);
        page.getContent().forEach(user -> {
            Optional<UserSpace> userSpaceOptional = spaces.stream()
                    .filter(space -> space.getUserId().equals(user.getId()))
                    .findFirst();
            userSpaceOptional.ifPresent(userSpace -> {
                user.setTotalSpaceQuota(userSpace.getTotalSpaceQuota());
            });
        });
        return page;
    }

    /**
     * 添加用户.
     * @param userVo
     * @return 添加结果.
     */
    @PostMapping("/add")
    public Boolean add(@RequestBody @Validated(GroupAdd.class) UserVo userVo) {
        //TODO 确认认证源方式 不包含本地化的，例如:LDAP\第三方 无法添加
        User user = BeanUtil.copyProperties(userVo, User.class, "id");
        LocalDateTime now = LocalDateTime.now();
        user.setCreateTime(now)
            .setPassword(SecureUtil.md5(DEFAULT_PASSWORD))
            .setSource(SourceEnum.LOCAL.getValue());
        Boolean saveUserFlag = userService.save(user);
        //添加用户空间信息
        UserSpace userSpace = BeanUtil.copyProperties(userVo, UserSpace.class);
        userSpace.setCreateTime(now)
                .setUserId(user.getId());
        Boolean saveUSpaceFlag = userSpaceService.save(userSpace);
        //保存用户机构关联信息
        OrgUser orgUser = new OrgUser();
        orgUser.setOrgId(userVo.getOrgId())
                .setTenantId(userVo.getTenantId())
                .setUserId(user.getId());
        Boolean saveOrgUserFlag = orgUserService.save(orgUser);
        if (!saveUserFlag || !saveUSpaceFlag || !saveOrgUserFlag) {
            throw new ApiRuntimeException(InfoCode.FAIL);
        }
        return true;
    }


    /**
     * 编辑用户.
     * @param userVo
     * @return 编辑结果.
     */
    @PostMapping("/update")
    public Boolean update(@RequestBody @Validated(GroupUpdate.class) UserVo userVo) {
        //TODO 确认认证源方式 不包含本地化的，例如:LDAP\第三方 无法修改
        //修改用户信息
        User user = BeanUtil.copyProperties(userVo, User.class);
        user.setUpdateTime(LocalDateTime.now());
        Boolean userFlag = userService.update(user,
                new UpdateWrapper<User>().lambda().eq(User::getId, user.getId()));
        //修改用户空间存储信息
        Boolean spaceFlag = userSpaceService.update(new UpdateWrapper<UserSpace>().
                lambda().
                eq(UserSpace::getUserId, userVo.getId())
                .set(UserSpace::getTotalSpaceQuota,
                        userVo.getTotalSpaceQuota()));
        if (!userFlag || !spaceFlag) {
            throw new ApiRuntimeException(InfoCode.FAIL);
        }
        return true;
    }


    /**
     * 切换用户激活&(启|禁)用状态.
     *
     * @param id
     * @param type
     * @return 切换结果.
     */
    @PostMapping("/status")
    public Boolean status(@RequestParam("id") Integer id, @RequestParam("type") String type) {
        if (ObjectUtil.isEmpty(id) || StrUtil.isBlankIfStr(type)) {
            throw new ApiRuntimeException(InfoCode.PARAMS_NOT_EXIST);
        }
        User user = userService.getById(id);
        if (BeanUtil.isEmpty(user)) {
            throw new ApiRuntimeException(InfoCode.USER_IS_NULL);
        }
        //根据type值以及user现有状态确定要切换的状态值 activateStatus：激活状态 userStatus：启禁用状态
        Integer newStatus = ACTIVATE_STATUS.equals(type)
                ? (user.getActivateStatus() == User.ActivateStatus.ACTIVATED.getValue()
                ? User.ActivateStatus.UNACTIVATED.getValue()
                : User.ActivateStatus.ACTIVATED.getValue())
                : (user.getUserStatus() == User.UserStatus.DISABLED.getValue()
                ? User.UserStatus.DEFAULT.getValue() : User.UserStatus.DISABLED.getValue());
        //Integer newStatus = null;
       /* if (ACTIVATE_STATUS.equals(type)) {
            newStatus = user.getActivateStatus() ==
                    User.ActivateStatus.ACTIVATED.getValue() ?
                    User.ActivateStatus.UNACTIVATED.getValue()
                    : User.ActivateStatus.ACTIVATED.getValue();
        }else {
            newStatus = user.getUserStatus() ==
                    User.UserStatus.DISABLED.getValue() ?
                    User.UserStatus.DEFAULT.getValue() : User.UserStatus.DISABLED.getValue();
        }*/
        return userService.update(new UpdateWrapper<User>()
                .lambda()
                .eq(User::getId, id)
                .set(ACTIVATE_STATUS.equals(type)
                        ? User::getActivateStatus : User::getUserStatus, newStatus));
    }


    /**
     * 移动用户.
     *
     * @param orgId
     * @param oldOrgId
     * @param id
     * @return 移动结果.
     */
    @PostMapping("/move")
    public Boolean move(@RequestParam("orgId") Long orgId,
                        @RequestParam("oldOrgId") Long oldOrgId,
                        @RequestParam("id") Long id) {
        if (ObjectUtil.hasEmpty(oldOrgId, orgId, id)) {
            throw new ApiRuntimeException(InfoCode.PARAMS_ERROR);
        }
        OrgUser orgUser = new OrgUser();
        orgUser.setOrgId(orgId);
        return orgUserService.update(orgUser,
                new UpdateWrapper<OrgUser>().lambda().eq(OrgUser::getUserId, id)
                .eq(OrgUser::getOrgId, oldOrgId));
    }

    /**
     * 删除用户或还原用户或在回收站里删除用户.
     * @param userVo
     * @return 操作结果.
     */
    @PostMapping("/delete")
    public Boolean delete(@RequestBody @Validated(GroupDelete.class) UserVo userVo) {
        return userService.update(new UpdateWrapper<User>()
                .lambda()
                .eq(User::getId, userVo.getId())
                .set(User::getDeleted, userVo.getDeleted()));
    }

    /**
     * 重置用户默认密码.
     * @param id
     * @param response
     * @return 重置结果.
     */
    @PostMapping("/reset")
    public Object reset(@RequestParam("id") @NotNull(message = "用户主键信息必输输入") Long id,
                        HttpServletResponse response)throws Exception {
        return userService.update(new UpdateWrapper<User>()
                .lambda()
                .eq(User::getId, id)
                .set(User::getPassword, SecureUtil.md5(DEFAULT_PASSWORD))
        );
    }
}
