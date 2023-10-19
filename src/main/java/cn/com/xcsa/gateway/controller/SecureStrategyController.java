package cn.com.xcsa.gateway.controller;

import cn.com.xcsa.api.exception.ApiRuntimeException;
import cn.com.xcsa.api.util.InfoCode;
import cn.com.xcsa.framework.annotation.Auth;
import cn.com.xcsa.gateway.domain.po.SecureStrategy;
import cn.com.xcsa.gateway.domain.vo.SecureStrategyVo;
import cn.com.xcsa.gateway.service.SecureStrategyService;
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
 * <p>防护策略配置表</p>.
 *
 */

@Auth
@RestController
@RequestMapping("/api/v1/secureStrategy")
public class SecureStrategyController {
    @Resource
    private SecureStrategyService secureStrategyService;
    /**
     * 分页查询.
     * @param pageNo
     * @param pageSize
     * @param uid
     * @return 结果
     * @param name
     */
    @GetMapping("/page")
    public Object page(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                       @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                       String name, Long uid) {
        if (uid == null) {
            throw new ApiRuntimeException(InfoCode.PARAMS_ERROR);
        }
        LambdaQueryWrapper<SecureStrategy> lqw = Wrappers.lambdaQuery(SecureStrategy.class)
                .like(StrUtil.isNotBlank(name), SecureStrategy::getProtectionName, name)
                .eq(SecureStrategy::getUid, uid);
        return secureStrategyService.searchPage(pageNo, pageSize, lqw);

    }

    /**
     * 新增防护策略配置表.
     * @param secureStrategyVo
     * @return 结果
     */
    @PostMapping("/add")
    public Boolean add(@RequestBody @Validated SecureStrategyVo secureStrategyVo) {
        SecureStrategy sty = BeanUtil.copyProperties(secureStrategyVo, SecureStrategy.class);
        sty.setCreateTime(LocalDateTime.now().toDate());
        return secureStrategyService.save(sty);
    }

    /**
     * 删除防护策略配置表.
     * @param id
     * @return 结果
     */
    @PostMapping("/del")
    public Boolean del(@RequestParam("id") Long id) {
        if (id == null) {
            throw new ApiRuntimeException(InfoCode.PARAMS_ERROR);
        }
        return secureStrategyService.removeById(id);
    }

    /**
     * 修改防护策略配置表.
     * @param secureStrategyVo
     * @return 结果
     */
    @PostMapping("/update")
    public Boolean update(@RequestBody SecureStrategyVo secureStrategyVo) {
        if (secureStrategyVo.getId() == null) {
            throw new ApiRuntimeException(InfoCode.PARAMS_ERROR);
        }
        SecureStrategy sty = secureStrategyService.getById(secureStrategyVo.getId());
        BeanUtil.copyProperties(secureStrategyVo, sty, CopyOptions.create()
                .setIgnoreNullValue(true));
        return secureStrategyService.updateById(sty);
    }
}
