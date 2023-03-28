package com.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.jpa.util.JPAUtil;

import com.model.Rental;

public class RentalDAO {
	private static EntityManager em;

    public RentalDAO() {
        EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();
        em = emf.createEntityManager();

    }
    
	@SuppressWarnings("unchecked")
	public List<Rental> getAllByUserId(String userId) {
		em.clear();
		List<Rental> list = new ArrayList<Rental>();
		Long userIdLong = Long.parseLong(userId);
		try {
	        em.getTransaction().begin();
	        Query query = em.createQuery(
	        		"SELECT r FROM Rental r JOIN r.house h WHERE h.id_user = :id_user", Rental.class); 
	        query.setParameter("id_user", userIdLong);
	        list = query.getResultList();
	        em.getTransaction().commit();
	        return list;
	    } catch (Exception e) {
	        em.getTransaction().rollback();
	    }
		return list;
	}
	
	public boolean add(Rental rental) {
		em.clear();
	    boolean result = true;
	    EntityTransaction transaction = em.getTransaction();
	    try {
	        transaction.begin();
	        em.persist(rental);
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
	
	public Rental findOne(long id) {
		em.clear();
		Rental rental = null;
		try {
			rental = em.find(Rental.class, id);			
		} catch(Exception e) {
			System.out.println("No existe esta casa");
		}
		
		return rental;
	}
	
	public boolean update(Rental rental) {
		em.clear();
	    EntityTransaction transaction = em.getTransaction();
	    boolean result = true;
	    try {
	        transaction.begin();
	        em.merge(rental);
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
		em.clear();
		Rental rental = em.find(Rental.class, id);
		
		if (rental == null) return false;
		
		try {
			em.getTransaction().begin();
			em.remove(rental);
			em.getTransaction().commit();
		} catch (Exception e) {
	        em.getTransaction().rollback();
	        return false;
		}
	    return true;
	}

}
