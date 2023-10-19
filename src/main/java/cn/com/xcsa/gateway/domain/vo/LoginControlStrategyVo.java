package cn.com.xcsa.gateway.domain.vo;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
@Getter
@Setter
public class LoginControlStrategyVo implements Serializable {
    /**
     * 主键id.
     */
    @NotNull(message = "id不能为空")
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
     * ios登录控制开启时禁止用户登录 1:开启 :禁止.
     */
    private Integer iosLogin;
    /**
     * 移动端登录控制开启时禁止用户登录 1:开启 0:禁止.
     */
    private Integer androidLogin;

}
