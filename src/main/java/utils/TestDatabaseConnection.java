package utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class TestDatabaseConnection {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("employeePU");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            // Thực hiện một truy vấn để lấy danh sách khách hàng (ví dụ Customer là entity đã map)
            List<?> customers = em.createQuery("SELECT c FROM Customer c").getResultList();
            System.out.println("Number of customers: " + customers.size());

            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}
