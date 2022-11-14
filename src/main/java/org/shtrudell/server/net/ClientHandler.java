package org.shtrudell.server.net;

import org.shtrudell.common.net.Message;
import org.shtrudell.common.net.MessageType;
import org.shtrudell.server.controller.Controller;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable, Closeable {

    private final Server.ClientHandlerListener listener;
    private final Socket clientSocket;
    private final Controller controller;
    private ObjectInputStream fromClient;
    private ObjectOutputStream toClient;
    private boolean connected = true;
    private boolean closed = false;

    public ClientHandler(Socket clientSocket, Server.ClientHandlerListener listener, Controller controller) {
        this.clientSocket = clientSocket;
        this.listener = listener;
        this.controller = controller;
    }

    @Override
    public void run() {
        try {
            fromClient = new ObjectInputStream(clientSocket.getInputStream());
            toClient = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
        while (connected && !closed) {
            try {
                Message msg = receiveMsg();
                System.out.println("Received message " + msg.getType());
                Message answer;
                switch (msg.getType()) {
                    case ADD_EMPLOYEE :
                        for(var emp : msg.getEmployees()) {
                            controller.createEmployee(emp);
                        }
                        break;
                    case DELETE_EMPLOYEE :
                        for(var emp : msg.getEmployees()) {
                            System.out.println(emp.getId());
                            controller.deleteEmployee(emp);
                        }
                        break;
                    case UPDATE_EMPLOYEE :
                        for(var emp : msg.getEmployees()) {
                            controller.updateEmployee(emp);
                        }
                        break;
                    case FIND_ALL_EMPLOYEES:
                        answer = new Message(MessageType.FIND_ALL_EMPLOYEES, controller.findAllEmployees());
                        sendMsg(answer);
                        break;
                    case ADD_DEPARTMENT:
                        for(var dep : msg.getDepartments()) {
                            controller.createDepartment(dep);
                        }
                        break;
                    case DELETE_DEPARTMENT:
                        for(var dep : msg.getDepartments()) {
                            controller.deleteDepartment(dep);
                        }
                        break;
                    case UPDATE_DEPARTMENT:
                        for(var dep : msg.getDepartments()) {
                            controller.updateDepartment(dep);
                        }
                        break;
                    case FIND_ALL_DEPARTMENTS:
                        answer = new Message(controller.findAllDepartments(), MessageType.FIND_ALL_DEPARTMENTS);
                        sendMsg(answer);
                        break;
                    case FIND_ALL:
                        answer = new Message(MessageType.FIND_ALL, controller.findAllEmployees(), controller.findAllDepartments());
                        sendMsg(answer);
                        break;
                    case DISCONNECT:
                        disconnectClient();
                        break;
                    default:
                        throw new Exception("Received corrupt message: " + msg);
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Client Disconnected");
                disconnectClient();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Client handler closed");
    }

    @Override
    public void close() {
        closed = true;
    }

    private void sendMsg(Message msg) throws UncheckedIOException {
        try {
            toClient.writeObject(msg);
            toClient.flush();
            toClient.reset();
        } catch (IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
    }

    private Message receiveMsg() throws IOException, ClassNotFoundException {
        return (Message) fromClient.readObject();
    }

    private void disconnectClient() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        connected = false;
        listener.onDisconnected(this);
    }
}
