package org.shtrudell.common.model;

import org.shtrudell.server.model.Department;
import org.shtrudell.server.model.Gender;

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
    public Department getDepartment();
}
