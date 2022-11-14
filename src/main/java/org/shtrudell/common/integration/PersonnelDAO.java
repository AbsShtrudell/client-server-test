package org.shtrudell.common.integration;

import org.shtrudell.common.model.DepartmentDTO;
import org.shtrudell.common.model.EmployeeDTO;
import org.shtrudell.common.model.Gender;

import java.util.Date;
import java.util.List;

public interface PersonnelDAO {
    public void createEmployee(EmployeeDTO employee);
    public void deleteEmployee(EmployeeDTO employee);
    public void updateEmployee(EmployeeDTO employee);
    public void createEmployees(List<EmployeeDTO> employees);
    public void deleteEmployees(List<EmployeeDTO> employees);
    public void updateEmployees(List<EmployeeDTO> employees);
    public List<EmployeeDTO> findAllEmployees();
    public void createDepartment(DepartmentDTO department);
    public void deleteDepartment(DepartmentDTO department);
    public void updateDepartment(DepartmentDTO department);
    public void createDepartments(List<DepartmentDTO> departments);
    public void deleteDepartments(List<DepartmentDTO> departments);
    public void updateDepartments(List<DepartmentDTO> departments);
    public List<DepartmentDTO> findAllDepartments();
}
