package com.howtostudyit.jbc.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.howtostudyit.jbc.entity.Blog;
import com.howtostudyit.jbc.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Integer>{
//	// All items
//	List<Item> findByBlog(Blog blog);
	
	// Pagination
	List<Item> findByBlog(Blog blog, Pageable pageable);

	Item findByBlogAndLink(Blog blog, String link);
}
