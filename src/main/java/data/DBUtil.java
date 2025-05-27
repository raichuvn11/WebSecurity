package data;

import jakarta.persistence.*;


public class DBUtil {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("employeePU");
    public static EntityManagerFactory getEmFactory() {
        return emf;
    }
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
