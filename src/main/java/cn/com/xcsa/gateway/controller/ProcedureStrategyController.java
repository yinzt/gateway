package cn.com.xcsa.gateway.controller;

import cn.com.xcsa.api.exception.ApiRuntimeException;
import cn.com.xcsa.api.framework.security.ClaimInfo;
import cn.com.xcsa.api.framework.security.SecurityApi;
import cn.com.xcsa.api.util.Constants;
import cn.com.xcsa.api.util.InfoCode;
import cn.com.xcsa.framework.annotation.Auth;
import cn.com.xcsa.gateway.domain.po.ProcedureStrategy;
import cn.com.xcsa.gateway.domain.vo.ProcedureStrategyVo;
import cn.com.xcsa.gateway.service.ProcedureStrategyService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.StrUtil;
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
import org.springframework.web.bind.annotation.RequestHeader;
import javax.annotation.Resource;
/**
 * <p>程序策略配置表</p>.
 */

@Auth
@RestController
@RequestMapping("/api/v1/procedureStrategy")
public class ProcedureStrategyController {
    @Resource
    private ProcedureStrategyService procedureStrategyService;

    @Resource
    private SecurityApi securityApi;
    /**
     * 分页查询.
     * @param pageNo
     * @param pageSize
     * @param name
     * @return 结果
     * @param token
     */
    @GetMapping("/page")
    public Object page(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                       @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                       String name,
                       @RequestHeader(Constants.AUTHORIZATION)String token) {
                       ClaimInfo claimInfo = securityApi.getClaimInfo(token);
                        Long tenantId = claimInfo.getTenantId();
        LambdaQueryWrapper<ProcedureStrategy> lqw = Wrappers.lambdaQuery(ProcedureStrategy.class)
                .eq(ProcedureStrategy::getTenantId, tenantId)
                .like(StrUtil.isNotBlank(name), ProcedureStrategy::getBinaryName, name);
        return procedureStrategyService.searchPage(pageNo, pageSize, lqw);

    }

    /**
     * 新增程序策略配置表.
     * @param procedureStrategyVo
     * @return 结果
     */
    @PostMapping("/add")
    public Boolean add(@RequestBody @Validated ProcedureStrategyVo procedureStrategyVo) {
        ProcedureStrategy psy = BeanUtil
                .copyProperties(procedureStrategyVo, ProcedureStrategy.class);
        psy.setCreateTime(LocalDateTime.now().toDate());
        return procedureStrategyService.save(psy);
    }

    /**
     * 删除程序策略配置表.
     * @param id
     * @return 结果
     */
    @PostMapping("/del")
    public Boolean del(@RequestParam("id") Long id) {
        if (id == null) {
            throw new ApiRuntimeException(InfoCode.PARAMS_ERROR);
        }
        return procedureStrategyService.removeById(id);
    }

    /**
     * 修改程序策略配置表.
     * @param procedureStrategyVo
     * @return 结果
     */
    @PostMapping("/update")
    public Boolean update(@RequestBody  ProcedureStrategyVo procedureStrategyVo) {
        if (procedureStrategyVo.getId() == null) {
            throw new ApiRuntimeException(InfoCode.PARAMS_ERROR);
        }
        ProcedureStrategy psy = procedureStrategyService.getById(procedureStrategyVo.getId());
        BeanUtil.copyProperties(procedureStrategyVo, psy, CopyOptions.create()
                .setIgnoreNullValue(true));
        return procedureStrategyService.updateById(psy);
    }
}
