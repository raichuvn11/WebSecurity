package DAO;

import business.Customer;
import business.Staff;
import data.DBUtil;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class UserInfoDAO {

    public Customer getCustomerInfoById(Long id) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String query = "SELECT c FROM Customer c WHERE c.personID = :id"; // Truy vấn JPQL
        try {
            TypedQuery<Customer> q = em.createQuery(query, Customer.class);
            q.setParameter("id", id); // Gắn tham số vào truy vấn
            return q.getSingleResult(); // Trả về khách hàng
        } catch (NoResultException e) {
            System.out.println("Customer not found with ID: " + id);
            return null; // Không tìm thấy khách hàng
        } finally {
            em.close();
        }
    }

    public Staff getStaffInfoById(Long id) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String query = "SELECT s FROM Staff s WHERE s.personID = :id"; // Truy vấn JPQL
        try {
            TypedQuery<Staff> q = em.createQuery(query, Staff.class);
            q.setParameter("id", id); // Gắn tham số vào truy vấn
            return q.getSingleResult(); // Trả về nhân viên
        } catch (NoResultException e) {
            System.out.println("Staff not found with ID: " + id);
            return null; // Không tìm thấy nhân viên
        } finally {
            em.close();
        }
    }
}
