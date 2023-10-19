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
* <p> 用户组表</p>.
*
* @author huyu
* @since 2023-09-24
*/
@Getter
@Setter
@TableName("sys_group")
public class Group extends Model<Group> {

    private static final long serialVersionUID = 1L;

    /**
    * 主键.
    */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
    * 关联sys_tenant表的id.
    */
    private Long tenantId;

    /**
    * 组名称.
    */
    private String name;

    /**
    * 创建时间.
    */
    private LocalDateTime createTime;

    /**
    * 是否删除 0：否 1：是.
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
