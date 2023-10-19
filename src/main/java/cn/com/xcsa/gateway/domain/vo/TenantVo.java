package cn.com.xcsa.gateway.domain.vo;

import cn.com.xcsa.gateway.util.GroupAdd;
import cn.com.xcsa.gateway.util.GroupList;
import cn.com.xcsa.gateway.util.GroupUpdate;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class TenantVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键.
     */
    @NotNull(message = "参数必须输入", groups = GroupUpdate.class)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 是否删除 0 ：否 1：是.
     */
    private Integer deleted;

    /**
     * 创建时间.
     */
    private LocalDateTime createTime;

    /**
     * 租户名称.
     */
    @NotBlank(message = "租户名称必须填写", groups = GroupAdd.class)
    private String name;

    /**
     * 联系人姓名.
     */
    private String username;

    /**
     * 联系人手机号.
     */
    private String phone;

    /**
     * 联系人邮箱.
     */
    private String email;

    /**
     * 租户域名.
     */
    @NotBlank(message = "租户域名必须填写", groups = GroupAdd.class)
    private String domain;

    /**
     * 租户状态：1：正常 2：禁用.
     */
    private Integer status;

    /**
     * 授权状态 1：未激活 2：已激活 3：已过期.
     */
    private Integer authStatus;

    /**
     * 总计空间配额（最小单位精度：B 字节）.
     */
    @Value("${default.totalSpaceQuota:0}")
    private Long totalSpaceQuota;

    /**
     * 已用空间配额（最小单位精度：B 字节）.
     */
    private Long useSpaceQuota;

    private String speedDefaultConfig;

    @NotNull(message = "分页参数必须传递", groups = GroupList.class)
    private Integer pageNo;

    @NotNull(message = "分页参数必须传递", groups = GroupList.class)
    private Integer pageSize;

}
