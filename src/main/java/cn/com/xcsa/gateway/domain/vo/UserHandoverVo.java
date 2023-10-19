package cn.com.xcsa.gateway.domain.vo;

import cn.com.xcsa.gateway.util.GroupAdd;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
* <p> 离职交接业务表</p>.
*
* @author huyu
* @since 2023-09-26
*/
@Getter
@Setter
public class UserHandoverVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键.
    */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
    * 关联租户表的id.
    */
    private Long tenantId;

    /**
     * 离职人user_id关联sys_user表.
     */
    @NotNull(message = "离职人userId必须输入", groups = GroupAdd.class)
    private Long leaveUserId;

    /**
     * 接收人user_id关联sys_user表.
     */
    @NotNull(message = "接收人userId必须输入", groups = GroupAdd.class)
    private Long receiveUserId;

    /**
    * 创建时间.
    */
    private LocalDateTime createTime;

    /**
    * 修改时间.
    */
    private LocalDateTime updateTime;

    /**
    * 是否删除0否1是.
    */
    private Integer deleted;




}
