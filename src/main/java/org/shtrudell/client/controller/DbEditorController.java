package org.shtrudell.client.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.util.Callback;
import org.shtrudell.client.model.EmployeeRow;
import org.shtrudell.client.net.Employee;
import org.shtrudell.common.integration.PersonnelDAO;
import org.shtrudell.common.model.EmployeeDTO;
import org.shtrudell.common.model.Gender;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DbEditorController {

    @FXML
    private TextField firstNameIF;
    @FXML
    private TextField lastNameIF;
    @FXML
    private TextField middleNameIF;
    @FXML
    private DatePicker dateIF;
    @FXML
    private ChoiceBox<Gender> genderIF;
    @FXML
    private TextField addressIF;
    @FXML
    private TableColumn<EmployeeRow, Gender> empGenderCol;
    @FXML
    private TableView<EmployeeRow> employeeTable;

    private PersonnelDAO personnelDAO;

    public DbEditorController() {
    }

    public void init(){
        System.out.println("init");
        personnelDAO.createEmployee(new Employee("Ilya", "Naumenko", "Vadimovich", Gender.MALE, new Date(), "gsfsd"));
        List<EmployeeDTO> employees = personnelDAO.findAllEmployees();
        System.out.println(employees);
        for(var emp : employees) {
             System.out.println(emp.toString());
        }
        initEmployeeTable(employees);
        genderIF.getItems().setAll(Gender.values());
    }

    private void initEmployeeTable(List<EmployeeDTO> employees) {

        ObservableList<Gender> genderList = FXCollections.observableArrayList(Gender.values());
        empGenderCol.setCellFactory(ComboBoxTableCell.forTableColumn(genderList));

        empGenderCol.setOnEditCommit((TableColumn.CellEditEvent<EmployeeRow, Gender> event) -> {
            TablePosition<EmployeeRow, Gender> pos = event.getTablePosition();

            Gender newGender = event.getNewValue();

            int row = pos.getRow();
            EmployeeRow person = event.getTableView().getItems().get(row);

            person.setGender(newGender);
        });

        empGenderCol.setCellValueFactory(param -> {
            EmployeeRow employeeRow = param.getValue();
            // F,M
            String genderCode = employeeRow.genderProperty().get();
            Gender gender = Gender.getByCode(genderCode);
            return new SimpleObjectProperty<>(gender);
        });

        ObservableList<EmployeeRow> list = FXCollections.observableArrayList();
        for(var emp : employees) {
            list.add(new EmployeeRow(emp));
        }
        employeeTable.setItems(list);
    }

    @FXML
    private void onAddEmployee(){
        if(firstNameIF.getText() == null || lastNameIF.getText() == null || middleNameIF.getText() == null ||
            genderIF.getValue() == null || dateIF.getValue() == null || addressIF.getText() == null) return;

        personnelDAO.createEmployee(new Employee(firstNameIF.getText(), lastNameIF.getText(),
                                                middleNameIF.getText(), genderIF.getValue(),
                                                java.sql.Date.valueOf(dateIF.getValue()),
                                                addressIF.getText()));

        updateEmployeeTable();
    }

    private void updateEmployeeTable() {
        List<EmployeeDTO> employees = personnelDAO.findAllEmployees();

        if(employees == null) return;

        ObservableList<EmployeeRow> list = FXCollections.observableArrayList();
        for(var emp : employees) {
            list.add(new EmployeeRow(emp));
        }
        employeeTable.getItems().clear();
        employeeTable.setItems(list);
    }

    public void setPersonnelDAO(PersonnelDAO personnelDAO) {
        this.personnelDAO = personnelDAO;
    }

    public void onDeleteEmployee(ActionEvent actionEvent) {
        ObservableList<EmployeeRow> employeeSelected;
        employeeSelected = employeeTable.getSelectionModel().getSelectedItems();
        List<EmployeeDTO> employees = new ArrayList<>();
        for(var emp : employeeSelected) {
            employees.add(emp.getEmployee());
        }
        personnelDAO.deleteEmployees(employees);
        updateEmployeeTable();
    }
}
