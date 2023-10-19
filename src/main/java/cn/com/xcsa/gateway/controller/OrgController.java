package cn.com.xcsa.gateway.controller;

import cn.com.xcsa.gateway.domain.vo.OrgVo;
import cn.com.xcsa.gateway.util.GroupAdd;
import cn.com.xcsa.gateway.util.GroupUpdate;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.validation.annotation.Validated;
import cn.com.xcsa.gateway.domain.po.Org;
import cn.com.xcsa.gateway.service.OrgService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
* <p>组织机构表 前端控制器</p>.
*
* @author huyu
* @since 2023-09-20
*/
@RestController
@RequestMapping("/api/v1/org")
public class OrgController {


    @Resource
    private OrgService orgService;

    private static final Integer FLAG_DELETE = 1;

    /**
     * 获取机构列表信息.
     *
     * @param orgVo
     * @return 机构列表.
     */
    @GetMapping("/list")
    public Object list(@RequestBody OrgVo orgVo) {
        LambdaQueryWrapper<Org> wrapper = Wrappers.lambdaQuery(Org.class);
        wrapper.orderBy(true, false, Org::getSortNo)
                .eq(ObjectUtil.isNotEmpty(orgVo.getPid()),
                        Org::getPid, ObjectUtil.isEmpty(orgVo.getPid()) ? 0 : orgVo.getPid())
                .eq(ObjectUtil.isNotEmpty(orgVo.getTenantId()),
                        Org::getTenantId, orgVo.getTenantId());
        return orgService.list(wrapper);
    }


    /**
     * 添加机构信息.
     * @param orgVo
     * @return 添加结果.
     */
    @PostMapping("/add")
    public Boolean add(@RequestBody @Validated(GroupAdd.class) OrgVo orgVo) {
        Org org = BeanUtil.copyProperties(orgVo, Org.class, "id");
        org.setCreateTime(LocalDateTime.now());
        return orgService.save(org);
    }


    /**
     * 修改机构信息.
     *
     * @param orgVo
     * @return 修改结果.
     */
    @PostMapping("/update")
    public Boolean update(@RequestBody @Validated(GroupUpdate.class) OrgVo orgVo) {
        Org org = BeanUtil.copyProperties(orgVo, Org.class);
        org.setUpdateTime(LocalDateTime.now());
        return orgService.saveOrUpdate(org);
    }


    /**
     * 删除组织机构.
     * @param id
     * @return 删除结果.
     */
    @PostMapping("/delete")
    public Boolean delete(@RequestParam("id") Integer id) {
        return orgService.update(new UpdateWrapper<Org>().
                lambda().in(Org::getId, id)
                .set(Org::getDeleted, FLAG_DELETE));
    }


}
