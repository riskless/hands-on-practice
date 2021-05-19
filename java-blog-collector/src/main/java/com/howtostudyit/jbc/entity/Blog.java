package com.howtostudyit.jbc.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

@Entity
public class Blog {
	@Id
	@GeneratedValue
	private Integer id;
	
	@Size(min=1, message="Invalid URL!")
	@URL(message="Invalid URL!")
	@Column(length=1000)
	private String url;
	
	@Size(min=1, message="Name must be at least 1 characters!")
	private String name;
	
	@ManyToOne
	@JoinColumn(name="user_id") // foreign key
	private User user;
	
//	@OneToMany(mappedBy="blog")
	@OneToMany(mappedBy="blog", cascade=CascadeType.REMOVE) // A blog is deleted with its items
	private List<Item> items;

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	public Integer getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
