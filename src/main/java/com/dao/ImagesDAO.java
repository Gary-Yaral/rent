package com.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.jpa.util.JPAUtil;
import com.model.Images;
import com.model.User;

public class ImagesDAO {
	private static EntityManager em;

    public ImagesDAO() {
        EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();
        em = emf.createEntityManager();

    }
	
	public List<Images> getAll() {
		List<Images> list = new ArrayList<Images>();
		try {
			TypedQuery<Images> query = em.createQuery("SELECT h FROM House h", Images.class);
			list = query.getResultList();			
		} catch(Exception e) {
			System.out.println("Error al obtener todas las casas");
		}
		
		return list;
	}
	
	public String getNextIndex() {
		if(getAll().size() == 0) {
			return "1";
		}
		try {
			Query query = em.createQuery("SELECT h.id FROM House h ORDER BY h.id DESC");
			query.setMaxResults(1);
			String lastIndex = String.valueOf((Long) query.getSingleResult());
			int nextIndex = Integer.parseInt(lastIndex) + 1;
			return String.valueOf(nextIndex);			
		} catch(Exception e) {
			return null;
		}		
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
	
	public User findUser(long id) {
		User user = null;
		try {
			user = em.find(User.class, id);			
		} catch(Exception e) {
			System.out.println("No existe ese usuario");
		}
		
		return user;
	}
	
	public Images findOne(long id) {
		Images house = null;
		try {
			house = em.find(Images.class, id);			
		} catch(Exception e) {
			System.out.println("No existe esta casa");
		}
		
		return house;
	}
	
	public boolean update(Images house) {
	    EntityTransaction transaction = em.getTransaction();
	    boolean result = true;
	    try {
	        transaction.begin();
	        em.merge(house);
	        transaction.commit();
	    } catch (Exception e) {
	        result = false;
	        if (transaction.isActive()) {
	            transaction.rollback();
	        }
	        e.printStackTrace();
	    } 
	    return result;
	}
	
	public boolean remove(Long id) {
		Images house = em.find(Images.class, id);
		
		if (house == null) return false;
		
		try {
			em.getTransaction().begin();
			em.remove(house);
			em.getTransaction().commit();
		} catch (Exception e) {
	        em.getTransaction().rollback();
	        return false;
		}
	    return true;
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
