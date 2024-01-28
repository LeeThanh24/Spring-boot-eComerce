package com.leethanh.admin.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.booleanThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.leethanh.common.entity.Roles;
import com.leethanh.common.entity.Users;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateUserWithOneRole() {
		Roles roleAdmin = entityManager.find(Roles.class, 1);
		Users userThanhLe = new Users("lethanh@gmail.com","0204","Thanh","Le");
		userThanhLe.addRole(roleAdmin);
		Users savedUser = usersRepository.save(userThanhLe);
		assertThat(savedUser.getId()).isGreaterThan(0);
		
	}
	
	@Test
	public void testCreateUserWithTwoRoles() {
		Users userHoangChau = new Users("hc@gmail.com","0405","Chau","Pham");
		Roles roleEditor = entityManager.find(Roles.class, 4);
		Roles roleAssistant = entityManager.find(Roles.class,3);
		
		userHoangChau.addRole(roleEditor);
		userHoangChau.addRole(roleAssistant);
		
		Users savedUser = usersRepository.save(userHoangChau);
		assertThat(savedUser.getId()).isGreaterThan(0);
		
	}
	
	@Test
	public void testListAllUsers() {
		Iterable<Users> listUsersIterable = usersRepository.findAll();
		listUsersIterable.forEach(user->System.out.println(user));
		
		
	}
	
	@Test
	public void testGetUserById () {
		Users userThanh = usersRepository.findById(1).get();
		System.out.print(userThanh);
		
		assertThat(userThanh).isNotNull();
		
	
	}
	
	@Test
	public void testUpdateUserById () {
		Users userThanh = usersRepository.findById(1).get();
		userThanh.setEnabled(true);
		userThanh.setEmail("ledaoxuanthanh@gmail.com");
		System.out.print(userThanh);
		usersRepository.save(userThanh);
		assertThat(userThanh).isNotNull();
		
	}
	
	@Test
	public void testUpdateUserRoles () {
		Users userChau = usersRepository.findById(3).get();
		Roles roleEditor = entityManager.find(Roles.class, 4);
//		Roles roleSalePerson = entityManager.find(Roles.class,2);
		
		userChau.getRoles().remove(roleEditor);
//		userChau.addRole(roleSalePerson);
		
		
		usersRepository.save(userChau);
		
		
	}
	
	@Test
	public void testDeleteUser () {
		Integer id = 3 ; 
		usersRepository.deleteById(id);
		
		
	}
	
	@Test
	public void testGetUserByEmail ()
	{
		String email= "ledaoxuanthanh@gmail.com";
		Users user = usersRepository.getUsersByEmail(email);
		assertThat(user).isNotNull();
	}
	
	@Test
	public void testCountById ()
	{
		Integer id = 100;
		Integer user = usersRepository.countById(id);
		assertThat(user).isNotNull().isGreaterThan(0);
	}
	
	@Test
	public void testDisableUser ()
	{
		Integer id = 1;
		boolean enabled = false;
		usersRepository.updateEnabledStatus(id,enabled);
		
	}
}
