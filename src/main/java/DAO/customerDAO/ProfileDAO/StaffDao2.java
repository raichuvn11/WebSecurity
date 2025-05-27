package DAO.customerDAO.ProfileDAO;

import data.DBUtil;

import business.Staff;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.EntityTransaction;

public class StaffDao2 {
    public Staff getStaffById(Long id) { // Chuyển đổi tham số thành Long
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT s FROM Staff s WHERE s.personID = :id";
        TypedQuery<Staff> q = em.createQuery(qString, Staff.class);
        q.setParameter("id", id);

        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    public void updateStaff(Staff staff) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            em.merge(staff); // Use merge to update the existing staff entity
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) {
                trans.rollback();
            }
            throw new RuntimeException("Failed to update staff profile: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
}
