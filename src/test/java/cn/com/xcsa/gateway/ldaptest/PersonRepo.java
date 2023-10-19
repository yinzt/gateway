package cn.com.xcsa.gateway.ldaptest;

import org.springframework.data.ldap.repository.LdapRepository;

public interface PersonRepo extends LdapRepository<Person> {
}
