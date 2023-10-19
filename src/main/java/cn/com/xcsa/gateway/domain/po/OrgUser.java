package cn.com.xcsa.gateway.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
* <p> 组织机构和用户信息关联表</p>.
*
* @author huyu
* @since 2023-09-22
*/
@Getter
@Setter
@TableName("sys_org_user")
@Accessors(chain = true)
public class OrgUser extends Model<OrgUser> {

    private static final long serialVersionUID = 1L;

    /**
    * 主键.
    */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
    * 关联sys_org表org_id.
    */
    private Long orgId;

    /**
    * 关联sys_user表user_id.
    */
    private Long userId;

    /**
    * 租户id.
    */
    private Long tenantId;


    /**
    * 返回ID.
    * @return id
    */
    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
