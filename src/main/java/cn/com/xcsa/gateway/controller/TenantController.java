package cn.com.xcsa.gateway.controller;


import cn.com.xcsa.api.dto.SettingsDto;
import cn.com.xcsa.api.dto.TenantSettingsDto;
import cn.com.xcsa.api.exception.ApiRuntimeException;
import cn.com.xcsa.api.framework.platform.SettingsApi;
import cn.com.xcsa.api.framework.platform.TenantSettingsApi;
import cn.com.xcsa.api.util.InfoCode;
import cn.com.xcsa.api.util.Page;
import cn.com.xcsa.api.util.SettingsKey;
import cn.com.xcsa.gateway.domain.po.Tenant;
import cn.com.xcsa.gateway.domain.po.TenantSpace;
import cn.com.xcsa.gateway.domain.vo.TenantVo;
import cn.com.xcsa.gateway.service.TenantService;
import cn.com.xcsa.gateway.service.TenantSpaceService;
import cn.com.xcsa.gateway.util.GroupAdd;
import cn.com.xcsa.gateway.util.GroupList;
import cn.com.xcsa.gateway.util.GroupUpdate;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSONObject;
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
import java.util.Optional;
import java.util.stream.Collectors;

/**
* <p> 前端控制器</p>.
*
* @author wuhui
* @since 2023-07-24
*/
@RestController
@RequestMapping("/api/v1/tenant")
public class TenantController {


    @Resource
    private TenantService tenantService;

    @Resource
    private TenantSpaceService tenantSpaceService;

    @Resource
    private SettingsApi settingsApi;

    @Resource
    private TenantSettingsApi tenantSettingsApi;

    //数据状态为删除状态
    private static final Integer FLAG_DELETE = 1;

    private static final Integer FLAG_UN_DELETE = 0;

    //默认配置速度限制
    private static final String DEFAULT_SPEED_CONFIG = "speedDefaultConfig";
    //租户总存储空间键
    private static final String TOTAL_SPACE_QUOTA = "totalSpaceQuota";
    /**
     * 分页查询租户列表数据.
     * @param tenantVo
     * @return 分页数据.
     */
    @GetMapping("/list")
    public Object list(@RequestBody @Validated(GroupList.class) TenantVo tenantVo) {
        LambdaQueryWrapper<Tenant> wrapper =
                Wrappers.lambdaQuery(Tenant.class)
                .eq(Tenant::getDeleted, FLAG_UN_DELETE);
        Page<Tenant> page = tenantService.searchPage(tenantVo.getPageNo(),
                tenantVo.getPageSize(), wrapper);
        //获取租户的id集合
        List<Long> tenantIds = page.getContent().stream()
                .map(Tenant::getId)
                .collect(Collectors.toList());
        //获取tenantIds里的空间配额数据集合
        LambdaQueryWrapper<TenantSpace> queryWrapper =
                Wrappers.lambdaQuery(TenantSpace.class).in(TenantSpace::getTenantId, tenantIds);
        List<TenantSpace> tenantSpaces = tenantSpaceService.list(queryWrapper);
        //组合数据
        page.getContent().forEach(tenant -> {
            Optional<TenantSpace> userSpaceOptional = tenantSpaces.stream()
                    .filter(space -> space.getTenantId().equals(tenant.getId()))
                    .findFirst();
            //匹配数据放入
            userSpaceOptional.ifPresent(tenantSpace -> {
                tenant.setTotalSpaceQuota(tenantSpace.getTotalSpaceQuota());
            });
            userSpaceOptional.ifPresent(tenantSpace -> {
                tenant.setUseSpaceQuota(tenantSpace.getUseSpaceQuota());
            });
        });

        return page;
    }


