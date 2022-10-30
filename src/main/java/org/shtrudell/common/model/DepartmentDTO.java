package org.shtrudell.common.model;

import java.io.Serializable;

public interface DepartmentDTO extends Serializable {
    public Long getId();
    public String getName();
    public String getType();
}
