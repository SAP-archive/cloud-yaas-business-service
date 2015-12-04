package com.sample.wishlist.model;

import java.io.Serializable; 

public class WishlistItemEntityId implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String product;
	
	public WishlistItemEntityId() {
	}
	
	public WishlistItemEntityId(String product) {

		this.product = product; 
	}
	
	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WishlistItemEntityId other = (WishlistItemEntityId) obj;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product)) {
			return false;
		} 
		return true;
	}	
	
}
