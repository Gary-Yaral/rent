package com.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;

//import com.constants.SystemConstant;
import com.jpa.util.JPAUtil;
import com.model.Tenant;

import java.util.List;

public class TenantDAO {

    private static EntityManager em;
    //private SystemConstant sc = new SystemConstant();
    public TenantDAO() {
        EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();
        em = emf.createEntityManager();
    }

    public void add(Tenant tenant) {
    	em.clear();
        em.getTransaction().begin();
        em.persist(tenant);
        em.getTransaction().commit();
    }

    public void update(Tenant tenant) {
    	em.clear();
        em.getTransaction().begin();
        em.merge(tenant);
        em.getTransaction().commit();
    }

    public void delete(Tenant tenant) {
    	em.clear();
        em.getTransaction().begin();
        em.remove(tenant);
        em.getTransaction().commit();
    }

    public Tenant findOne(int userId) {
    	em.clear();
        return em.find(Tenant.class, userId);
    }
    
    public Tenant exists(String dni, String email) {
    	em.clear();
        Query query = em.createQuery("SELECT u FROM Tenant u WHERE u.email = :email OR u.dni = :dni");
        query.setParameter("email", email);
        query.setParameter("dni", dni);
        try {
            return (Tenant) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Tenant findUserByEmailAndPassword(String email, String password) {
    	em.clear();
        Query query = em.createQuery("SELECT u FROM Tenant u WHERE u.email = :email AND u.password = :password");
        query.setParameter("email", email);
        query.setParameter("password", password);
        try {
            return (Tenant) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    
    public List<Tenant> getAll() {
    	em.clear();
        return em.createQuery("SELECT u FROM Tenant u", Tenant.class).getResultList();
    }
}

