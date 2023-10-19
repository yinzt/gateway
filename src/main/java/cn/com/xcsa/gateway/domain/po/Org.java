package cn.com.xcsa.gateway.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
* <p> 组织机构表</p>.
*
* @author huyu
* @since 2023-10-16
*/
@Getter
@Setter
@TableName("sys_org")
public class Org extends Model<Org> {

    private static final long serialVersionUID = 1L;

    /**
    * 主键.
    */
    @TableId(value = "id", type = IdType.AUTO)
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
    @TableLogic
    private Integer deleted;

    /**
    * 组织机构名称.
    */
    private String orgName;

    /**
    * 上级组织机构id.
    */
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


    /**
    * 返回ID.
    * @return id
    */
    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
