package com.jpa.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
	private static final String PERSISTENCE_UNIT_NAME = "myPersistenceUnit";
	private static final EntityManagerFactory entityManagerFactory;

	  static {
	    try {
	      entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	    } catch (Throwable ex) {
	      System.err.println("Error al crear EntityManagerFactory: " + ex);
	      throw new ExceptionInInitializerError(ex);
	    }
	  }

	  public static EntityManagerFactory getEntityManagerFactory() {
	    return entityManagerFactory;
	  }

	  public static void shutdown() {
	    getEntityManagerFactory().close();
	  }
}
