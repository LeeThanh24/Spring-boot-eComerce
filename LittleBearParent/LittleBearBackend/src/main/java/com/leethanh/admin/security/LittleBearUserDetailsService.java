package com.leethanh.admin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.leethanh.admin.user.UsersRepository;
import com.leethanh.common.entity.Users;

public class LittleBearUserDetailsService implements UserDetailsService {

	@Autowired
	UsersRepository usersRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Users user = usersRepository.getUsersByEmail(email);
		
		if (user !=null)
		{
			
			return new LittleBearUserDetails(user);
		}
		throw new UsernameNotFoundException("Cound not find user with email "+email);
	}

}
