package cn.com.xcsa.gateway.controller;
import cn.com.xcsa.api.exception.ApiRuntimeException;
import cn.com.xcsa.api.framework.security.SecurityApi;
import cn.com.xcsa.api.util.InfoCode;
import cn.com.xcsa.framework.annotation.Auth;
import cn.com.xcsa.gateway.domain.po.AccessScope;
import cn.com.xcsa.gateway.domain.vo.AccessScopeVo;
import cn.com.xcsa.gateway.service.AccessScopeService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.joda.time.LocalDateTime;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.annotation.Resource;

/**
 *<p>访问安全配置中的网络访问区域配置表</p>.
 *
 */
@Auth
@RestController
@RequestMapping("/api/v1/accessScope")
public class AccessScopeController {
    @Resource
    private AccessScopeService accessScopeService;

    @Resource
    private SecurityApi securityApi;
    /**
     * 分页查询.
     * @param pageNo
     * @param pageSize
     * @param sid
     * @return 结果
     */
    @GetMapping("/page")
    public Object page(
                       @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                       @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                       Long sid) {
        if (sid == null) {
            throw new ApiRuntimeException(InfoCode.PARAMS_ERROR);
        }
        LambdaQueryWrapper<AccessScope> lqw = Wrappers.lambdaQuery(AccessScope.class)
                .eq(AccessScope::getSid, sid);
        return accessScopeService.searchPage(pageNo, pageSize, lqw);

    }

    /**
     * 新增网络访问区域配置表.
     * @param accessScopeVo
     * @return 结果
     */
    @PostMapping("/add")
    public Boolean add(@RequestBody @Validated AccessScopeVo accessScopeVo) {
            AccessScope accessScope = BeanUtil.copyProperties(accessScopeVo, AccessScope.class);
            accessScope.setCreateTime(LocalDateTime.now().toDate());
            return accessScopeService.save(accessScope);
    }

    /**
     * 删除网络访问区域配置表.
     * @param id
     * @return 结果
     */
    @PostMapping("/del")
    public Boolean del(@RequestParam("id") Long id) {
        if (id == null) {
            throw new ApiRuntimeException(InfoCode.PARAMS_ERROR);
        }
        return accessScopeService.removeById(id);
    }

    /**
     * 修改网络访问区域配置表.
     * @param accessScopeVo
     * @return 结果
     */
    @PostMapping("/update")
    public Boolean update(@RequestBody AccessScopeVo accessScopeVo) {
        if (accessScopeVo.getId() == null) {
            throw new ApiRuntimeException(InfoCode.PARAMS_ERROR);
        }
        AccessScope accessScope = accessScopeService.getById(accessScopeVo.getId());
        BeanUtil.copyProperties(accessScopeVo, accessScope,
                CopyOptions.create().setIgnoreNullValue(true));
        return accessScopeService.updateById(accessScope);
    }
}
