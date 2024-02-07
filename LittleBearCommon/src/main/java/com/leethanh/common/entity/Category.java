package com.leethanh.common.entity;

import java.util.HashSet;
import java.util.Set;

import org.aspectj.weaver.patterns.IfPointcut.IfFalsePointcut;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity(name="categories")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id ; 
	
	@Column(length = 128,nullable = false,unique = true)
	private String name;
	
	@Column(length = 64,nullable = false,unique = true)
	private String alias;
	
	@Column(length = 64,nullable = false)
	private String image;
	
	private boolean enabled;
	
	@OneToOne
	@JoinColumn(name = "parent_id")
	private Category parent;
	
	@OneToMany(mappedBy = "parent")
	private Set<Category>children=new HashSet<>();
	
}
