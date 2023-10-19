package cn.com.xcsa.gateway.domain.vo;


import cn.com.xcsa.gateway.util.GroupAdd;
import cn.com.xcsa.gateway.util.GroupDelete;
import cn.com.xcsa.gateway.util.GroupList;
import cn.com.xcsa.gateway.util.GroupUpdate;
import cn.com.xcsa.gateway.util.GroupVariable;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;


@Getter
@Setter
public class UserVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键.
     */
    @NotNull(message = "主键信息必须填写", groups = GroupUpdate.class)
    @NotNull(message = "主键信息必须填写", groups = GroupDelete.class)
    private Long id;

    /**
     * 用户昵称.
     */
    @NotBlank(message = "昵称信息必须填写", groups = GroupAdd.class)
    private String name;

    /**
     * 登录名，只能是字母数字和下划线.
     */
    @NotBlank(message = "账号信息必须填写", groups = GroupVariable.class)
    @NotBlank(message = "账号信息必须填写", groups = GroupAdd.class)
    private String loginName;

    /**
     * 开启本地密码模式才需要.
     */
    @NotBlank(message = "密码信息必须填写", groups = GroupVariable.class)
    private String password;

    /**
     * 租户id.
     */
    private Long tenantId;

    /**
     * 手机号.
     */
    private String phone;

    /**
     * 邮箱.
     */
    @Email
    private String email;

    /**
     * 激活状态：0：未激活 1：已激活.
     */
    private Integer activateStatus;

    /**
     * 锁定状态：0：未锁定 1：已锁定.
     */
    private Integer lockStatus;

    /**
     * 用户状态：1：已禁用 2：离职交接中.
     */
    private Integer userStatus;

    /**
     * 来源：LDAP、dingding（钉钉）、weixin（微信）、feishu（飞书）、local（手动）.
     */
    private String source;

    /**
     * 创建时间.
     */
    private LocalDateTime createTime;

    /**
     * 修改时间.
     */
    private LocalDateTime updateTime;

    /**
     * 权重排序.
     */
    private Integer sortNo;

    /**
     * googleauth的secret值.
     */
    private String authSecret;

    /**
     * 是否删除：0：否 1：是 2:真实删除.
     */
    @NotNull(message = "删除状态必须填写", groups = GroupDelete.class)
    @NotNull(message = "删除状态必须填写", groups = GroupList.class)
    private Integer deleted;

    /**
     * 总计空间配额（最小单位精度：B 字节）.
     */
    private Long totalSpaceQuota;

    /**
     * 关联sys_org表org_id.
     */
    @NotNull(message = "机构关键信息必须填写", groups = GroupAdd.class)
    private Long orgId;

    /**
     * 拖拽验证码开启时 传递的参数.
     */
    @TableField(exist = false)
    private String token;

    @NotNull(message = "分页参数必须传递", groups = GroupList.class)
    private Integer pageNo;

    @NotNull(message = "分页参数必须传递", groups = GroupList.class)
    private Integer pageSize;

}
