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
* <p> 用户登录信息表</p>.
*
* @author huyu
* @since 2023-09-20
*/
@Getter
@Setter
@TableName("sys_user_login")
public class UserLogin extends Model<UserLogin> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
    * 用户ID.
    */
    private Long userId;

    /**
    * 租户id.
    */
    private Long tenantId;

    /**
    * 登录名.
    */
    private String loginName;

    /**
    * 用户名类型，ldap，dingding，weixin，feishu，email，phone.
    */
    private String loginType;

    /**
    * 认证token，本地登录是此字段为null.
    */
    private String loginToken;

    private LocalDateTime createTime;

    /**
    * token有效期本地登录是此字段为null.
    */
    private Integer expireTime;

    /**
    * 是否删除：0：否 1：是.
    */
    @TableLogic
    private Integer deleted;


    /**
    * 返回ID.
    * @return id
    */
    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
