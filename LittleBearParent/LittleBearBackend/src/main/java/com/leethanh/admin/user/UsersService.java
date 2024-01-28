package com.leethanh.admin.user;

import java.util.List;
import java.util.NoSuchElementException;

import org.hibernate.internal.log.SubSystemLogging;
import org.hibernate.query.NativeQuery.ReturnableResultNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leethanh.common.entity.Roles;
import com.leethanh.common.entity.Users;

@Service
@Transactional
public class UsersService {

	@Autowired
	UsersRepository usersRepository;

	@Autowired
	RolesRepository rolesRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	public List<Users> listAll() {
		return (List<Users>) usersRepository.findAll();
	}

	public List<Roles> listRoles() {
		return (List<Roles>) rolesRepository.findAll();
	}

	public void save(Users user) {
		boolean isUpdatingUser = (user.getId() != null);
		if (isUpdatingUser) {
			Users existedUser = usersRepository.findById(user.getId()).get();
			String password = user.getPassword();
			if (password.isEmpty()) // user doesnt want to change the password
			{
				String oldPassword = existedUser.getPassword();
				user.setPassword(oldPassword);
			}else 
			{
				encodePassword(user);
			}

		} else {
			encodePassword(user);
		}

		usersRepository.save(user);

	}

	public void encodePassword(Users user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
	}

	public boolean isEmailUnique(Integer id, String email) {
		System.out.println("id in isEmailUnique service= "+id);
		Users user = usersRepository.getUsersByEmail(email);
		
		if (user ==null && id ==-1 )
		{
			return true ;
		}
		
		
		System.out.println("is updating");
		if (id !=-1 )  //meaning that is updating  
		{
			System.out.println("email null ?"+(user ==null));
			if (user !=null) //not existed 
			{
				if (user.getId()==id ) //email compatible with its own id 
				{
					return true ;
				}
			}else  //existed
			{
				return true ;
			}
		}
		return false ;
		
		
	}

	public Users getUserById(Integer id) throws UserNotFoundException {
		try {
			return usersRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			throw new UserNotFoundException("Could not find any user with ID :" + id);
		}
	}
	
	public void delete(Integer id) throws UserNotFoundException {
		Integer countById=usersRepository.countById(id);
		if (countById ==null || countById ==0 )
		{
			throw new UserNotFoundException("Could not find any user with ID :" + id);
		}
		usersRepository.deleteById(id);
	}
	
	
	public void updateUserStatus(Integer id,boolean status)  {
		usersRepository.updateEnabledStatus(id, status);
	}
}
