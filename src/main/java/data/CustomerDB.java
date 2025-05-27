package data;

import business.Customer;

import javax.persistence.*;

public class CustomerDB {
    public static Customer accountExists(String email, String pass){
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        Customer customer = null;
        String query = "SELECT c FROM Customer c " + "WHERE c.email = :email AND c.password = :pass ";
        TypedQuery<Customer> q = em.createQuery(query, Customer.class);
        q.setParameter("email", email);
        q.setParameter("pass", pass);
        try{
            customer = q.getSingleResult();
            return customer;
        }
        catch(NoResultException e){
            return null;
        }
        finally{
            em.close();
        }
    }
    public static boolean emailExists(String email){
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT c.email FROM Customer c WHERE c.email = :email";
        Query query = em.createQuery(qString);
        query.setParameter("email", email);
        try{
            String emailResult = (String) query.getSingleResult();
            return !emailResult.isEmpty();
        }
        catch(NoResultException e){
            return false;
        }
        finally{
            em.close();
        }
    }

    public static Customer getCustomer(Long customerID) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT c FROM Customer c where c.personID = :customerID";
        TypedQuery<Customer> q = em.createQuery(qString, Customer.class);
        q.setParameter("customerID", customerID);
        try {
            Customer customer = q.getSingleResult();
            return customer;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public static Customer getCustomerByEmailPass(String email, String password) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT c FROM Customer c where c.email = :email and c.password = :password";
        TypedQuery<Customer> q = em.createQuery(qString, Customer.class);
        q.setParameter("email", email);
        q.setParameter("password", password);
        try {
            Customer customer = q.getSingleResult();
            return customer;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public static Customer getCustomerByGoogleLogin(String googleLogin) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT c FROM Customer c where c.googleLogin = :googleLogin";
        TypedQuery<Customer> q = em.createQuery(qString, Customer.class);
        q.setParameter("googleLogin", googleLogin);
        try {
            Customer customer = q.getSingleResult();
            return customer;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public static int insert(Customer customer) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin(); // Bắt đầu giao dịch
            em.persist(customer); // Lưu đối tượng Customer vào database
            trans.commit(); // Commit giao dịch
            return 1; // Trả về 1 nếu thành công
        } catch (Exception e) {
            if (trans != null && trans.isActive()) { // Kiểm tra nếu giao dịch đang hoạt động
                trans.rollback(); // Rollback giao dịch nếu có lỗi
            }
            e.printStackTrace(); // In lỗi ra console để kiểm tra
            return 0; // Trả về 0 nếu gặp lỗi
        } finally {
            if (em != null && em.isOpen()) { // Đảm bảo EntityManager được đóng
                em.close();
            }
        }
    }

    public static void addCustomer(Customer customer) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(customer);
            System.out.println("them thanh cong");
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