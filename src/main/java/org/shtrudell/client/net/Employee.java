package org.shtrudell.client.net;

import org.shtrudell.common.model.DepartmentDTO;
import org.shtrudell.common.model.EmployeeDTO;
import org.shtrudell.common.model.Gender;

import java.util.Date;

public class Employee implements EmployeeDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private Gender gender;
    private Date date;
    private String address;
    private DepartmentDTO department;

    public Employee(String firstName, String lastName, String middleName, Gender gender, Date date, String address) {
        this.id = 0L;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.gender = gender;
        this.date = date;
        this.address = address;
    }

    @Override
    public String getMiddleName() {
        return middleName;
    }

    @Override
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Override
    public Gender getGender() {
        return gender;
    }

    @Override
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public void setDepartment(DepartmentDTO department) {
        this.department = department;
    }

    @Override
    public DepartmentDTO getDepartment() {
        return department;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", gender=" + gender +
                ", date=" + date +
                ", address='" + address + '\'' +
                ", department=" + department +
                '}';
    }
}
