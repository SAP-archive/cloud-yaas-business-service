package com.sample.wishlist.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity @IdClass(WishlistEntityId.class)
public class WishlistEntity {

	@Id
	@GeneratedValue( strategy=GenerationType.AUTO )
	@Column(length=255)
	private String id;

	@Id
	@Column(length=255)
	@JsonIgnore	
	private String tenant;

	@Column(length=255)
	@NotNull
	private String owner;
	
	@Column(length=255)
	private String title;
	
	@Column(length=255)
	private String description; 
	
	@Column
	private Date createdAt;
	
	@Column(length=255)
	private String url;
	
	@OneToMany(targetEntity=WishlistItemEntity.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, orphanRemoval=true)
	private List<WishlistItemEntity> items;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTenant() {
		return tenant;
	}

	public void setTenant(String tenant) {
		this.tenant = tenant;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<WishlistItemEntity> getItems() {
		return items;
	}

	public void setItems(List<WishlistItemEntity> items) {
		this.items = items;
	}  
	
}
