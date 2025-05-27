package utils;

<<<<<<< HEAD
<<<<<<< HEAD
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
=======
import jakarta.persistence.*;

>>>>>>> master
=======
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
>>>>>>> master

public class UtilsDb {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("employeePU");

    public static EntityManagerFactory getEmFactory() {
        return emf;
    }
}
