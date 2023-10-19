package cn.com.xcsa.gateway.domain.vo;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
@Getter
@Setter
public class AccessStrategyVo implements Serializable {


    private static final long serialVersionUID = 1L;
    @NotNull(message = "id不能为空")
    private Long id;
    /**
     * 是否用户名登录 1:是 2:否.
     * */
    private Integer loginName;
    /**
     * 是否邮箱登录1:是 2:否.
     * */
    private Integer email;
    /**
     * 手机号登录 1:是 2:否.
     */
    private Integer phone;
    /**
     * 手机号登录密码验证方式 1:密码验证 2:密码加验证码验证.
     */
    private Integer phoneVerify;
    /**
     * 默认密码.
     */
    @NotNull(message = "默认密码不能为空")
    private String defaultPwd;
    /**
     * 密码最少位数.
     */
    @NotNull(message = "密码最少位数不能为空")
    private Integer pwdLeast;
    /**
     * 密码强度 1:不限 2:低 3:中 4:高.
     */
    @NotNull(message = "密码强度不能为空")
    private Integer intensityPwd;
    /**
     * 密码有效期 单位天.
     */
    @NotNull(message = "密码有效期不能为空")
    private Integer dayPwd;
    /**
     * 是否开启短信验证 1:开启 2:关闭.
     */
    private Integer sms;
    /**
     * 1:安全环境检测 2:用户每次登录需要进行验证.
     */
    private Integer smsVerify;
    /**
     * 是否开启令牌验证 1:开启 2:关闭.
     */
    private Integer token;
    /**
     * 1:安全环境检测 2:用户每次登录需要进行验证.
     */
    private Integer tokenVerify;
}
