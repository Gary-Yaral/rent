package com.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.jpa.util.JPAUtil;
import com.model.House;
import com.model.User;

public class HouseDAO {
	private static EntityManager em;

    public HouseDAO() {
        EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();
        em = emf.createEntityManager();

    }
    
	@SuppressWarnings("unchecked")
	public List<House> getAllHousesByUserId(String userId) {
		List<House> list = new ArrayList<House>();
		Long userIdLong = Long.parseLong(userId);
		try {
	        em.getTransaction().begin();
	        Query query = em.createQuery("SELECT h FROM House h WHERE h.user.id = :userId");
	        query.setParameter("userId", userIdLong);
	        list = query.getResultList();
	        em.getTransaction().commit();
	        return list;
	    } catch (Exception e) {
	        em.getTransaction().rollback();
	    }
		return list;
	}
	
	public List<House> getAll() {
		List<House> list = new ArrayList<House>();
		try {
			TypedQuery<House> query = em.createQuery("SELECT h FROM House h", House.class);
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
	
	public boolean add(House house) {
	    boolean result = true;
	    EntityTransaction transaction = em.getTransaction();
	    try {
	        transaction.begin();
	        em.persist(house);
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
	
	public User findUser(long id) {
		User user = null;
		try {
			user = em.find(User.class, id);			
		} catch(Exception e) {
			System.out.println("No existe ese usuario");
		}
		
		return user;
	}
	
	public House findOne(long id) {
		House house = null;
		try {
			house = em.find(House.class, id);			
		} catch(Exception e) {
			System.out.println("No existe esta casa");
		}
		
		return house;
	}
	
	public boolean update(House house) {
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
		House house = em.find(House.class, id);
		
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

}
