package cn.com.xcsa.gateway.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
* <p> </p>.
*
* @author huyu
* @since 2023-09-20
*/
@Getter
@Setter
@TableName("sys_email_settings")
public class EmailSettings extends Model<EmailSettings> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long tenantId;

    private String host;

    private String port;

    private String username;

    private String password;

    private LocalDateTime createTime;


    /**
    * 返回ID.
    * @return id
    */
    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
