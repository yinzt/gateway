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
* @author wuhui
* @since 2023-08-11
*/
@Getter
@Setter
@TableName("sys_license")
public class License extends Model<License> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long tenantId;

    private String content;

    private LocalDateTime createAt;


    /**
    * 返回ID.
    * @return id
    */
    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
