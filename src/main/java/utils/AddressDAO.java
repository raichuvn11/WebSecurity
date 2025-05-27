package utils;

import business.Address;
<<<<<<< HEAD

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
=======
import jakarta.persistence.*;

>>>>>>> master

public class AddressDAO {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("employeePU");

    public void insertAddress(Address address) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(address);  // Chèn dữ liệu vào bảng
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}