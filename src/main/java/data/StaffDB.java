package data;

import business.Staff;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class StaffDB {
    public static Staff getStaffByEmailPass(String email, String password) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT s FROM Staff s where s.email = :email and s.password = :password";
        TypedQuery<Staff> q = em.createQuery(qString, Staff.class);
        q.setParameter("email", email);
        q.setParameter("password", password);
        try {
            Staff staff = q.getSingleResult();
            return staff;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}
