package org.shtrudell.client.model;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import org.shtrudell.common.model.EmployeeDTO;
import org.shtrudell.common.model.Gender;
import org.shtrudell.server.model.Department;
import java.util.Date;

public class EmployeeRow {
    private final SimpleLongProperty id = new SimpleLongProperty();
    private final SimpleStringProperty firstName = new SimpleStringProperty();
    private final SimpleStringProperty lastName = new SimpleStringProperty();
    private final SimpleStringProperty middleName = new SimpleStringProperty();
    private final SimpleStringProperty gender = new SimpleStringProperty();
    private final SimpleStringProperty date = new SimpleStringProperty();
    private final SimpleStringProperty address = new SimpleStringProperty();
    private final SimpleLongProperty department = new SimpleLongProperty();

    private final EmployeeDTO employee;

    public EmployeeRow(EmployeeDTO employee) {
        this.employee = employee;

        bindDTO(employee);
    }

    private void bindDTO(EmployeeDTO employee) {
        if(employee == null) return;

        setId(employee.getId());
        setFirstName(employee.getFirstName());
        setLastName(employee.getLastName());
        setMiddleName(employee.getMiddleName());
        setGender(employee.getGender());
        setDate(employee.getDate());
        setAddress(employee.getAddress());

        firstName.unbind();

        firstName.addListener((observable, oldValue, newValue) -> {getEmployee().setFirstName(newValue);});
        lastName.addListener((observable, oldValue, newValue) -> {getEmployee().setLastName(newValue);});
        middleName.addListener((observable, oldValue, newValue) -> {getEmployee().setMiddleName(newValue);});
        gender.addListener((observable, oldValue, newValue) -> {
            try {
                getEmployee().setGender(Gender.getByCode(newValue));
            } catch (IllegalArgumentException e) {
                gender.set(oldValue);
            }
        });
        date.addListener((observable, oldValue, newValue) -> {
            try {
                getEmployee().setDate(java.sql.Date.valueOf(newValue));
            } catch (IllegalArgumentException ignored) {
                date.set(oldValue);
            }
        });
        address.addListener((observable, oldValue, newValue) -> {getEmployee().setAddress(newValue);});
    }

    public EmployeeDTO getEmployee() {
        return employee;
    }

    private void setId(Long id) {
        this.id.set(id);
    }

    public Long getId() {
        return id.get();
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getMiddleName() {
        return middleName.get();
    }

    public void setMiddleName(String middleName) {
        this.middleName.set(middleName);
    }

    public Gender getGender() {
        return Gender.getByCode(this.gender.get());
    }

    public void setGender(Gender gender) {
        this.gender.set(gender.getCode());
    }

    public Date getDate() {
        Date date = null;
        try {
            date = new Date(Date.parse(this.date.get()));
        } catch (Exception ignore) {}
        return date;
    }

    public void setDate(Date date) {
        this.date.set(date.toString());
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getAddress() {
        return address.get();
    }

    public Department getDepartment() {
        return null;
    }

    public void setDepartment(Department department) {

    }

    public SimpleStringProperty genderProperty() {
        return gender;
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public SimpleLongProperty departmentProperty() {
        return department;
    }

    public SimpleLongProperty idProperty() {
        return id;
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public SimpleStringProperty middleNameProperty() {
        return middleName;
    }
}
