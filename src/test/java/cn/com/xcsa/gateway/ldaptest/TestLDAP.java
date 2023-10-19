package cn.com.xcsa.gateway.ldaptest;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.NamingException;
import java.util.Hashtable;

/**
 * 传统方式对接ldap.
 */
public class TestLDAP {
	/**
	 * 主方法传统方式对接ldap.
	 * @param args
	 */
	public static void main(String[] args) {
		String ldapUrl = "ldap://172.16.0.140:389"; // AD服务器地址
		String baseDn = "ou=研发,dc=xinchuang,dc=com"; // 基础DN
		String username = "administrator@xinchuang.com"; // AD用户名
		String password = "P@ssw0rd1"; // AD密码

		Hashtable<String, String> env = new Hashtable<>();
		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, ldapUrl);
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, username);
		env.put(Context.SECURITY_CREDENTIALS, password);

		try {
			// 创建LDAP连接
			InitialLdapContext context = new InitialLdapContext(env, null);

			// 设置搜索条件
			SearchControls controls = new SearchControls();
			controls.setSearchScope(SearchControls.SUBTREE_SCOPE);

//			String searchBase = "ou=研发,dc=xinchuang,dc=com";
//			String filter = "(objectClass=organizationalUnit)";
			String filter = "(objectClass=user)";
			String[] returnAttributes = {"cn", "sn", "mail"};
			controls.setReturningAttributes(returnAttributes);
//
//			// 执行搜索
			NamingEnumeration<SearchResult> results =
					context.search(baseDn, filter, controls);

			while (results.hasMore()) {
				SearchResult searchResult = results.next();
				Attributes attributes = searchResult.getAttributes();

				// 获取用户属性
				String username1 = attributes.get("cn").get().toString();
				String lastName = attributes.get("sn").get().toString();
//				String email = attributes.get("mail").get().toString();

				// 处理其他用户属性...

				System.out.println("Username: " + username1);
				System.out.println("Last Name: " + lastName);
//				System.out.println("Email: " + email);
			}
			//搜索ou信息
			/*while (results.hasMore()) {
				SearchResult result = results.next();
				Attributes attrs = result.getAttributes();
				String ou = attrs.get("ou").get().toString();
				System.out.println(ou);
			}*/

//				// 读取组织架构数据属性
//				String ou = attrs.get("ou").get().toString();
//				String description = attrs.get("description").get().toString();
//				String parent = attrs.get("parent").get().toString();
//
//				// 同步到AD
//				String newDn = "ou=" + ou + "," + parent; // 新的DN
//				Attributes newAttrs = new BasicAttributes();
//				newAttrs.put("ou", ou);
//				newAttrs.put("description", description);
//
//				context.createSubcontext(newDn, newAttrs);
//			}

			// 关闭LDAP连接
			context.close();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

}
