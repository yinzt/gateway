package cn.com.xcsa.gateway.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@TableName("sys_user_strategy")
public class UserStrategy extends Model<UserStrategy> {
    /**
     * 主键id.
     * */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 租户id.
     * */
    private Long tenantId;
    /**
     * 是否开启安全防护策略 1:开启 0:关闭.
     */
    private Integer protection;
    /**
     * 创建时间.
     */
    private Date createTime;
}
