package cn.com.xcsa.gateway.ldaptest;

import java.util.List;
import java.util.Optional;

import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.support.LdapNameBuilder;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

public class PersonRepoImpl implements PersonRepo {

	@Autowired
	private LdapTemplate ldapTemplate;


	private static class PersonContextMapper implements ContextMapper {
		public Object mapFromContext(Object ctx) {
			DirContextAdapter context = (DirContextAdapter) ctx;
			Person p = new Person();
			p.setFullname(context.getStringAttribute("cn"));
			p.setSn(context.getStringAttribute("sn"));
			p.setDescription(context.getStringAttribute("description"));
			return p;
		}
	}

	/**
	 * 获取对象.
	 * @param name
	 * @param company
	 * @return duixiang.
	 */
	public Person findByPrimaryKey(String name, String company) {
		Person person = new Person();
		person.setCompany(company);
		person.setFullname(name);
		Name dn = buildDn(person);
		return (Person) ldapTemplate.lookup(dn, new PersonContextMapper());
	}

	/**
	 * 修改描述.
	 * @param p
	 */
	public void updateDescription(Person p) {
		Name dn = buildDn(p);
		Attribute attr = new BasicAttribute("description", p.getDescription());
		ModificationItem item = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr);
		ldapTemplate.modifyAttributes(dn, new ModificationItem[] {item});
	}


	/**
	 * 根据lastname获取list.
	 * @param lastName
	 * @return 结果.
	 */
	public List<String> getPersonNamesByLastName(String lastName) {
		LdapQuery query = query()
				.base("DC=xinchuang,DC=com")
// 这里一定记得初始化的时候放入了baseDN就不要重复填，不然会在baseDN目录里搜这个base目录
				.attributes("cn", "sn")
				.where("objectclass").is("person")
				.and("sn").is(lastName);
		return ldapTemplate.search(query,
				new AttributesMapper<String>() {
					public String mapFromAttributes(Attributes attrs)
							throws NamingException {

						return (String) attrs.get("cn").get();
					}
				});
	}

	/**
	 * 增加方法.
	 * @param person
	 */
	public void create(Person person) {
		Name dn = buildDn(person);
		ldapTemplate.bind(dn, null, buildAttributes(person));
	}

	/**
	 * 修改.
	 * @param p
	 */
	public void update(Person p) {
		Name dn = buildDn(p);
		ldapTemplate.rebind(dn, null, buildAttributes(p));
	}

	/**
	 * 组装basedn.
	 * @param p
	 * @return basedn.
	 */
	protected Name buildDn(Person p) {
		final String baseDn = "dc=xinchuang,dc=com";
		return LdapNameBuilder.newInstance(baseDn)
				.add("ou", p.getCompany())
				.add("cn", p.getFullname())
				.build();
	}

	/**
	 * 组装Attributes.
	 * @param p
	 * @return Attributes.
	 */
	private Attributes buildAttributes(Person p) {
		Attributes attrs = new BasicAttributes();
		BasicAttribute ocattr = new BasicAttribute("objectclass");
		ocattr.add("top");
		ocattr.add("person");
		ocattr.add("organizationalPerson");
		ocattr.add("user");
		attrs.put(ocattr);
		attrs.put("cn", p.getFullname());
		attrs.put("sn", "Person");
		return attrs;
	}


	/**
	 * PersonRepo接口要实现的方法.
	 * @param entity
	 * @return 结果.
	 * @param <S>
	 */
	@Override
	public <S extends Person> S save(S entity) {
		return null;
	}

	/**
	 * PersonRepo接口要实现的方法.
	 * @param entities
	 * @return 结果.
	 * @param <S>
	 */
	@Override
	public <S extends Person> List<S> saveAll(Iterable<S> entities) {
		return null;
	}
	/**
	 * PersonRepo接口要实现的方法.
	 * @param name
	 * @return 结果.
	 */
	@Override
	public Optional<Person> findById(Name name) {
		return Optional.empty();
	}
	/**
	 * PersonRepo接口要实现的方法.
	 * @param name
	 * @return 结果.
	 */
	@Override
	public boolean existsById(Name name) {
		return false;
	}
	/**
	 * PersonRepo接口要实现的方法.
	 * @return 结果.
	 */
	@Override
	public List<Person> findAll() {
		return null;
	}
	/**
	 * PersonRepo接口要实现的方法.
	 * @param names
	 * @return 结果.
	 */
	@Override
	public List<Person> findAllById(Iterable<Name> names) {
		return null;
	}
	/**
	 * PersonRepo接口要实现的方法.
	 * @return 结果.
	 */
	@Override
	public long count() {
		return 0;
	}
	/**
	 * PersonRepo接口要实现的方法.
	 * @param name
	 */
	@Override
	public void deleteById(Name name) {

	}
	/**
	 * PersonRepo接口要实现的方法.
	 * @param entity
	 */
	@Override
	public void delete(Person entity) {
		Name dn = buildDn(entity);
		ldapTemplate.unbind(dn);
	}
	/**
	 * PersonRepo接口要实现的方法.
	 * @param names
	 */
	@Override
	public void deleteAllById(Iterable<? extends Name> names) {

	}
	/**
	 * PersonRepo接口要实现的方法.
	 * @param entities
	 */
	@Override
	public void deleteAll(Iterable<? extends Person> entities) {

	}
	/**
	 * PersonRepo接口要实现的方法.
	 */
	@Override
	public void deleteAll() {

	}
	/**
	 * PersonRepo接口要实现的方法.
	 * @param ldapQuery
	 * @return 结果.
	 */
	@Override
	public Optional<Person> findOne(LdapQuery ldapQuery) {
		return Optional.empty();
	}
	/**
	 * PersonRepo接口要实现的方法.
	 * @param ldapQuery
	 * @return 结果.
	 */
	@Override
	public Iterable<Person> findAll(LdapQuery ldapQuery) {
		return null;
	}

}
