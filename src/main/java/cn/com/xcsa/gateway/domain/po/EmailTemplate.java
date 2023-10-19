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
@TableName("sys_email_template")
public class EmailTemplate extends Model<EmailTemplate> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
    * 模板类型.
    */
    private String emailType;

    private Long tenantId;

    /**
    * 0：默认，1:根租户绑定.
    */
    private String scope;

    private String subject;

    private String content;

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
