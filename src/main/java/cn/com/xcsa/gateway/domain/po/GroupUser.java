package cn.com.xcsa.gateway.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
* <p> 用户组关联用户表</p>.
*
* @author huyu
* @since 2023-09-24
*/
@Getter
@Setter
@TableName("sys_group_user")
public class GroupUser extends Model<GroupUser> {

    private static final long serialVersionUID = 1L;

    /**
    * 主键.
    */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
    * 关联租户表id.
    */
    private Long tenantId;

    /**
    * 关联sys_group表的id.
    */
    private Long groupId;

    /**
    * 关联sys_user表的id.
    */
    private Long userId;

    /**
    * 创建时间.
    */
    private LocalDateTime createTime;

    /**
    * 是否删除 0：否 1：是.
    */
    @TableLogic
    private Integer deleted;


    /**
     * 用户昵称.
     */
    @TableField(exist = false)
    private String name;

    /**
     * 登录名，只能是字母数字和下划线.
     */
    @TableField(exist = false)
    private String loginName;



    /**
     * 手机号.
     */
    @TableField(exist = false)
    private String phone;

    /**
     * 邮箱.
     */
    @TableField(exist = false)
    private String email;

    /**
    * 返回ID.
    * @return id
    */
    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
