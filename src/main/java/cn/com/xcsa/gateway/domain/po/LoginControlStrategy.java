package cn.com.xcsa.gateway.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@TableName("sys_login_control_strategy")
public class LoginControlStrategy extends Model<LoginControlStrategy> {
    /**
     * 主键id.
     * */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 租户id.
     * */
    private Long tenantId;
    /**
     * win安全云登录控制开启时禁止用户登录 1:开启 0:禁止.
     */
    private Integer securityCloud;
    /**
     * win
     * 文档云登录控制开启时禁止用户登录 1:开启 0:禁止.
     */
    private Integer winCloudDoc;
    /**
     * mac
     * 文档云登录控制开启时禁止用户登录 1:开启 0:禁止.
     */
    private Integer macCloudDoc;
    /**
     * 国产化文档云登录控制开启时禁止用户登录 1:开启 0:禁止.
     */
    private Integer localization;
    /**
     * web登录控制开启时禁止用户登录 1:开启 0:禁止.
     */
    private Integer webLogin;
    /**
     * ios登录控制开启时禁止用户登录 1:开启 0:禁止.
     */
    private Integer iosLogin;
    /**
     * 移动端登录控制开启时禁止用户登录 1:开启 0:禁止.
     */
    private Integer androidLogin;
    /**
     * 创建时间.
     */
    private Date createTime;

}
