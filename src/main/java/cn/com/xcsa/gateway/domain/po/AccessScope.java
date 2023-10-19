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
@TableName("sys_access_scope")
public class AccessScope extends Model<AccessScope> {
    /**
     * 主键id.
     * */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 访问区域名称.
     */
    private String  visitName;
    /**
     * 访问范围.
     */
    private String visitRange;
    /**
     * 访问成员.
     */
    private String visitMember;
    /**
     * 访问空间范围.
     */
    private String visitSpace;
    /**
     * 创建时间.
     */
    private Date createTime;
    /**
     * 关联sys_security_strategy表添加.
     */
    private Long sid;

}
