package com.howtostudyit.jbc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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

@Transactional
@Service
public class InitDbService {
	// Spring will automatically create the class based on related Interface 
	@Autowired
	private RoleRepository roleRepoistory;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BlogRepository blogRepository;

	@Autowired
	private ItemRepository itemRepository;

	@PostConstruct // called automatically by Sprint context
	public void init() {
		if(roleRepoistory.findByName("ROLE_ADMIN") == null) {
			Role roleUser = new Role();
			roleUser.setName("ROLE_USER");
			roleRepoistory.save(roleUser);
	
			Role roleAdmin = new Role();
			roleAdmin.setName("ROLE_ADMIN");
			roleRepoistory.save(roleAdmin);
	
			User userAdmin = new User();
			userAdmin.setName("admin");			
//			userAdmin.setPassword("admin"); // plain text
			
			// encode password
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();		
			userAdmin.setPassword(encoder.encode("admin"));			
			userAdmin.setEnabled(true);
			
	
			List<Role> roles = new ArrayList<Role>();
			roles.add(roleAdmin);
			roles.add(roleUser);
			userAdmin.setRoles(roles);
			userRepository.save(userAdmin);
	
			Blog blogJavavids = new Blog();
			blogJavavids.setName("JavaVids");
			blogJavavids.setUrl("http://feeds.feedburner.com/javavids?format=xml");
			blogJavavids.setUser(userAdmin);
			blogRepository.save(blogJavavids);
			
//			// @Scheduled(fixedDelay = 3600000L) public void reloadBlogs()
//			Item item1 = new Item();
//			item1.setBlog(blogJavavids);
//			item1.setTitle("first");
//			item1.setLink("http://www.javavids.com");
//			item1.setPublishedDate(new Date());
//			itemRepository.save(item1);
//			
//			Item item2 = new Item();
//			item2.setBlog(blogJavavids);
//			item2.setTitle("second");
//			item2.setLink("http://www.javavids.com");
//			item2.setPublishedDate(new Date());
//			itemRepository.save(item2);
		}
	}
}
