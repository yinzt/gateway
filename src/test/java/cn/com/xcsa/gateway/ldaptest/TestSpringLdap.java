package cn.com.xcsa.gateway.ldaptest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static org.springframework.ldap.query.LdapQueryBuilder.query;


@SpringBootTest
@RunWith(SpringRunner.class)
public class TestSpringLdap {

	@Autowired
	private LdapTemplate ldapTemplate;

	@Autowired
	private PersonRepoImpl personRepo;

	private class PersonAttributesMapper implements AttributesMapper<Person> {
		public Person mapFromAttributes(Attributes attrs) throws NamingException {
			Person person = new Person();
			person.setFullname((String) attrs.get("cn").get());
			person.setSn(attrs.get("sn") == null
					? null : (String) attrs.get("sn").get());
			person.setDescription(attrs.get("description") == null
					? null : (String) attrs.get("description").get());
			return person;
		}
	}

	/**
	 * 测试.
	 */
	@Test
	public void testSearch() {
		ldapTemplate.setDefaultCountLimit(0);
		List<Person> search = ldapTemplate.search(query().
						base("DC=xinchuang,DC=com")
						.where("objectclass")
				.is("person"), new PersonAttributesMapper() {
				});
		search.forEach(s -> {
			System.out.println(s);
		});
	}


	/**
	 * 测试.
	 */
	@Test
	public void testfindByPrimaryKey() {
		String name = "User1";
		String company = "研发";
		Object byPrimaryKey = personRepo.findByPrimaryKey(name, company);
		System.out.println(byPrimaryKey);
	}

	/**
	 * 测试 ModifyAttributes 方式update.
	 * 是根据basedn下的ou的cn来变更的 也就是 distinguishedName=CN=张撞,OU=研发,DC=xinchuang,DC=com
	 */
	@Test
	public void testModifyAttributes() {
		Person person = new Person();
		person.setCompany("研发");
		person.setFullname("张撞");
		person.setDescription("描述描述111111");
		personRepo.updateDescription(person);
	}

	/**
	 * 测试rebind修改.
	 * rebind 是粗糙修改 先解绑定 然后再绑定 会创建新的
	 */
	@Test
	public void testRebindUpdate() {
		Person person = new Person();
		person.setCompany("研发");
		person.setFullname("张撞");
		person.setDescription("描述描述");
		personRepo.update(person);
	}


	/**
	 * 测试删除.
	 */
	@Test
	public void testDelete() {
		Person person = new Person();
		person.setCompany("研发");
		person.setFullname("张撞墙");
		personRepo.delete(person);
	}



	/**
	 * 根据指定lastName获取.
	 */
	@Test
	public void getlastName() {
		ldapTemplate.setIgnorePartialResultException(true);
		String lastName = "t";
		List<String> name = personRepo.getPersonNamesByLastName(lastName);
		System.out.println(name);
	}

	/**
	 * 获取全部CN.
	 * @return
	 */
	@Test
	public void getAllPersonNames() {
		ldapTemplate.setIgnorePartialResultException(true);
		List<String> search = ldapTemplate.search(query()
						.base("DC=xinchuang,DC=com")
						.where("objectclass").is("person"),
				(AttributesMapper<String>) attrs -> (String) attrs.get("cn").get());
		System.out.println(search);
		System.out.println(search.size());
	}

	/**
	 * ceshi.
	 */
	@Test
	public void getPageDataPerson() {
		ldapTemplate.setIgnorePartialResultException(true);
		List<String> search = ldapTemplate.search(
				query().base("DC=xinchuang,DC=com")
						.where("objectclass").is("person")
						.and("distinguishedName")
						.is("CN=t8,OU=研发,DC=xinchuang,DC=com"),
				(AttributesMapper<String>) attrs -> (String) attrs.get("cn").get());
		System.out.println(search);
	}


	/**
	 * 获取basedn目录下所有子元素.
	 */
	@Test
	public void ldapTest() {
		String baseDn = "ou=研发,DC=xinchuang,DC=com"; // 基础DN
		List<String> list = ldapTemplate.list(baseDn);
		System.out.println(list);
	}

	/**
	 * 获取指定ou目录下的子机构（不包含孙机构及以下）.
	 */
	@Test
	public void ldapTestDep() {
		ldapTemplate.setDefaultCountLimit(0);
		ldapTemplate.setIgnorePartialResultException(true);
		String searchBase = "OU=研发,DC=xinchuang,DC=com";
		List<String> organizations = new ArrayList<>();
		String searchFilter = "(objectClass=organizationalUnit)";
		SearchControls searchControls = new SearchControls();
		searchControls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
		try {

			NamingEnumeration<SearchResult> results =
					ldapTemplate.getContextSource()
							.getReadWriteContext()
							.search(searchBase,
									searchFilter,
									searchControls);

			while (results.hasMore()) {
				SearchResult result = results.next();
				String organizationDN = result.getNameInNamespace();

				// 提取机构名
				String[] split = organizationDN.split(",");
				String organizationName = split[0];
//				String organizationName =
//				organizationDN.substring(organizationDN.indexOf("ou=") + 4);
				organizations.add(organizationName);
			}
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}

		organizations.forEach(org -> {
			System.out.println(org);
		});
	}




	/**
	 * 递归查询机构及子孙机构.
	 */
	@Test
	public void ldapTestOrg() {
		List<HashMap> organizations = new ArrayList<>();

		String searchBase = "OU=研发,DC=xinchuang,DC=com";
		getSubOrganizations(searchBase, organizations);
		organizations.forEach(org -> {
			System.out.println(org);
		});

	}


	/**
	 * 递归方法.
	 * @param searchBase
	 * @param organizations
	 */
	private void getSubOrganizations(String searchBase, List<HashMap> organizations) {
		String searchFilter = "(objectClass=organizationalUnit)";
		SearchControls searchControls = new SearchControls();
		searchControls.setSearchScope(SearchControls.ONELEVEL_SCOPE);

		try {
			NamingEnumeration<SearchResult> results = ldapTemplate.getContextSource()
					.getReadWriteContext()
					.search(searchBase, searchFilter, searchControls);
			while (results.hasMore()) {

				SearchResult result = results.next();
				String organizationDN = result.getNameInNamespace();

				// 提取机构名
				String[] split = organizationDN.toUpperCase().split(",");
				String organizationName = split[0];
				//放入集合存储当前等级以及它所有父级
				HashMap<String, Object> map = new HashMap<>();
				map.put("organizationName", organizationName);
				map.put("organizationDN", organizationDN);
				organizations.add(map);
				//获取子组织
				List<String> list = new ArrayList<>();
				for (String org : split) {
					if (org.startsWith("OU=")) {
						list.add(org);
					}
				}
				String newSearchBaseString = String.join(",", list);
				// 递归调用自身，继续获取下一级组织单位
				getSubOrganizations(organizationDN, organizations);

			}
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
	}









}
