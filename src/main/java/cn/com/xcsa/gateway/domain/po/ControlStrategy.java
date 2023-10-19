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
@TableName("sys_control_strategy")
public class ControlStrategy extends Model<ControlStrategy> {
    /**
     * 主键id.
     * */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 控制策略名称.
     */
    private String controlName;
    /**
     * 控制策略.
     */

    private String controlStrategy;
    /**
     * 控制成员.
     */
    private String controlMember;
    /**
     * 创建时间.
     */
    private Date createTime;
    /**
     * 关联sys_login_control_strategy表.
     */
    private Long cid;
}
