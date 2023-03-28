package com.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.jpa.util.JPAUtil;
import com.model.User;

import java.util.List;

public class UserDAO {

    private static EntityManager em;

    public UserDAO() {
        EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();
        em = emf.createEntityManager();
        em.setProperty("hibernate.show_sql", "false");

    }

    public void add(User user) {
    	em.clear();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
    }

    public boolean update(User user) {
    	em.clear();
    	try {
    		em.getTransaction().begin();
    		em.merge(user);
    		em.getTransaction().commit();
    		return true;
    	} catch(Exception e) {
    		return false;
    	}
    }

    public void delete(User user) {
    	em.clear();
        em.getTransaction().begin();
        em.remove(user);
        em.getTransaction().commit();
    }

    public User findOne(long userId) {
    	em.clear();
        return em.find(User.class, userId);
    }
    
    public User exists(String email) {
    	em.clear();
        Query query = em.createQuery("SELECT u FROM User u WHERE u.email = :email");
        query.setParameter("email", email);
        try {
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public User findUserByEmailAndPassword(String email, String password) {
    	em.clear();
        Query query = em.createQuery("SELECT u FROM User u WHERE u.email = :email AND u.password = :password");
        query.setParameter("email", email);
        query.setParameter("password", password);
        try {
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    
    public List<User> getAll() {
    	em.clear();
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }
}

