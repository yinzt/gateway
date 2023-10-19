package cn.com.xcsa.gateway.domain.vo;

import cn.com.xcsa.gateway.util.GroupAdd;
import cn.com.xcsa.gateway.util.GroupUpdate;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class OrgVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键.
     */
    @TableId(value = "id", type = IdType.AUTO)
    @NotNull(message = "机构id信息必须填写", groups = GroupUpdate.class)
    private Long id;

    /**
     * 租户id.
     */
    private Long tenantId;

    /**
     * 创建时间.
     */
    private LocalDateTime createTime;

    /**
     * 创建者id，关联sys_user表id.
     */
    private Long createId;

    /**
     * 修改时间.
     */
    private LocalDateTime updateTime;

    /**
     * 是否删除： 0：否 1：是.
     */
    private Integer deleted;

    /**
     * 组织机构名称.
     */
    @NotBlank(message = "机构名称必须填写", groups = GroupAdd.class)
    private String orgName;

    /**
     * 上级组织机构id.
     */
    @NotNull(message = "上级机构信息必须填写", groups = GroupAdd.class)
    private Long pid;


    /**
     * 上级机构树形数据.
     */
    private String pids;

    /**
     * 排序权重.
     */
    private Integer sortNo;

    /**
     * 第三方部门id.
     */
    private String thirdId;
    /**
     * 第三方部门 0:本系统 1:LDAP 2:dingding 3:wechat 4:feishu.
     */
    private Integer thirdType;



}
