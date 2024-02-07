package com.leethanh.admin.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.leethanh.common.entity.Roles;
import com.leethanh.common.entity.Users;

public class LittleBearUserDetails implements UserDetails {
	
	private Users user;

	
	public LittleBearUserDetails(Users user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<Roles>roles= user.getRoles();
		List<SimpleGrantedAuthority> authorities= new ArrayList<>();
		for (Roles role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
		
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getEmail();
	}
	
	
	public String getFullname() {
		// TODO Auto-generated method stub
		return user.getFullname();
	}
	
	public void setFirstName(String firstName) {
		// TODO Auto-generated method stub
		this.user.setFirstName(firstName);
	}
	public void setLastName(String lastName) {
		// TODO Auto-generated method stub
		this.user.setLastName(lastName);
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return user.getEnabled();
	}

}
