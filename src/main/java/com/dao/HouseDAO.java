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
import com.model.HouseRender;
import com.model.Images;
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
	
	public List<HouseRender> getAll() {
		List<HouseRender> data = new ArrayList<HouseRender>();
		List<House> list = new ArrayList<House>();
		
		try {
			TypedQuery<House> query = em.createQuery("SELECT h FROM House h WHERE h.status = :status", House.class);
			query.setParameter("status", "DISPONIBLE");
			list = query.getResultList();	
			for(House house : list) {
				List<String> urls = new ArrayList<String>();
				HouseRender hr = new HouseRender();				
				// Consultamos sus imagenes
				TypedQuery<Images> q = em.createQuery("SELECT i FROM Images i WHERE i.house.id = :houseId", Images.class);
				q.setParameter("houseId", house.getId());
				List<Images> images = q.getResultList();
				for(Images img: images) {
					urls.add(img.getName());
				}
				
				User user = house.getUser();				
				hr.setUser(user);
				hr.setHouse(house);
				hr.setUrls(urls);
				data.add(hr);
			}
			
		} catch(Exception e) {
			System.out.println("Error al obtener todas las casas");
		}
		
		return data;
		
	}
	
	public HouseRender getOneHouseData(long house_id) {
		HouseRender data = new HouseRender();		
		try {
			TypedQuery<House> query = em.createQuery("SELECT h FROM House h WHERE h.id = :house_id", House.class);
			query.setParameter("house_id", house_id);
			House house  = query.getSingleResult();
			data.setHouse(house);
			List<String> list = new ArrayList<String>();
			
			TypedQuery<Images> q = em.createQuery("SELECT i FROM Images i WHERE i.house.id = :house_id", Images.class);
			q.setParameter("house_id", house.getId());
			List<Images> images = q.getResultList();
			for(Images img: images) {
				list.add(img.getName());
			}
			
			data.setUser(house.getUser());
			data.setUrls(list);
			
		} catch(Exception e) {
			System.out.println("Error al obtener datos de la casa");
		}
		
		return data;
		
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
	
	public House add(House house) {
		House result = new House();
	    EntityTransaction transaction = em.getTransaction();
	    try {
	        transaction.begin();
	        em.persist(house);
	        result = house;
	        transaction.commit();
	    } catch (Exception e) {
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
	
	public long countAvailable(Long id) {
		TypedQuery<Long> query = em.createQuery("SELECT COUNT(h) FROM House h WHERE h.status = 'DISPONIBLE' AND h.user.id = :userId", Long.class);
		query.setParameter("userId", id);
		Long count = query.getSingleResult();
		return count;

	}
	
	
}
