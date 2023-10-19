package cn.com.xcsa.gateway.domain.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 请求实体.
 * @author wuhui
 */
@Data
public class LoginVo implements Serializable {

    private String loginName;

    @NotNull(message = "参数不能为空")
    private Long userId;
}
