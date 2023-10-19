package cn.com.xcsa.gateway.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
* <p> 离职交接业务表</p>.
*
* @author huyu
* @since 2023-09-26
*/
@Getter
@Setter
@TableName("sys_user_handover")
public class UserHandover extends Model<UserHandover> {

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
    private Long leaveUserId;

    /**
    * 接收人user_id关联sys_user表.
    */
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
    @TableLogic
    private Integer deleted;


    /**
    * 返回ID.
    * @return id
    */
    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
