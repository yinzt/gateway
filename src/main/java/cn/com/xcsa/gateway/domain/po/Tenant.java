package cn.com.xcsa.gateway.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

/**
* <p> 租户信息表</p>.
*
* @author huyu
* @since 2023-09-21
*/
@Getter
@Setter
@TableName("sys_tenant")
public class Tenant extends Model<Tenant> {

    private static final long serialVersionUID = 1L;

    /**
    * 主键.
    */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
    * 是否删除 0 ：否 1：是.
    */
    @TableLogic
    private Integer deleted;

    /**
    * 创建时间.
    */
    private LocalDateTime createTime;

    /**
    * 租户名称.
    */
    private String name;

    /**
    * 联系人姓名.
    */
    private String username;

    /**
    * 联系人手机号.
    */
    private String phone;

    /**
    * 联系人邮箱.
    */
    private String email;

    /**
    * 租户域名.
    */
    private String domain;

    /**
    * 租户状态：1：正常 2：禁用.
    */
    private Integer status;

    /**
    * 授权状态 1：未激活 2：已激活 3：已过期.
    */
    private Integer authStatus;

    /**
     * 总计空间配额（最小单位精度：B 字节）.
     */
    @TableField(exist = false)
    private Long totalSpaceQuota;

    /**
     * 已用空间配额（最小单位精度：B 字节）.
     */
    @TableField(exist = false)
    private Long useSpaceQuota;



    public enum Status {
        normal(1, "正常"),
        forbidden(2, "禁用");
        private final Integer varValue;
        private final String varName;
        Status(Integer value, String vaname) {
            this.varValue = value;
            this.varName = vaname;
        }

        /**
         * h获取值.
         * @return 值.
         */
        public Integer getVarValue() {
            return varValue;
        }

        /**
         * 获取名.
         * @return 名.
         */
        public String getVarName() {
            return varName;
        };
    }

    public enum AuthStatus {
        INACTIVE(1, "未激活"),
        ACTIVATED(2, "已激活"),
        EXPIRED(3, "已过期");
        private final Integer value;
        private final String name;

        AuthStatus(Integer varValue, String varName) {
            this.value = varValue;
            this.name = varName;
        }
        /**
         * h获取值.
         * @return 值.
         */
        public Integer getValue() {
            return value;
        }
        /**
         * 获取名.
         * @return 名.
         */
        public String getName() {
            return name;
        }
    }


    /**
    * 返回ID.
    * @return id
    */
    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
