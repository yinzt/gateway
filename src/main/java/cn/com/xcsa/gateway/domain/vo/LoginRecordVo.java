package cn.com.xcsa.gateway.domain.vo;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
* <p> </p>.
*
* @author huyu
* @since 2023-09-13
*/
@Getter
@Setter
public class LoginRecordVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键.
    */
    @NotNull(message = "数据id为必传信息")
    private Long id;

    /**
    * 用户id.
    */
    private Long userId;

    /**
    * 登陆名.
    */
    private String loginName;

    /**
    * 用户名类型，ldap，dingding，weixin，feishu，email，phone.
    */
    private String loginType;

    /**
    * 租户id.
    */
    private Long tenantId;

    /**
    * 登录的token.
    */
    private String token;

    /**
    * 0失败，1在线，2下线.
    */
    private Integer status;

    /**
    * 客户端版本.
    */
    private String clientVersion;

    /**
    * 登录IP地址.
    */
    private String ipAddr;

    /**
    * 系统名称.
    */
    private String systemName;

    /**
    * 系统版本.
    */
    private String systemVersion;

    /**
    * 0管理后台，1安全云web，2安全云客户端，3文档云客户端.
    */
    private String terminalType;

    /**
     * 登录时间.
     */
    private LocalDateTime createTime;

    /**
    * 是否删除：0：否 1：是.
    */
    private Integer deleted;




}
