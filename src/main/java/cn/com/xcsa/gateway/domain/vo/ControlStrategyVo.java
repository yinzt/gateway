package cn.com.xcsa.gateway.domain.vo;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ControlStrategyVo {
    /**
     * 主键.
     */
    private Long id;
    /**
     * 控制策略名称.
     */
    @NotNull(message = "控制策略名称不能为空")
    private String controlName;
    /**
     * 控制策略.
     */
    @NotNull(message = "控制策略不能为空")
    private String controlStrategy;
    /**
     * 控制成员.
     */
    @NotNull(message = "控制成员不能为空")
    private String controlMember;
    /**
     * 关联sys_login_control_strategy表.
     */
    @NotNull(message = "cid不能为空")
    private Long cid;
}
