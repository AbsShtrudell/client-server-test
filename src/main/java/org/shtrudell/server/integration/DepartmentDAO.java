package org.shtrudell.server.integration;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.shtrudell.common.model.DepartmentDTO;
import org.shtrudell.server.model.Department;

import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO extends JpaDAO {

    public Department findByName(String name, String type, boolean endTransactionAfterSearching){
        if(name == null) {
            return null;
        }
        try {
            EntityManager em = beginTransaction();
            try {
                var query = em.createNamedQuery("findDepartmentByName", Department.class);
                query.setParameter("name", name);
                return query.getSingleResult();
            } catch (NoResultException noSuchEmployee) {
                return null;
            }
        } finally {
            if (endTransactionAfterSearching) {
                commitTransaction();
            }
        }
    }

    public List<Department> findAll() {
        try {
            EntityManager em = beginTransaction();
            try {
                return em.createNamedQuery("findAllDepartments", Department.class).getResultList();
            } catch (NoResultException noSuchEmployee) {
                return new ArrayList<>();
            }
        } finally {
            commitTransaction();
        }
    }

    public void create(DepartmentDTO department) {
        try {
            EntityManager em = beginTransaction();
            em.persist(department);
        } finally {
            commitTransaction();
        }
    }

    public void delete(Long id) {
        try {
            EntityManager em = beginTransaction();
            em.createNamedQuery("deleteDepartmentById", Department.class).
                    setParameter("departmentId", id).executeUpdate();
        } finally {
            commitTransaction();
        }
    }

    public void delete(String name) {
        try {
            EntityManager em = beginTransaction();
            em.createNamedQuery("deleteDepartmentByName", Department.class).
                    setParameter("name", name).executeUpdate();
        } finally {
            commitTransaction();
        }
    }
}
