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
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
    }

    public void update(User user) {
        em.getTransaction().begin();
        em.merge(user);
        em.getTransaction().commit();
    }

    public void delete(User user) {
        em.getTransaction().begin();
        em.remove(user);
        em.getTransaction().commit();
    }

    public User findOne(int userId) {
        return em.find(User.class, userId);
    }
    
    public User exists(String email) {
        Query query = em.createQuery("SELECT u FROM User u WHERE u.email = :email");
        query.setParameter("email", email);
        try {
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public User findUserByEmailAndPassword(String email, String password) {
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
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }
}

