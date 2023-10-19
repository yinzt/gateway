package cn.com.xcsa.gateway.ldaptest;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
public class LdapConfig {
	@Value("${server.ldap.urls}")
	private String url;
	//	@Value("${spring.ldap.base}")
//	String base;
	@Value("${server.ldap.username}")
	private String username;
	@Value("${server.ldap.password}")
	private String password;

	/**
	 * 加载ContextSource.
	 * @return source源.
	 */
	@Bean
	public LdapContextSource ldapContextSource() {
		LdapContextSource source = new LdapContextSource();
		source.setUrl(url);
		source.setUserDn(username);
		source.setPassword(password);
		//source.setBase(base);
		return source;
	}

	/**
	 * 获取ldapTemplate对象.
	 * @return ldapTemplate对象.
	 */
	@Bean
	public LdapTemplate ldapTemplate() {
		return new LdapTemplate(ldapContextSource());
	}


}
