package com.petshop.dao_test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;

import com.petshop.dao.AuthorityDao;
import com.petshop.models.authority.Authority;
import com.petshop.models.authority.Role;

@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
public class AuthorityDaoTest {

	@Autowired
	private AuthorityDao authorityDao;

	@Test
	@Transactional
	@Rollback(true)
	public void testSaveAuthority() {
		Authority authority = new Authority();
		authority.setRoles(Role.USER);
		Authority getAuthority = authorityDao.saveAuthority(authority);
		assertEquals(authority, getAuthority);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testFindByRole() {
		Authority authority1 = new Authority();
		authority1.setRoles(Role.USER);
		authorityDao.saveAuthority(authority1);

		Authority authority2 = new Authority();
		authority2.setRoles(Role.ADMIN);
		authorityDao.saveAuthority(authority2);
		
		Authority findAuthority = authorityDao.findByRole(Role.ADMIN);
		assertEquals(authority2, findAuthority);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testGetAllRoles() {
		Authority authority1 = new Authority();
		authority1.setRoles(Role.USER);
		authorityDao.saveAuthority(authority1);

		Authority authority2 = new Authority();
		authority2.setRoles(Role.ADMIN);
		authorityDao.saveAuthority(authority2);
		
		List<Authority> allAuth = authorityDao.getAllRoles();
		
		assertEquals(2, allAuth.size());
	}

}
