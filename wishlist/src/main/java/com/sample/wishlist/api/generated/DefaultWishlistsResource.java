
package com.sample.wishlist.api.generated;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sample.wishlist.model.WishlistDao;
import com.sample.wishlist.model.WishlistEntity;
import com.sample.wishlist.model.WishlistEntityId;
import com.sample.wishlist.model.WishlistItemEntity;

/**
* Resource class containing the custom logic. Please put your logic here!
*/
@Component("apiWishlistsResource")
@Singleton
public class DefaultWishlistsResource implements com.sample.wishlist.api.generated.WishlistsResource
{
	private final Logger logger = LoggerFactory.getLogger(DefaultWishlistsResource.class);
	
	@javax.ws.rs.core.Context
	private javax.ws.rs.core.UriInfo uriInfo;
	
	@Autowired
	private WishlistDao wishlistDao;

	/* GET / */
	@Override
	public Response get(final PagedParameters paged, final YaasAwareParameters yaasAware)
	{		
		// place some logic here
		return Response.ok().entity(wishlistDao.findAll(yaasAware.getHybrisTenant())).build();
	}

	/* POST / */
	@Override
	public Response post(final YaasAwareParameters yaasAware, final Wishlist wishlist)
	{
		WishlistEntity entity = wishlistDao.find(new WishlistEntityId(yaasAware.getHybrisTenant(), wishlist.getId()));

		if (entity != null) {
			return Response.status(Status.CONFLICT).build();		
		} 

		try {
			entity = new WishlistEntity();
			entity.setTenant(yaasAware.getHybrisTenant());
			entity.setId(wishlist.getId());
			entity.setDescription(wishlist.getDescription());
			entity.setTitle(wishlist.getTitle());
			entity.setOwner(wishlist.getOwner());
			entity.setItems(convertToWishlistItemEntity(wishlist.getItems(), wishlist.getId(), yaasAware.getHybrisTenant()));
			entity.setCreatedAt(wishlist.getCreatedAt());
			
			logger.error("persist entity: " + entity.getId());
			
			wishlistDao.persist(entity);
	
			return Response.created(uriInfo.getAbsolutePath()).status(Status.CREATED).build();		
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	/* GET /{wishlistId} */
	@Override
	public Response getByWishlistId(final YaasAwareParameters yaasAware, final java.lang.String wishlistId)
	{
		return Response.ok().entity(wishlistDao.find(new WishlistEntityId(yaasAware.getHybrisTenant(), wishlistId))).build();
	}

	/* PUT /{wishlistId} */
	@Override
	public Response putByWishlistId(final YaasAwareParameters yaasAware, final java.lang.String wishlistId, final Wishlist wishlist)
	{
		WishlistEntity entity = wishlistDao.find(new WishlistEntityId(yaasAware.getHybrisTenant(), wishlistId));

		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		try {
			entity.setDescription(wishlist.getDescription());
			entity.setTitle(wishlist.getTitle());
			entity.setOwner(wishlist.getOwner());
			entity.setItems(convertToWishlistItemEntity(wishlist.getItems(), wishlist.getId(), yaasAware.getHybrisTenant()));
			entity.setCreatedAt(wishlist.getCreatedAt());
			wishlistDao.update(entity);
			
			// place some logic here
			return Response.ok().build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	/* DELETE /{wishlistId} */
	@Override
	public Response deleteByWishlistId(final YaasAwareParameters yaasAware, final java.lang.String wishlistId)
	{
		WishlistEntity entity = wishlistDao.find(new WishlistEntityId(yaasAware.getHybrisTenant(), wishlistId));
		if (entity==null) {
			return Response.status(Status.NO_CONTENT).build();
		}
		
		wishlistDao.delete(new WishlistEntityId(yaasAware.getHybrisTenant(), wishlistId));
		return Response.ok().build();
	}

	/* GET /{wishlistId}/wishlistItems */
	@Override
	public Response getByWishlistIdWishlistItems(final PagedParameters paged, final YaasAwareParameters yaasAware, final java.lang.String wishlistId)
	{
		WishlistEntity entity = wishlistDao.find(new WishlistEntityId(yaasAware.getHybrisTenant(), wishlistId));
		if (entity == null) {
			return Response.noContent().build();
		}
		List<WishlistItemEntity> items = entity.getItems();
		if (items == null || items.size()==0) {
			return Response.status(Status.NO_CONTENT).build();
		}		
		return Response.ok().entity(convertToWishlistItem(items)).build();
	}

	/* POST /{wishlistId}/wishlistItems */
	@Override
	public Response postByWishlistIdWishlistItems(final YaasAwareParameters yaasAware, final java.lang.String wishlistId, final WishlistItem wishlistItem)
	{
		WishlistEntity entity = wishlistDao.find(new WishlistEntityId(yaasAware.getHybrisTenant(), wishlistId));

		if (entity != null) {
			WishlistItemEntity itemEntity = convertToWishlistItemEntity(wishlistItem, wishlistId, yaasAware.getHybrisTenant());
			entity.getItems().add(itemEntity);				
			try {
				wishlistDao.update(entity);  
				
				return Response.created(uriInfo.getAbsolutePath()).status(Status.CREATED).build();
			} catch (Exception e) {
				Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		} 
		
		return Response.status(400).build();	
	}
	
	private List<WishlistItemEntity> convertToWishlistItemEntity(List<WishlistItem> items, String wishlistId, String tenant) {
		if (items == null) return null;
		
		ArrayList<WishlistItemEntity> result = new ArrayList<WishlistItemEntity>(items.size());
		for (WishlistItem w : items) {
			WishlistItemEntity e = convertToWishlistItemEntity(w, wishlistId, tenant);
			result.add(e);
		}
		return result; 	
	}
	
	private WishlistItemEntity convertToWishlistItemEntity(WishlistItem item, String wishlistId, String tenant) {
		WishlistItemEntity e = new WishlistItemEntity();
		e.setAmount(item.getAmount());
		e.setCreatedAt(item.getCreatedAt());
		e.setNote(item.getNote());
		e.setCategory(item.getCategory());
		e.setLink(item.getLink());
		e.setProduct(item.getProduct());
		return e; 	
	}
	
	private List<WishlistItem> convertToWishlistItem(List<WishlistItemEntity> items) {
		if (items == null) return null;
		
		ArrayList<WishlistItem> result = new ArrayList<WishlistItem>(items.size());
		for (WishlistItemEntity w : items) {
			WishlistItem e = new WishlistItem();
			e.setAmount(w.getAmount());
			e.setCreatedAt(w.getCreatedAt());
			e.setNote(w.getNote());
			e.setCategory(w.getCategory());
			e.setLink(w.getCategory());
			e.setProduct(w.getProduct());
			result.add(e);
		}
		return result; 
		
	}
	

}
