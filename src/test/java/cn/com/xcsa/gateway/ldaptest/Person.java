package cn.com.xcsa.gateway.ldaptest;


import lombok.Data;

@Data
public class Person {
	private String objectClass; // organizationalPerson
	private String cn; // 名称、唯一标识
	private String displayName; // 显示名
	private String sAMAccountName; // 登录名（唯一标识）
	private String sn; // 姓
	private String givenName; // 名
	private String objectGUID;

	private boolean isDeleted; // 删除的、禁用的
	private boolean isPrivilegeHolder; // 特权
	private boolean isRecycled; // 恢复的
	private String distinguishedName; // DN
	private String description;

	private String company; //组织=ou


	private String fullname; //全名=cn

}
