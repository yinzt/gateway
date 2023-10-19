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
@TableName("sys_secure_strategy")
public class SecureStrategy extends Model<SecureStrategy> {
    /**
     * 主键.
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 防护名称.
     */
    private String protectionName;
    /**
     * 防护策略.
     */
    private String protectionStrategy;
    /**
     * 防护成员.
     */
    private String protectionUser;
    /**
     * 创建时间.
     */
    private Date createTime;
    /**
     * 关联sys_user_strategy.
     */
    private Long uid;
}
