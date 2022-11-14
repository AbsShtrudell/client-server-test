package org.shtrudell.common.model;

import org.shtrudell.server.model.Department;

import java.io.Serializable;
import java.util.Date;

public interface EmployeeDTO extends Serializable {
    public Long getId();
    public String getFirstName();
    public String getLastName();
    public String getMiddleName();
    public Gender getGender();
    public Date getDate();
    public String getAddress();
    public void setFirstName(String firstName);
    public void setLastName(String firstName);
    public void setMiddleName(String middleName);
    public void setGender(Gender gender);
    public void setDate(Date date);
    public void setAddress(String address);
    public void setDepartment(DepartmentDTO department);
    public DepartmentDTO getDepartment();
}
