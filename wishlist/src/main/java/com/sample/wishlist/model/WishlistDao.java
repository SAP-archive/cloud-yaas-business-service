package com.sample.wishlist.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class WishlistDao {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public void persist(WishlistEntity entity) {		
		em.persist(entity);
	}
		
	@Transactional
	public void update(WishlistEntity entity) {
		em.merge(entity);
	}
	
	@Transactional
	public WishlistEntity find(WishlistEntityId id) {
		WishlistEntity entity = em.find(WishlistEntity.class, id);
		//em.refresh(entity);
		return entity; 
	}

	@SuppressWarnings("unchecked")
	public List<WishlistEntity> findAll(String tentant) {
		Query query = em.createQuery("SELECT w FROM WishlistEntity w WHERE w.tenant = :tenant");
		return query.setParameter("tenant", tentant).getResultList();
	}

	@Transactional
	public void delete(WishlistEntityId primaryKey) {
		WishlistEntity entity = em.find(WishlistEntity.class, primaryKey);
		if (entity != null)
			em.remove(entity);
	}

}