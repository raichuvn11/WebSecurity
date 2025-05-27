package data;

<<<<<<< HEAD
<<<<<<< HEAD
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
=======
import jakarta.persistence.*;

>>>>>>> master
=======
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
>>>>>>> master

public class DBUtil {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("employeePU");
    public static EntityManagerFactory getEmFactory() {
        return emf;
    }
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
