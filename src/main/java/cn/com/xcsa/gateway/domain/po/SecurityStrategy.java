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
@TableName("sys_security_strategy")
public class SecurityStrategy extends Model<SecurityStrategy> {
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
     * 用户登录密码时错误锁定次数.
     */
    private Integer loginFail;
    /**
     * 用户锁定后手动解锁 1:开启手动 0:关闭手动.
     */
    private Integer unlockMethod;
    /**
     * 自动解锁时间 单位分.
     */
    private Integer unlockTime;
    /**
     * 用户长时间没有操作时自动提出登录 单位分.
     */
    private Integer userTimeout;
    /**
     * 管理员长时间没有操作时自动提出登录 单位分.
     */
    private Integer adminTimeout;
    /**
     * 是否跳转登录 1:开启 0:关闭.
     */
    private Integer jump;
    /**
     * 是否开启ip登录范围 1:开启 0:不开启.
     */
    private Integer ipScope;
    /**
     * 是否开启管理员不受限制ip登录 1:开启 0:不开启.
     */
    private Integer adminLimit;
    /**
     * 创建时间.
     */
    private Date createTime;

}
