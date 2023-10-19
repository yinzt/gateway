package cn.com.xcsa.gateway.controller;

import cn.com.xcsa.api.dto.SettingsDto;
import cn.com.xcsa.api.exception.ApiRuntimeException;
import cn.com.xcsa.api.framework.platform.DeployType;
import cn.com.xcsa.api.framework.platform.PlatformApi;
import cn.com.xcsa.api.framework.platform.SettingsApi;
import cn.com.xcsa.api.framework.platform.TenantSettingsApi;
import cn.com.xcsa.api.util.InfoCode;
import cn.com.xcsa.api.util.SettingsKey;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;



@RestController
@RequestMapping("/api/v1/application")
public class ApplicationController {

    @Resource
    private SettingsApi settingsApi;

    @Resource
    private PlatformApi platformApi;

    @Resource
    private TenantSettingsApi tenantSettingsApi;

    /**
     * 查询登录配置 .
     * @return 配置实体 .
     */
    @GetMapping("/config")
    public Object config() {

        JSONObject object = new JSONObject();
        DeployType deployInfo = platformApi.deployInfo();
        object.put("adminUrl",
                getConfigValue(deployInfo, SettingsKey.ADMIN_URL)); //管理后台地址
        object.put("captcha",
                BooleanUtil.toBoolean(getConfigValue(deployInfo, SettingsKey.CAPTCHA_CONFIG)));
        object.put("account",
                StrUtil.split(getConfigValue(deployInfo, SettingsKey.LOGIN_FIELD), ","));
        object.put("scan", "dingding"); //扫码的登录如：weixin，dingding，feishu
        object.put("sms", true); //是否支持短信登录
        return object;
    }


    private String getConfigValue(DeployType deployInfo, String key) {
        //此配置需登录前查询 因为租户id是登录后获取的 所以租户的配置不查询
       /* if (!DeployType.LOCAL.equals(deployInfo)) {
            TenantSettingsDto tenantSettingsDto = tenantSettingsApi.settingsInfo(null, key);
            return tenantSettingsDto.getParamValue();
        }*/
        SettingsDto info = settingsApi.settingsInfo(key);
        if (BeanUtil.isEmpty(info)) {
            throw new ApiRuntimeException(InfoCode.USER_IS_NULL);
        }

        return info.getParamValue();
    }




}
