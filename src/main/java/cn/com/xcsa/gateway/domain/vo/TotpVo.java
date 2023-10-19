package cn.com.xcsa.gateway.domain.vo;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
public class TotpVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "用户名信息必传项")
    private String id;

    @NotBlank(message = "安全访问token必传项")
    private String accessToken;

    @NotBlank(message = "请输入令牌的验证码")
    private String code;




}
