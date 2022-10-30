package org.shtrudell.server;

import jakarta.persistence.*;
import org.shtrudell.common.integration.EmployeeDAO;
import org.shtrudell.server.model.Employee;
import org.shtrudell.server.model.Gender;

import java.util.Date;

public class Main {
    private static EntityManager em;

    public static void main(String[] args) {

        init();
        EmployeeDAO employeeDAO = new EmployeeDAO();
        employeeDAO.create(new Employee("Ilya", "Naumenko", "Vadimovich", Gender.MALE, new Date(), "dadad"));
        close();
    }

    static public void init() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory( "Lab6" );
        em = emf.createEntityManager();
    }

    static public void close() {
        em.getEntityManagerFactory().close();
        em.close();
    }
}