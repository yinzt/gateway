package cn.com.xcsa.gateway.controller;


import cn.com.xcsa.api.framework.platform.SettingsApi;
import cn.com.xcsa.gateway.domain.vo.ConfigVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


import javax.annotation.Resource;



@RestController
@RequestMapping("/api/v1/config")
public class ConfigController {

    @Resource
    private SettingsApi settingsApi;


    /**
     * 添加配置方法.
     * @param configVo
     * @return 添加成功返回true 添加失败返回false
     */
    @PostMapping("/add")
    public Object add(@RequestBody @Validated ConfigVo configVo) {

        Long aLong = settingsApi.addSettings(configVo.getParamName(), configVo.getParamKey(),
                configVo.getParamValue(), configVo.getRemark());
        if (aLong > 1) {
            return true;
        }
        return false;
    }

    /**
     * 修改配置.
     * @param configVo
     * @return 变更数据条数
     */
    @PostMapping("/update")
    public Object update(@RequestBody @Validated ConfigVo configVo) {
         return settingsApi.updateSettings(configVo.getParamKey(), configVo.getParamName(),
                configVo.getParamValue(), configVo.getRemark());
    }


    /**
     * 根据指定参数查询.
     * @param paramKey
     * @param paramName
     * @return 符合条件的list
     */
    @GetMapping("/list")
    public Object list(String paramKey, String paramName) {
        return settingsApi.settingsList(paramKey, paramName);
    }

    /**
     * 删除.
     * @param paramKey
     * @return 成功数据条数
     */
    @PostMapping("/delete")
    public Object delete(String paramKey) {
        return settingsApi.deleteSettings(paramKey);
    }



}
