package cn.com.xcsa.gateway.ldaptest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import javax.naming.Name;

@Entry(objectClasses = {"person"})
@Data
public class LdapUser {
	@Id
	@JsonIgnore // 必写
	private Name distinguishedName;
	/* 登录账号 */
	@Attribute(name = "sAMAccountName")
	private String loginName;
	/* 用户姓名 */
	@Attribute(name = "cn")
	private String userName;
	/* 邮箱 */
	@Attribute(name = "mail")
	private String email;

}
