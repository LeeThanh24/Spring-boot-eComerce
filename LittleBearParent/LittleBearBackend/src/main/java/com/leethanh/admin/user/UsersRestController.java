package com.leethanh.admin.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class UsersRestController {
	

	@Autowired
	UsersService usersService;
	
	@PostMapping("/users/check_email")
	public String checkDuplicateEmailString (@Param("id") Integer id,@Param("email")String email)
	{
		return usersService.isEmailUnique(id,email)?"OK":"Duplicated";
	}
	
	@PostMapping("/users/delete/{id}")
	public boolean deleteUser( @PathVariable("id") Integer id)
	{
		try {
			usersService.delete(id);
			return true;
		} catch (UserNotFoundException e) {
			// TODO: handle exception
			return false;
		}
		

	}
	
	@PostMapping("/users/{id}/status/{status}")
	public boolean updateStatus( @PathVariable("id") Integer id,@PathVariable("status") boolean status)
	{
		try {
			usersService.updateUserStatus(id, status);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		

	}
}
