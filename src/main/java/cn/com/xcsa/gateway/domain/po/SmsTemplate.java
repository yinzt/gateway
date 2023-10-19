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
@TableName("sys_sms_template")
public class SmsTemplate extends Model<SmsTemplate> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
    * 模板类型.
    */
    private String smsType;

    private Long tenantId;

    /**
    * 0：默认，1:根租户绑定.
    */
    private String scope;

    private String sign;

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
