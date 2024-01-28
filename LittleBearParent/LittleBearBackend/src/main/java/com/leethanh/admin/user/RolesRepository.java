package com.leethanh.admin.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.leethanh.common.entity.Roles;

@Repository
public interface RolesRepository extends CrudRepository<Roles, Integer> {

}
