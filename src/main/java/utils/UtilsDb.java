package utils;

import jakarta.persistence.*;


public class UtilsDb {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("employeePU");

    public static EntityManagerFactory getEmFactory() {
        return emf;
    }
}
