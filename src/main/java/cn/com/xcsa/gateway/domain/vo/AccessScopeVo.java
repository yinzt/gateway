package cn.com.xcsa.gateway.domain.vo;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
public class AccessScopeVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 访问区域名称.
     */
    @NotNull(message = "访问区域名称不能为空")
    private String  visitName;
    /**
     * 访问范围.
     */
    @NotNull(message = "访问范围不能为空")
    private String visitRange;
    /**
     * 访问成员.
     */
    @NotNull(message = "访问成员不能为空")
    private String visitMember;
    /**
     * 访问空间范围.
     */
    @NotNull(message = "访问空间范围不能为空")
    private String visitSpace;
    /**
     * 关联sys_security_strategy表添加.
     */
    @NotNull(message = "sid不能为空")
    private Long sid;
}
