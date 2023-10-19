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
import lombok.experimental.Accessors;

/**
* <p> 租户空间信息表</p>.
*
* @author huyu
* @since 2023-09-21
*/
@Getter
@Setter
@TableName("sys_tenant_space")
@Accessors(chain = true)
public class TenantSpace extends Model<TenantSpace> {

    private static final long serialVersionUID = 1L;

    /**
    * 主键.
    */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
    * 租户id.
    */
    private Long tenantId;

    /**
    * 创建时间.
    */
    private LocalDateTime createTime;

    /**
    * 总计空间配额（最小单位精度：B 字节）.
    */
    private Long totalSpaceQuota;

    /**
    * 已用空间配额（最小单位精度：B 字节）.
    */
    private Long useSpaceQuota;

    /**
    * 是否删除 ：0：否 1：是.
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
