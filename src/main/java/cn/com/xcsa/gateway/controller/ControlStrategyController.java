package cn.com.xcsa.gateway.controller;

import cn.com.xcsa.api.exception.ApiRuntimeException;
import cn.com.xcsa.api.util.InfoCode;
import cn.com.xcsa.framework.annotation.Auth;
import cn.com.xcsa.gateway.domain.po.ControlStrategy;
import cn.com.xcsa.gateway.domain.vo.ControlStrategyVo;
import cn.com.xcsa.gateway.service.ControlStrategyService;
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
import javax.annotation.Resource;
/**
 *
 * <p>登录控制策略规则配置表</p>.
 *
 */

@Auth
@RestController
@RequestMapping("/api/v1/controlStrategy")
public class ControlStrategyController {
    @Resource
    private ControlStrategyService controlStrategyService;
    /**
     * 分页查询.
     * @param pageNo
     * @param pageSize
     * @param cid
     * @return 结果
     * @param name
     */
    @GetMapping("/page")
    public Object page(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                       @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                       String name, Long cid) {
        if (cid == null) {
            throw new ApiRuntimeException(InfoCode.PARAMS_ERROR);
        }
        LambdaQueryWrapper<ControlStrategy> lqw = Wrappers.lambdaQuery(ControlStrategy.class)
                .like(StrUtil.isNotBlank(name), ControlStrategy::getControlName, name)
                .eq(ControlStrategy::getCid, cid);
        return controlStrategyService.searchPage(pageNo, pageSize, lqw);
    }

    /**
     * 新增登录控制策略规则配置表.
     * @param controlStrategyVo
     * @return 结果
     */
    @PostMapping("/add")
    public Boolean add(@RequestBody @Validated ControlStrategyVo controlStrategyVo) {
        ControlStrategy controlStrategy = BeanUtil.
                copyProperties(controlStrategyVo, ControlStrategy.class);
        controlStrategy.setCreateTime(LocalDateTime.now().toDate());
        return controlStrategyService.save(controlStrategy);
    }

    /**
     * 删除登录控制策略规则配置表.
     * @param id
     * @return 结果
     */
    @PostMapping("/del")
    public Boolean del(@RequestParam("id") Long id) {
        if (id == null) {
            throw new ApiRuntimeException(InfoCode.PARAMS_ERROR);
        }
        return controlStrategyService.removeById(id);
    }

    /**
     * 修改登录控制策略规则配置表.
     * @param controlStrategyVo
     * @return 结果
     */
    @PostMapping("/update")
    public Boolean update(@RequestBody  ControlStrategyVo controlStrategyVo) {
        if (controlStrategyVo.getId() == null) {
            throw new ApiRuntimeException(InfoCode.PARAMS_ERROR);
        }
        ControlStrategy controlStrategy = controlStrategyService
                .getById(controlStrategyVo.getId());
         BeanUtil.copyProperties(controlStrategyVo, controlStrategy, CopyOptions.create()
                 .setIgnoreNullValue(true));
        return controlStrategyService.updateById(controlStrategy);
    }
}
