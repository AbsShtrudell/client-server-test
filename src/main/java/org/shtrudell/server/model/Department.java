package org.shtrudell.server.model;

import jakarta.persistence.*;
import org.shtrudell.common.model.DepartmentDTO;

@Entity
@Table(name="department")
public class Department  implements DepartmentDTO {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "type")
    private String type;

    @Version
    @Column(name = "OPTLOCK")
    private int versionNum;

    public Department() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
