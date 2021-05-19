package com.howtostudyit.jbc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.howtostudyit.jbc.entity.Blog;
import com.howtostudyit.jbc.entity.User;

public interface BlogRepository extends JpaRepository<Blog, Integer> {
	List<Blog> findByUser(User user);
}
