package com.howtostudyit.jbc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.howtostudyit.jbc.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{

	Role findByName(String name);

}
