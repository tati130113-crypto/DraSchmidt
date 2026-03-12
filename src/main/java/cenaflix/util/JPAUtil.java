package cenaflix.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
    
    private static final EntityManagerFactory FACTORY = 
        Persistence.createEntityManagerFactory("cenaflix-pu");
    
    public static EntityManager getEntityManager() {
        return FACTORY.createEntityManager();
    }
    
    public static void close() {
        if (FACTORY != null && FACTORY.isOpen()) {
            FACTORY.close();
        }
    }
}