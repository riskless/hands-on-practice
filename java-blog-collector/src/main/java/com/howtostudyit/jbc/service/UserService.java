package com.howtostudyit.jbc.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.howtostudyit.jbc.entity.Blog;
import com.howtostudyit.jbc.entity.Item;
import com.howtostudyit.jbc.entity.Role;
import com.howtostudyit.jbc.entity.User;
import com.howtostudyit.jbc.repository.BlogRepository;
import com.howtostudyit.jbc.repository.ItemRepository;
import com.howtostudyit.jbc.repository.RoleRepository;
import com.howtostudyit.jbc.repository.UserRepository;

@Service
@Transactional
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BlogRepository blogRepository;

	@Autowired
	private ItemRepository itemRepository;

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public User findOne(int id) {
		return userRepository.findOne(id);
	}

	@Transactional // Inside transaction
	public User findOneWithBlogs(int id) {
		User user = findOne(id);
		List<Blog> blogs = blogRepository.findByUser(user);
		for (Blog blog : blogs) {
//			// All items
//			List<Item> items = this.itemRepository.findByBlog(blog);
			
			// Pagination
			List<Item> items = this.itemRepository.findByBlog(blog, new PageRequest(0, 10, Direction.DESC, "publishedDate"));
			blog.setItems(items);
		}
		user.setBlogs(blogs);
		return user;
	}
	
//	// plain password
//	public void save(User user) { 
//		userRepository.save(user);
//		
//	}
	
	// Encode password
	public void save(User user) {
	    user.setEnabled(true);
	    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	    user.setPassword(encoder.encode(user.getPassword()));
	        
	    List<Role> roles = new ArrayList<Role>();
	    roles.add(roleRepository.findByName("ROLE_USER"));
	    user.setRoles(roles);	   
	    
		userRepository.save(user);
		
	}

	public User findOneWithBlogs(String name) {
		User user = userRepository.findByName(name);
		return findOneWithBlogs(user.getId());
	}

	public void delete(int id) {
		userRepository.delete(id);		
	}

	public User findOne(String username) {
		return userRepository.findByName(username);
	}
}
