package org.shtrudell.server.net;

import org.shtrudell.server.controller.Controller;
import org.shtrudell.server.util.ConfigHandler;
import org.shtrudell.server.util.Configs;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class Server implements Closeable {
    private static final int LINGER_TIME = 5000;
    private static final int TIMEOUT_HALF_HOUR = 1800000;
    private final List<ClientHandler> clientHandlers = new ArrayList<>();
    private final Controller controller;
    private int port = 8080;
    private boolean closed = false;

    public Server() {
        controller = new Controller();
        configure();
    }

    public void start() {
        try {
            ServerSocket listeningSocket = new ServerSocket(port);
            while (!this.closed) {
                Socket clientSocket = listeningSocket.accept();
                System.out.println("Client connected");
                startClientHandler(clientSocket);
            }
            listeningSocket.close();
        } catch (IOException e) {
            System.err.println("Server failure!");
        } finally {
            for(var handler : clientHandlers) {
                handler.close();
                removeClientHandler(handler);
            }
        }
    }

    @Override
    public void close() {
        this.closed = true;
    }

    private void startClientHandler(Socket clientSocket) throws SocketException {
        clientSocket.setSoLinger(true, LINGER_TIME);
        clientSocket.setSoTimeout(TIMEOUT_HALF_HOUR);
        ClientHandler handler = new ClientHandler(clientSocket, new ClientHandlerListener(), controller);
        synchronized (clientHandlers) {
            clientHandlers.add(handler);
        }
        Thread handlerThread = new Thread(handler);
        handlerThread.setPriority(Thread.MAX_PRIORITY);
        handlerThread.start();
    }

    private void removeClientHandler(ClientHandler handler) {
        synchronized (clientHandlers) {
            clientHandlers.remove(handler);
        }
    }

    private void configure() {
        ConfigHandler confHandler = new ConfigHandler();
        Configs conf = confHandler.load();

        if(conf == null) return;

        port = conf.getPort();
    }

    class ClientHandlerListener {
        public void onDisconnected(ClientHandler handler) {
            removeClientHandler(handler);
        }
    }
}
