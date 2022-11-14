package org.shtrudell.common.net;

import org.shtrudell.common.model.DepartmentDTO;
import org.shtrudell.common.model.EmployeeDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Message implements Serializable {
    private List<EmployeeDTO> employees;
    private List<DepartmentDTO> departments;
    private MessageType type;

    public Message() {
        employees = new ArrayList<>();
        departments = new ArrayList<>();
    }

    public Message(MessageType type) {
        employees = new ArrayList<>();
        departments = new ArrayList<>();
        this.type = type;
    }

    public Message(MessageType type, List<EmployeeDTO> employees, List<DepartmentDTO> departments) {
        this.employees = employees;
        this.departments = departments;
        this.type = type;
    }

    public Message(List<DepartmentDTO> departments, MessageType type) {
        this.employees = new ArrayList<>();
        this.departments = departments;
        this.type = type;
    }

    public Message(MessageType type, List<EmployeeDTO> employees) {
        this.employees = employees;
        this.departments = new ArrayList<>();
        this.type = type;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public List<EmployeeDTO> getEmployees() {
        return employees;
    }

    public List<DepartmentDTO> getDepartments() {
        return departments;
    }

    public void setEmployees(List<EmployeeDTO> employees) {
        this.employees = employees;
    }

    public void setDepartments(List<DepartmentDTO> departments) {
        this.departments = departments;
    }

    @Override
    public String toString() {
        return "Message{" +
                "employees=" + employees +
                ", departments=" + departments +
                ", type=" + type +
                '}';
    }
}
