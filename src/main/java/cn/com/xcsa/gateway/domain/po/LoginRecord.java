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
* <p> </p>.
*
* @author huyu
* @since 2023-09-20
*/
@Getter
@Setter
@TableName("sys_login_record")
public class LoginRecord extends Model<LoginRecord> {

    private static final long serialVersionUID = 1L;

    /**
    * 主键.
    */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
    * 用户id.
    */
    private Long userId;

    /**
    * 登陆名.
    */
    private String loginName;

    /**
    * 用户名类型，ldap，dingding，weixin，feishu，email，phone.
    */
    private String loginType;

    /**
    * 租户id.
    */
    private Long tenantId;

    /**
    * 登录的token.
    */
    private String token;

    /**
    * 0失败，1在线，2下线.
    */
    private Integer status;

    /**
    * 客户端版本.
    */
    private String clientVersion;

    /**
    * 登录IP地址.
    */
    private String ipAddr;

    /**
    * 系统名称.
    */
    private String systemName;

    /**
    * 系统版本.
    */
    private String systemVersion;

    /**
    * 设备唯一标识.
    */
    private String deviceId;

    /**
    * 0管理后台，1安全云web，2安全云客户端，3文档云客户端.
    */
    private String terminalType;

    /**
    * 登录时间.
    */
    private LocalDateTime createTime;

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
