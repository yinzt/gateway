package cn.com.xcsa.gateway.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
/**
 * 安全策略->访问策略访问规则配置表.
 *
 *
 */
@Getter
@Setter
@TableName("sys_access_strategy")
public class AccessStrategy extends Model<AccessStrategy> {
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
    * 是否用户名登录 1:是 2:否.
    * */
    private Integer loginName;
    /**
    * 是否邮箱登录1:是 2:否.
    * */
    private Integer email;
    /**
     * 手机号登录 1:是 2:否.
     */
    private Integer phone;
    /**
     * 手机号登录密码验证方式 1:密码验证 2:密码加验证码验证.
     */
    private Integer phoneVerify;
    /**
     * 默认密码.
     */
    private String defaultPwd;
    /**
     * 密码最少位数.
     */
    private Integer pwdLeast;
    /**
     * 密码强度 1:不限 2:低 3:中 4:高.
     */
    private Integer intensityPwd;
    /**
     * 密码有效期 单位天.
     */
    private Integer dayPwd;
    /**
     * 是否开启短信验证 1:开启 0:关闭.
     */
    private Integer sms;
    /**
     * 1:安全环境检测 2:用户每次登录需要进行验证.
     */
    private Integer smsVerify;
    /**
     * 是否开启令牌验证 1:开启 0:关闭.
     */
    private Integer token;
    /**
     * 1:安全环境检测 2:用户每次登录需要进行验证.
     */
    private Integer tokenVerify;
    /**
     * 创建时间.
     */
    private Date createTime;
}
