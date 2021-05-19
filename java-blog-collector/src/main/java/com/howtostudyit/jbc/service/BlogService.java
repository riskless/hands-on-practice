package com.howtostudyit.jbc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.howtostudyit.jbc.entity.Blog;
import com.howtostudyit.jbc.entity.Item;
import com.howtostudyit.jbc.entity.User;
import com.howtostudyit.jbc.exception.RssException;
import com.howtostudyit.jbc.repository.BlogRepository;
import com.howtostudyit.jbc.repository.ItemRepository;
import com.howtostudyit.jbc.repository.UserRepository;

@Service
public class BlogService {
	@Autowired
	private BlogRepository blogRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RssService rssService;
	
	@Autowired
	private ItemRepository itemRepository;
	
	public void saveItems(Blog blog) {
		// need to rss xsd
		// test url -> http://feeds.feedburner.com/javavids?format=xml, https://eslnews.org.nz/?feed=rss2
		// rss feeds java -> https://blog.feedspot.com/java_rss_feeds/
		// rss feeds news -> https://blog.feedspot.com/world_news_rss_feeds/
		try {
			List<Item> items = rssService.getItems(blog.getUrl());
			for (Item item : items) {
				Item savedItem = itemRepository.findByBlogAndLink(blog, item.getLink());
				if (savedItem == null) {
					item.setBlog(blog);
					itemRepository.save(item);
				}
			}
		} catch (RssException e) {
			e.printStackTrace();
		}
	}
	
	// 1 hour = 60 seconds * 60 minutes * 1000
	// run automatically when the server is started
	@Scheduled(fixedDelay = 3600000L)
	public void reloadBlogs() {
		List<Blog> blogs = blogRepository.findAll();
		for (Blog blog : blogs) {
			saveItems(blog);
		}
	}
	  
	public void save(Blog blog, String name) {
		User user = userRepository.findByName(name);
		blog.setUser(user);
		blogRepository.save(blog);
		saveItems(blog);

	}
	
	public void delete(int id) {
		blogRepository.delete(id);		
	}
	
	// <global-method-security pre-post-annotations="enabled" /> in security.xml
	// @P("blog") -> it can be used in the @PreAuthorize("#blog.user.name)
	@PreAuthorize("#blog.user.name == authentication.name or hasRole('ROLE_ADMIN')")
	public void delete(@P("blog") Blog blog) {
		blogRepository.delete(blog);		
	}

	public Blog findOne(int id) {
		return blogRepository.findOne(id);
	}
}
