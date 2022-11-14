package org.shtrudell.server;

import jakarta.persistence.*;
import org.shtrudell.server.net.Server;

public class Main {
    private static EntityManager em;

    public static void main(String[] args) {
        init();
        Server server = new Server();
        server.start();
        close();
    }

    static public void init() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory( "Lab6" );
        em = emf.createEntityManager();
    }

    static public void close() {
        em.getEntityManagerFactory().close();
        em.close();
    }
}