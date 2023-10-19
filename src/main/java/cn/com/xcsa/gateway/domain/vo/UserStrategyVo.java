package cn.com.xcsa.gateway.domain.vo;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
public class UserStrategyVo implements Serializable {
    /**
     * 主键.
     */
    private Long id;
    /**
     * 租户id.
     * */
    @NotNull(message = "租户id不能为空")
    private Long tenantId;
    /**
     * 是否开启安全防护策略 1:开启 2:关闭.
     */
    private Integer protection;

}
