package data;

import business.Customer;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import business.Staff;
import business.Customer;
import business.Owner;

public class PersonDB {
    static String tableName;

    public static boolean emailExists(String email) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            // Kiểm tra trong bảng Customer
            String qCustomer = "SELECT COUNT(c) FROM Customer c WHERE c.email = :email";
            Long customerCount = (Long) em.createQuery(qCustomer).setParameter("email", email).getSingleResult();
            if(customerCount > 0) {
                tableName = "Customer";
                return true;
            }

            String qOwner = "SELECT COUNT(o) FROM Owner o WHERE o.email = :email";
            Long ownerCount = (Long) em.createQuery(qOwner).setParameter("email", email).getSingleResult();
            if(ownerCount > 0) {
                tableName = "Owner";
                return true;
            }

            String qStaff = "SELECT COUNT(s) FROM Staff s WHERE s.email = :email";
            Long staffCount = (Long) em.createQuery(qStaff).setParameter("email", email).getSingleResult();
            if(staffCount > 0) {
                tableName = "Staff";
                return true;
            }
            return false;

        } finally {
            em.close();
        }
    }

    public static boolean updatePassword(String email, String newPassword) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            em.getTransaction().begin();

            // Câu lệnh JPQL UPDATE
            String query = "UPDATE " + tableName + " t SET t.password = :password WHERE t.email = :email";
            int rowsUpdated = em.createQuery(query)
                    .setParameter("password", newPassword)
                    .setParameter("email", email)
                    .executeUpdate();

            em.getTransaction().commit();
            return rowsUpdated > 0; // Trả về true nếu có dòng được cập nhật
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }




}
