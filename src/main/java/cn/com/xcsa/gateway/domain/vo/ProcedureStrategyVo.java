package cn.com.xcsa.gateway.domain.vo;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
public class ProcedureStrategyVo implements Serializable {
    /**
     * 主键id.
     */
    private Long id;
    /**
     * 租户id.
     * */
    @NotNull(message = "租户id不能为空")
    private Long tenantId;
    /**
     * 软件名称.
     */
    @NotNull(message = "软件名称不能为空")
    private String softwareName;
    /**
     *公司名称.
     */
    @NotNull(message = "公司名称不能为空")
    private String companyName;
    /**
     * 二进制程序名称.
     */
    @NotNull(message = "二进制程序名称不能为空")
    private String binaryName;
    /**
     * 状态 1:启用 0:禁用.
     */
    private Integer status;
}
