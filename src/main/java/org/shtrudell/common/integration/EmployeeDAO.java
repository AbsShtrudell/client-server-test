package org.shtrudell.common.integration;

import org.shtrudell.common.model.EmployeeDTO;
import org.shtrudell.server.model.Employee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO extends JpaDAO{

    public EmployeeDAO() {
    }

    public Employee findEmployeeByNameAndSurname(String name, String surname, boolean endTransactionAfterSearching){
        if(name == null || surname == null) {
            return null;
        }
        try {
            EntityManager em = beginTransaction();
            try {
                var query = em.createNamedQuery("findEmployeeByNameAndSurname", Employee.class);
                query.setParameter("name", name);
                query.setParameter("surname", surname);
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

    public List<Employee> findAll() {
        try {
            EntityManager em = beginTransaction();
            try {
                return em.createNamedQuery("findAllEmployees", Employee.class).getResultList();
            } catch (NoResultException noSuchEmployee) {
                return new ArrayList<>();
            }
        } finally {
            commitTransaction();
        }
    }

    public void create(EmployeeDTO employee) {
        try {
            EntityManager em = beginTransaction();
            em.persist(employee);
        } finally {
            commitTransaction();
        }
    }

    public void delete(Long id) {
        try {
            EntityManager em = beginTransaction();
            em.createNamedQuery("deleteEmployeeById", Employee.class).
                    setParameter("employeeId", id).executeUpdate();
        } finally {
            commitTransaction();
        }
    }
}
