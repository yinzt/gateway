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
@TableName("sys_procedure_strategy")
public class ProcedureStrategy extends Model<ProcedureStrategy> {
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
     * 软件名称.
     */
    private String softwareName;
    /**
     *公司名称.
     */
    private String companyName;
    /**
     * 二进制程序名称.
     */
    private String binaryName;
    /**
     * 状态 1:启用 0:禁用.
     */
    private Integer status;
    /**
     * 创建时间.
     */
    private Date createTime;
}
