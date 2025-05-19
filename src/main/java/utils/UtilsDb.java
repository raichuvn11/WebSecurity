package utils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class UtilsDb {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("employeePU");

    public static EntityManagerFactory getEmFactory() {
        return emf;
    }
}
