package org.shtrudell.server.controller;

import org.shtrudell.common.integration.PersonnelDAO;
import org.shtrudell.common.model.DepartmentDTO;
import org.shtrudell.common.model.EmployeeDTO;
import org.shtrudell.server.integration.DepartmentDAO;
import org.shtrudell.server.integration.EmployeeDAO;

import java.util.ArrayList;
import java.util.List;

public class Controller implements PersonnelDAO {
    private final EmployeeDAO employeeDAO;
    private final DepartmentDAO departmentDAO;

    public Controller() {
        employeeDAO = new EmployeeDAO();
        departmentDAO = new DepartmentDAO();
    }
    @Override
    public void createEmployee(EmployeeDTO employee) {
        try {
            if (employeeDAO.findEmployeeByNameAndSurname(employee.getFirstName(), employee.getLastName(), true) != null) {
                throw new Exception("Account for: " + employee.getFirstName() + " " + employee.getLastName() + " already exists");
            }
            employeeDAO.create( employee.getFirstName(), employee.getLastName(),
                                employee.getMiddleName(), employee.getGender(),
                                employee.getDate(), employee.getAddress());
        } catch (Exception e) {
            System.out.println("Could not create account for: " + employee.getFirstName() + " " + employee.getFirstName());
        }
    }
    @Override
    public void deleteEmployee(EmployeeDTO employee) {
        try {
            employeeDAO.delete(employee.getFirstName(), employee.getLastName());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Could not delete account for: " + employee.getFirstName() + " " + employee.getLastName());
        }
    }
    @Override
    public void updateEmployee(EmployeeDTO employee) {

    }

    @Override
    public void createEmployees(List<EmployeeDTO> employees) {

    }

    @Override
    public void deleteEmployees(List<EmployeeDTO> employees) {

    }

    @Override
    public void updateEmployees(List<EmployeeDTO> employees) {

    }
    @Override
    public List<EmployeeDTO> findAllEmployees() {
        return new ArrayList<>(employeeDAO.findAll());
    }
    @Override
    public void createDepartment(DepartmentDTO department) {

    }
    @Override
    public void deleteDepartment(DepartmentDTO department) {

    }
    @Override
    public void updateDepartment(DepartmentDTO department) {

    }

    @Override
    public void createDepartments(List<DepartmentDTO> departments) {

    }

    @Override
    public void deleteDepartments(List<DepartmentDTO> departments) {

    }

    @Override
    public void updateDepartments(List<DepartmentDTO> departments) {

    }
    @Override
    public List<DepartmentDTO> findAllDepartments() {
        return new ArrayList<>(departmentDAO.findAll());
    }
}
