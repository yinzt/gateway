package cn.com.xcsa.gateway.domain.vo;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
public class SecurityStrategyVo implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键.
     */
    @NotNull(message = "id不能为空")
    private Long id;
    /**
     * 用户登录密码时错误锁定次数.
     */
    private Integer loginFail;
    /**
     * 用户锁定后手动解锁 1:开启手动 2:关闭手动.
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
     * 是否跳转登录 1:开启 2:关闭.
     */
    private Integer jump;
    /**
     * 是否开启ip登录范围 1:开启 2:不开启.
     */
    private Integer ipScope;
    /**
     * 是否开启管理员不受限制ip登录 1:开启 2:不开启.
     */
    private Integer adminLimit;
}
