package com.BrandonHellwarth.RetroGames.Models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="items")
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@Size(min=1,max=200)
	private String name;
	@NotNull
	@Size(min=1,max=200)
	private String description;
	@NotNull
	@Min(value=0)
	private Float price;
	@NotNull
	@Min(value=0)
	private Integer stock;
	@Min(value=0)
	private Integer popularity = 0;
	@Column(updatable=false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createdAt;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updatedAt;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
    		name = "users_items", 
            joinColumns = @JoinColumn(name = "item_id"), 
            inverseJoinColumns = @JoinColumn(name = "user_id")
    		)
    private List<User> users = new ArrayList<User>();
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
    		name = "items_categories", 
            joinColumns = @JoinColumn(name = "category_id"), 
            inverseJoinColumns = @JoinColumn(name = "item_id")
    		)
    private List<Category> categories = new ArrayList<Category>();
    
    
    public Item() {
    	
    }
    
    public Item(Integer popularity) {
    	
    }
    
    public Item(String name, String description, Float price, Integer stock) {
    	
    }
    
    
    @PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }
    
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }

    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Integer getPopularity() {
		return popularity;
	}

	public void setPopularity(Integer popularity) {
		this.popularity = popularity;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
}
