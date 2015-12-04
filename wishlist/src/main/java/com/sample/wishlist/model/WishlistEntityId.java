package com.sample.wishlist.model;

import java.io.Serializable;

public class WishlistEntityId implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private String id; 	
		
		private String tenant;
		
		public WishlistEntityId() {
		}

		public WishlistEntityId(String tenant, String id) {
			this.tenant = tenant; 
			this.id = id; 
		}
		
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
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((id == null) ? 0 : id.hashCode());
			result = prime * result + ((tenant == null) ? 0 : tenant.hashCode());
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
			WishlistEntityId other = (WishlistEntityId) obj;
			if (id == null) {
				if (other.id != null)
					return false;
			} else if (!id.equals(other.id))
				return false;
			if (tenant == null) {
				if (other.tenant != null)
					return false;
			} else if (!tenant.equals(other.tenant))
				return false;
			return true;
		}	
		
}
