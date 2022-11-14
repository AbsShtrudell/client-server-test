package org.shtrudell.client.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.shtrudell.common.integration.PersonnelDAO;
import org.shtrudell.common.model.DepartmentDTO;
import org.shtrudell.common.model.EmployeeDTO;
import org.shtrudell.common.net.*;

public class Client {
    private static final int TIMEOUT_HALF_HOUR = 1800000;
    private static final int TIMEOUT_HALF_MINUTE = 30000;
    private Socket socket;
    private ObjectOutputStream toServer;
    private ObjectInputStream fromServer;
    private boolean connected;

    public void connect(String host, int port) throws IOException {
        socket = new Socket();
        socket.connect(new InetSocketAddress(host, port), TIMEOUT_HALF_MINUTE);
        socket.setSoTimeout(TIMEOUT_HALF_HOUR);
        connected = true;
        toServer = new ObjectOutputStream(socket.getOutputStream());
        fromServer = new ObjectInputStream(socket.getInputStream());
    }

    public void disconnect() {
        try {
            sendMsg(new Message(MessageType.DISCONNECT));
            socket.close();
            socket = null;
            connected = false;
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    public Message receiveMsg() {
        Message msg = null;
        while (msg == null) {
            try {
                msg = (Message) fromServer.readObject();
            } catch (IOException ioe) {
                if (connected) {
                    System.out.println("Lost connection.");
                    disconnect();
                }
            } catch (ClassNotFoundException ignored) {
            }
        }
        return msg;
    }

    public void sendMsg(Message msg) {
        try {
            toServer.writeObject(msg);
            toServer.flush();
            toServer.reset();
        } catch (IOException ioe) {
            System.out.println("Can't send message");
        }
    }

    public PersonnelDAO getPersonnelDAO() {
        return new MessageController();
    }

    private class MessageController implements PersonnelDAO {

        private Message createMessage(MessageType type, EmployeeDTO employee) {
            ArrayList<EmployeeDTO> employees = new ArrayList<>(Arrays.asList(employee));
            return new Message(type, employees);
        }

        private Message createMessage(MessageType type, DepartmentDTO department) {
            ArrayList<DepartmentDTO> departments = new ArrayList<>(Arrays.asList(department));
            return new Message(departments, type);
        }

        private Message createMessage(List<EmployeeDTO> employees, MessageType type ) {
            return new Message(type, employees);
        }

        private Message createMessage(MessageType type, List<DepartmentDTO> departments) {
            return new Message(departments, type);
        }

        @Override
        public void createEmployee(EmployeeDTO employee) {
            sendMsg(createMessage(MessageType.ADD_EMPLOYEE, employee));
        }

        @Override
        public void deleteEmployee(EmployeeDTO employee) {
            sendMsg(createMessage(MessageType.DELETE_EMPLOYEE, employee));
        }

        @Override
        public void updateEmployee(EmployeeDTO employee) {
            sendMsg(createMessage(MessageType.UPDATE_EMPLOYEE, employee));
        }

        @Override
        public void createEmployees(List<EmployeeDTO> employees) {
            sendMsg(createMessage(employees, MessageType.ADD_EMPLOYEE));
        }

        @Override
        public void deleteEmployees(List<EmployeeDTO> employees) {
            sendMsg(createMessage(employees, MessageType.DELETE_EMPLOYEE));
        }

        @Override
        public void updateEmployees(List<EmployeeDTO> employees) {
            sendMsg(createMessage(employees, MessageType.UPDATE_EMPLOYEE));
        }

        @Override
        public List<EmployeeDTO> findAllEmployees() {
            Message msg = new Message(MessageType.FIND_ALL_EMPLOYEES);
            sendMsg(msg);
            return receiveMsg().getEmployees();
        }

        @Override
        public void createDepartment(DepartmentDTO department) {
            sendMsg(createMessage(MessageType.ADD_DEPARTMENT, department));
        }

        @Override
        public void deleteDepartment(DepartmentDTO department) {
            sendMsg(createMessage(MessageType.DELETE_DEPARTMENT, department));
        }

        @Override
        public void updateDepartment(DepartmentDTO department) {
            sendMsg(createMessage(MessageType.UPDATE_DEPARTMENT, department));
        }

        @Override
        public void createDepartments(List<DepartmentDTO> departments) {
            sendMsg(createMessage(MessageType.ADD_DEPARTMENT, departments));
        }

        @Override
        public void deleteDepartments(List<DepartmentDTO> departments) {
            sendMsg(createMessage(MessageType.DELETE_DEPARTMENT, departments));
        }

        @Override
        public void updateDepartments(List<DepartmentDTO> departments) {
            sendMsg(createMessage(MessageType.UPDATE_DEPARTMENT, departments));
        }

        @Override
        public List<DepartmentDTO> findAllDepartments() {
            sendMsg(new Message(MessageType.FIND_ALL_DEPARTMENTS));
            return receiveMsg().getDepartments();
        }
    }
}
