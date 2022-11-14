package org.shtrudell.server.integration;

import org.shtrudell.common.model.EmployeeDTO;
import org.shtrudell.common.model.Gender;
import org.shtrudell.server.model.Employee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmployeeDAO extends JpaDAO{

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

    public void create(String firstName, String lastName, String middleName, Gender gender, Date date, String address) {
        try {
            EntityManager em = beginTransaction();
            em.persist(new Employee(firstName, lastName, middleName, gender, date, address));
        } finally {
            commitTransaction();
        }
    }

    public void delete(String firstName, String lastName) {
        try {
            var emp = findEmployeeByNameAndSurname(firstName, lastName, true);
            EntityManager em = beginTransaction();
            System.out.println(emp.getFirstName() + " " + emp.getLastName());
            em.remove(em.contains(emp) ? emp : em.merge(emp));
            em.flush();
        } finally {
            commitTransaction();
            System.out.println(findAll());
        }
    }
}
