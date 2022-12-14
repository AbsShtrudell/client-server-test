package org.shtrudell.server.model;

import java.util.Date;
import jakarta.persistence.*;
import org.shtrudell.common.model.DepartmentDTO;
import org.shtrudell.common.model.Gender;
import org.shtrudell.common.model.EmployeeDTO;

@NamedQueries({
        @NamedQuery(
                name = "deleteEmployeeById",
                query = "DELETE FROM Employee emp WHERE emp.id = :employeeId"
        )
        ,
        @NamedQuery(
                name = "findEmployeeByNameAndSurname",
                query = "SELECT emp FROM Employee emp " +
                        "WHERE emp.firstName LIKE :name " +
                        "and emp.lastName LIKE :surname",
                lockMode = LockModeType.OPTIMISTIC
        )
        ,
        @NamedQuery(
                name = "findAllEmployees",
                query = "SELECT emp FROM Employee emp",
                lockMode = LockModeType.OPTIMISTIC
        )
})

@Entity
@Table(name="employee")
public class Employee implements EmployeeDTO {
    @Id
    @GeneratedValue
    @Column(name="id")
    private Long id;
    @Column(name="firstName")
    private String firstName;
    @Column(name="lastName")
    private String lastName;
    @Column(name="middleName")
    private String middleName;
    @Column(name="gender")
    @Enumerated(EnumType.ORDINAL)
    private Gender gender;
    @Column(name="birthdate")
    private Date date;
    @Column(name="address")
    private String address;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "department")
    private Department department;

    @Version
    @Column(name = "OPTLOCK")
    private int versionNum;

    public Employee() {

    }

    public Employee(String firstName, String lastName, String middleName, Gender gender, Date date, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.gender = gender;
        this.date = date;
        this.address = address;
    }

    @Override
    public Long getId() {
        return id;
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
    public DepartmentDTO getDepartment() {
        return department;
    }

    @Override
    public void setDepartment(DepartmentDTO department) {
        this.department = (Department)department;
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
