package com.leethanh.admin.category;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.leethanh.common.entity.Category;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer>, CrudRepository<Category, Integer>{

}
