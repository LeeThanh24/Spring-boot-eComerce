package com.leethanh.common.entity;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.type.TrueFalseConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "users")
public class Users {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id ; 
	
	@Column(length = 128, nullable = false,unique = true )
	private String email;
	
	@Column(length = 64,nullable = false)
	private String password;
	
	@Column(name ="first_name",length = 45,nullable = false)
	private String firstName;
	
	@Column(name ="last_name",length = 45,nullable = false)
	private String lastName;
	
	@Column(length = 64)
	private String photos;
	
	@Column(length = 64) 
	private boolean enabled;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "users_roles",
			joinColumns = @JoinColumn(name="user_id"),
			inverseJoinColumns = @JoinColumn(name="role_id")
			)
	private Set<Roles> roles = new HashSet<>();

	
	
	
	public Users() {
		
	}

	
	public Users(String email, String password, String firstName, String lastName) {
		
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer idInteger) {
		this.id = idInteger;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}

	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Roles> getRoles() {
		return roles;
	}

	public void setRoles(Set<Roles> roles) {
		this.roles = roles;
	}
	
	public void addRole(Roles role)
	{
		this.roles.add(role);
	}


	@Override
	public String toString() {
		return "Users [id=" + id + ", email=" + email + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", photos=" + photos + ", enabled=" + enabled + ", roles=" + roles + "]";
	}
	
	@Transient
	public String getPhotoImagePath() {
		
		String path="";
		if (id ==null || photos==null||id<=0 )
		{
			path="/images/default_user.png";
			 
		}else 
		{
			path="/user-photos/"+this.id+"/"+this.photos;
		}
		System.out.println("Enter photo image path in Users "+path);
		return path;
	}
	
	@Transient
	public String getFullname() {
		
		
		return firstName+" "+lastName;
	}
	
}
