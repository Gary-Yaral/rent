package com.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import com.jpa.util.JPAUtil;
import com.model.Images;

public class ImagesDAO {
	private static EntityManager em;

    public ImagesDAO() {
        EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();
        em = emf.createEntityManager();

    }
	
	public List<Images> getAll(long house_id) {
		List<Images> list = new ArrayList<Images>();
		try {
			TypedQuery<Images> query = em.createQuery("SELECT img FROM Images img WHERE img.house.id = :houseId", Images.class);
			query.setParameter("houseId", house_id);
			list = query.getResultList();		
		} catch(Exception e) {
			System.out.println("Error al obtener todas las casas");
		}
		
		return list;
	}
	
	public boolean add(Images images) {
	    boolean result = true;
	    EntityTransaction transaction = em.getTransaction();
	    try {
	        transaction.begin();
	        em.persist(images);
	        transaction.commit();
	    } catch (Exception e) {
	        result = false;
	        System.out.print(e);
	        if (transaction.isActive()) {
	            transaction.rollback();
	        }
	        e.printStackTrace();
	    } 
	    return result;
	}
	
	public void removeAll(Long house_id) {
		TypedQuery<Images> query = em.createQuery("SELECT img FROM Images img WHERE img.house.id = :houseId", Images.class);
		query.setParameter("houseId", house_id);
		List<Images> imagesToDelete = query.getResultList();
		em.getTransaction().begin();
		for (Images image : imagesToDelete) {
		    em.remove(image);
		}
		em.getTransaction().commit();
	}
}
