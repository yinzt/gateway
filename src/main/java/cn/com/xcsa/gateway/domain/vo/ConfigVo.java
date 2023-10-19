package cn.com.xcsa.gateway.domain.vo;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
* <p> </p>.
*
* @author wuhui
* @since 2023-09-03
*/
@Getter
@Setter
public class ConfigVo implements Serializable {


    private static final long serialVersionUID = 1L;




    /**
    * 参数名称.
    */
    @NotBlank(message = "参数名称不能为空")
    private String paramName;

    /**
    * 参数键.
    */
    @NotNull(message = "参数键不能为空")
    private String paramKey;

    /**
    * 参数值.
    */
    @NotNull(message = "参数值不能为空")
    private String paramValue;


    /**
    * 备注.
    */
    private String remark;



}