    /**
     * 添加租户信息.
     * @param tenantVo
     * @return 添加结果.
     */
    @PostMapping("/add")
    public Boolean add(@RequestBody @Validated(GroupAdd.class) TenantVo tenantVo) {
        //存储租户对象
        Tenant tenant = BeanUtil.copyProperties(tenantVo, Tenant.class, "id");
        tenant.setCreateTime(LocalDateTime.now());
        Boolean tenantFlag = tenantService.save(tenant);
        //存储租户空间信息
        TenantSpace tenantSpace = BeanUtil.copyProperties(tenantVo, TenantSpace.class);
        tenantSpace.setTenantId(tenant.getId())
                .setCreateTime(LocalDateTime.now())
                .setTotalSpaceQuota(
                        JSONObject.parseObject(tenantVo.getSpeedDefaultConfig()).
                                getLong(TOTAL_SPACE_QUOTA));
        Boolean tenantSpaceFlag = tenantSpaceService.save(tenantSpace);
        //存储租户限速信息
        if (ObjectUtil.isEmpty(tenantVo.getSpeedDefaultConfig())) {
            throw new ApiRuntimeException(InfoCode.FAIL);
        }
        SettingsDto info = settingsApi.settingsInfo(SettingsKey.TENANT_DEFAULT_CONFIG);
        Long added = tenantSettingsApi.addSettings(tenant.getId(), info.getParamName(),
                info.getParamKey(), tenantVo.getSpeedDefaultConfig(),
                info.getRemark());
        if (!tenantFlag || !tenantSpaceFlag || added < 1) {
            throw new ApiRuntimeException(InfoCode.FAIL);
        }
        return true;
    }

    /**
     * 修改租户信息.
     *
     * @param tenantVo
     * @return 修改结果.
     */
    @PostMapping("/update")
    public Boolean update(@RequestBody @Validated(GroupUpdate.class) TenantVo tenantVo) {
        //修改租户信息
        Tenant tenant = BeanUtil.copyProperties(tenantVo, Tenant.class);
        Boolean tenantFlag = tenantService.update(tenant,
                new UpdateWrapper<Tenant>().lambda().eq(Tenant::getId, tenant.getId()));
        //修改租户空间存储信息
        Boolean spaceFlag = tenantSpaceService.update(new UpdateWrapper<TenantSpace>().
                lambda().
                eq(TenantSpace::getTenantId, tenantVo.getId())
                .set(TenantSpace::getTotalSpaceQuota,
                        JSONObject.parseObject(tenantVo.getSpeedDefaultConfig()).
                                getLong(TOTAL_SPACE_QUOTA)));
        //修改租户限速信息
        if (ObjectUtil.isEmpty(tenantVo.getSpeedDefaultConfig())) {
            throw new ApiRuntimeException(InfoCode.FAIL);
        }
        TenantSettingsDto info = tenantSettingsApi.settingsInfo(tenantVo.getId(),
                SettingsKey.TENANT_DEFAULT_CONFIG);
        Boolean tenantSettingFlag = tenantSettingsApi.updateSettings(tenantVo.getId(),
                SettingsKey.TENANT_DEFAULT_CONFIG,
                info.getParamName(), tenantVo.getSpeedDefaultConfig(), info.getRemark());
        if (!tenantFlag || !spaceFlag || !tenantSettingFlag) {
            throw new ApiRuntimeException(InfoCode.FAIL);
        }
        return true;
    }

    /**
     * 修改租户信息删除状态.
     *
     * @param tenantVo
     * @return 处理结果.
     */
    @PostMapping("/delete")
    public Boolean delete(@RequestBody @Validated(GroupUpdate.class) TenantVo tenantVo) {
        return tenantService.update(new UpdateWrapper<Tenant>().
                lambda().
                eq(Tenant::getId, tenantVo.getId())
                .set(Tenant::getDeleted, FLAG_DELETE));
    }


    /**
     * 切换租户状态.
     * @param tenantVo
     * @return 切换结果.
     */
    @PostMapping("/toggle")
    public Boolean toggle(@RequestBody  @Validated(GroupUpdate.class) TenantVo tenantVo) {
        Tenant tenant = tenantService.getById(tenantVo.getId());
        if (BeanUtil.isEmpty(tenant)) {
            throw new ApiRuntimeException(InfoCode.FAIL);
        }
        // 切换租户状态
        Integer newStatus = tenant.getStatus()
                == Tenant.Status.normal.getVarValue()
                ? Tenant.Status.forbidden.getVarValue() : Tenant.Status.normal.getVarValue();

        return tenantService.update(new UpdateWrapper<Tenant>()
                .lambda()
                .eq(Tenant::getId, tenantVo.getId())
                .set(Tenant::getStatus, newStatus));
    }


    /**
     * 租户默认配置项.
     *
     * @return 默认配置信息.
     */
    @GetMapping("/config")
    public Object config() {
        //获取系统默认配置
        SettingsDto info = settingsApi.settingsInfo(SettingsKey.TENANT_DEFAULT_CONFIG);
        if (BeanUtil.isEmpty(info)) {
            throw new ApiRuntimeException(InfoCode.USER_IS_NULL);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(DEFAULT_SPEED_CONFIG, JSONObject.parseObject(info.getParamValue()));
        return jsonObject;
    }



}
