package DAO;

import business.Coupon;
import data.DBUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import javax.persistence.*;

import business.Category;

public class CouponDAO {

    public void insert(Coupon coupon) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        try {
            em.persist(coupon);
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
            trans.rollback();
        } finally {
            em.close();
        }
    }

    // Update an existing coupon
    public void update(Coupon coupon) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        try {
            em.merge(coupon);
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
            trans.rollback();
        } finally {
            em.close();
        }
    }
    public void delete(String couponId) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            Coupon coupon = em.find(Coupon.class, couponId);
            if (coupon != null) {
                em.remove(coupon);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    // Get coupon by ID
    public Coupon getCouponById(String couponId) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            return em.find(Coupon.class, couponId);
        } finally {
            em.close();
        }
    }
    public List<Coupon> getAllCoupons() {
        EntityManager entityManager = DBUtil.getEntityManager();
        try {
            String jpql = "SELECT c FROM Coupon c";
            TypedQuery<Coupon> query = entityManager.createQuery(jpql, Coupon.class);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }
    public static List<Category> getCategoriesByCoupon(String couponId) {
        EntityManager entityManager = DBUtil.getEntityManager();
        try {
            String jpql = "SELECT c.applicableFurniture FROM Coupon c  WHERE c.couponID = :couponId";
            TypedQuery<Category> query = entityManager.createQuery(jpql, Category.class);
            query.setParameter("couponId", couponId);  // Truyền couponId vào tham số của truy vấn

            return query.getResultList();
        }
        finally {
            entityManager.close();
        }

    }

    public static boolean existedCoupon(String couponName) {
        EntityManager entityManager = DBUtil.getEntityManager();
        try {
            // JPQL truy vấn để kiểm tra xem có Coupon nào với couponName này không
            String jpql = "SELECT COUNT(c) FROM Coupon c WHERE c.couponName = :couponName";

            // Tạo TypedQuery để thực thi truy vấn
            TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
            query.setParameter("couponName", couponName);  // Đặt tham số couponName trong truy vấn

            // Trả về số lượng kết quả (nếu > 0 thì có tồn tại coupon)
            Long count = query.getSingleResult();
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            entityManager.close();
        }
    }
}

