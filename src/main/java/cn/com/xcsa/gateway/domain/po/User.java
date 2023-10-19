package cn.com.xcsa.gateway.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p> 用户信息表</p>.
 *
 * @author huyu
 * @since 2023-09-11
 */
@Getter
@Setter
@TableName("sys_user")
@Accessors(chain = true)
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键.
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户昵称.
     */
    private String name;

    /**
     * 登录名，只能是字母数字和下划线.
     */
    private String loginName;

    /**
     * 开启本地密码模式才需要.
     */
    private String password;

    /**
     * 租户id.
     */
    private Long tenantId;

    /**
     * 手机号.
     */
    private String phone;

    /**
     * 邮箱.
     */
    private String email;



    /**
     * 来源：LDAP、dingding（钉钉）、weixin（微信）、feishu（飞书）、local（手动）.
     */
    private String source;

    /**
     * 创建时间.
     */
    private LocalDateTime createTime;

    /**
     * 修改时间.
     */
    private LocalDateTime updateTime;

    /**
     * 权重排序.
     */
    private Integer sortNo;

    /**
     * googleauth的secret值.
     */
    private String authSecret;


    /**
     * 是否删除：0：否 1：是.
     */
    private Integer deleted;


    /**
     * 返回ID.
     * @return id
     */
    @Override
    public Serializable pkVal() {
        return this.id;
    }

    /**
     * 激活状态：0：未激活 1：已激活.
     */
    private Integer activateStatus;

    /**
     * 锁定状态：0：未锁定 1：已锁定.
     */
    private Integer lockStatus;

    /**
     * 用户状态：0:正常 1：已禁用 2：离职交接中.
     */
    private Integer userStatus;

    /**
     * 登录凭证.
     */
    @TableField(exist = false)
    private String token;

    /**
     * 总计空间配额（最小单位精度：B 字节）.
     */
    @TableField(exist = false)
    private Long totalSpaceQuota;

    @TableField(exist = false)
    private ActivateStatus activateStatusEnum;

    @TableField(exist = false)
    private LockStatus lockStatusEnum;

    @TableField(exist = false)
    private UserStatus userStatusEnum;

    public enum ActivateStatus {
        UNACTIVATED(0),
        ACTIVATED(1);

        private final Integer value;

        /**
         * .
         * @param varvalue
         */
        ActivateStatus(Integer varvalue) {
            this.value = varvalue;
        }

        /**
         * .
         * @return .
         */
        public Integer getValue() {
            return value;
        }
    }


    public enum LockStatus {
        UNLOCKED(0),
        LOCKED(1);

        private final Integer value;

        /**
         * .
         * @param varvalue
         */
        LockStatus(Integer varvalue) {
            this.value = varvalue;
        }

        /**
         * 获取值.
         * @return 值.
         */
        public Integer getValue() {
            return value;
        }
    }

    public enum UserStatus {
        DEFAULT(0),
        DISABLED(1),
        RESIGNING(2);

        private final Integer value;

        /**
         * .
         * @param varValue
         */
        UserStatus(Integer varValue) {
            this.value = varValue;
        }
        /**
         * 获取值.
         * @return 值.
         */
        public Integer getValue() {
            return value;
        }
    }

    public enum Source {

        LDAP("LDAP"),
        DINGDING("dingding"),
        WEIXIN("weixin"),
        FEISHU("feishu"),
        LOCAL("local");

        private final String value;

        /**
         * 获取值.
         * @param varValue
         */
        Source(String varValue) {
            this.value = varValue;
        }

        /**
         *
         * @return 返回值.
         */
        public String getValue() {
            return value;
        }

        /**
         * 获取来源.
         * @param value
         * @return 来源.
         */
        public static Source fromValue(String value) {
            for (Source source : Source.values()) {
                if (source.getValue().equalsIgnoreCase(value)) {
                    return source;
                }
            }
            throw new IllegalArgumentException("Invalid source value: " + value);
        }
    }



}
