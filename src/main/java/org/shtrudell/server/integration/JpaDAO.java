package org.shtrudell.server.integration;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public abstract class JpaDAO {
    protected final EntityManagerFactory emFactory;
    protected final ThreadLocal<EntityManager> threadLocalEntityManager = new ThreadLocal<>();

    protected JpaDAO() {
        emFactory = Persistence.createEntityManagerFactory("Lab6");
    }

    public void update() {
        commitTransaction();
    }

    protected EntityManager beginTransaction() {
        EntityManager em = emFactory.createEntityManager();
        threadLocalEntityManager.set(em);
        EntityTransaction transaction = em.getTransaction();
        if(!transaction.isActive()) {
            transaction.begin();
        }
        return em;
    }

    protected void commitTransaction() {
        threadLocalEntityManager.get().getTransaction().commit();
    }
}
