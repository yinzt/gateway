package cn.com.xcsa.gateway.domain.vo;

import cn.com.xcsa.gateway.util.GroupAdd;
import cn.com.xcsa.gateway.util.GroupUpdate;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
* <p> 用户组表</p>.
*
* @author huyu
* @since 2023-09-24
*/
@Getter
@Setter
public class GroupVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键.
     */
    @NotNull(message = "组主键信息必输输入", groups = GroupUpdate.class)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
    * 关联sys_tenant表的id.
    */
    private Long tenantId;

    /**
     * 组名称.
     */
    @NotBlank(message = "组名称必须输入", groups = GroupAdd.class)
    private String name;

    /**
    * 创建时间.
    */
    private LocalDateTime createTime;

    /**
    * 是否删除 0：否 1：是.
    */
    private Integer deleted;



}
