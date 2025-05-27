package data;

import ENumeration.EOrderStatus;
import business.Coupon;
import business.Order;
import business.Payment;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.Date;

public class PaymentDB {
    public static boolean insert(Payment payment) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();

            Coupon coupon = payment.getCoupon();
            // Tìm Coupon từ DB để đảm bảo nó được quản lý bởi JPA
            if(coupon != null) {
                Coupon managedCoupon = em.find(Coupon.class, coupon.getCouponID());
                if(managedCoupon.getCurrentUsage() >= managedCoupon.getUseLimit()) {
                    return false;
                }
                managedCoupon.setCurrentUsage(managedCoupon.getCurrentUsage() + 1);
            }

            // Lưu Payment
            em.persist(payment);

            trans.commit();
            return true;
        } catch (Exception e) {
            System.out.println("Loi: " + e);
            if (trans.isActive()) {
                trans.rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }

    public static void updatePaymentDate(Long orderID) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            em.getTransaction().begin();

            // Tìm Payment theo Order ID
            TypedQuery<Payment> query = em.createQuery(
                    "SELECT p FROM Payment p WHERE p.order.id = :orderID", Payment.class);
            query.setParameter("orderID", orderID);

            Payment payment = query.getResultStream().findFirst().orElse(null);

            if (payment != null) {
                if (payment.getPaymentDate() == null) {
                    // Cập nhật thời gian thanh toán thành thời gian hiện tại
                    payment.setPaymentDate(new Date());
                    em.merge(payment); // Ghi lại thay đổi
                }
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
        }
    }
}

