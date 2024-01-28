package com.leethanh.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.leethanh.common.entity.Roles;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Rollback(false)
public class RolesRepositoryTests {

	@Autowired
	RolesRepository roleRepository;
	
	@Test
	public void testCreateFirstRole () {
		Roles roleAdmin = new Roles("Admin","Manage everything");
		Roles savedRole = roleRepository.save(roleAdmin);
			
		assertThat(savedRole.getId()).isGreaterThan(0);
				
	}
	
	@Test
	public void testRestRoles () {
		Roles roleSalePerson = new Roles("SalesPerson","Manage product price, customers, shipping, orders, sales report");
		Roles roleEditor = new Roles("Editor","Manage categories, brands, products, articles and menus");
		Roles roleShipper = new Roles("Shipper","View products, orders, update order status");
		Roles roleAssistant = new Roles("Assistant","Manage questions and reviews");

		roleRepository.saveAll(List.of(roleSalePerson,roleAssistant,roleEditor,roleShipper));
			
		
				
	}
}
