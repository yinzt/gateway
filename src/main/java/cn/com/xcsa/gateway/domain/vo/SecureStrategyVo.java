package cn.com.xcsa.gateway.domain.vo;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Getter
@Setter
public class SecureStrategyVo implements Serializable {
    /**
     * 主键.
     */
    private Long id;
    /**
     * 防护名称.
     */
    @NotNull(message = "防护名称不能为空")
    private String protectionName;
    /**
     * 防护策略.
     */
    @NotNull(message = "防护策略不能为空")
    private String protectionStrategy;
    /**
     * 防护成员.
     */
    @NotNull(message = "防护成员不能为空")
    private String protectionUser;
    /**
     * 关联sys_user_strategy.
     */
    @NotNull(message = "uid不能为空")
    private Long uid;
}
