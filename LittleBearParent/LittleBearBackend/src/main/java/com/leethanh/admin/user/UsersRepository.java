package com.leethanh.admin.user;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.leethanh.common.entity.Users;

@Repository
public interface UsersRepository extends PagingAndSortingRepository<Users, Integer>,CrudRepository<Users, Integer>{

	@Query("SELECT u FROM Users u where u.email= :email")
	public Users getUsersByEmail(@Param("email") String email);
	
	public Integer countById(Integer Id);
	
	
	@Query("UPDATE Users u SET u.enabled = ?2 WHERE u.id = ?1")
	@Modifying
	public void updateEnabledStatus (Integer id, boolean enabled);
}