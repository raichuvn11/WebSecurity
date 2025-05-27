package DAO;

import ENumeration.EOrderStatus;
import business.Order;
import data.DBUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class OrderDAO {

    public List<Order> getAllOrders() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String query = "SELECT o FROM Order o";
        return em.createQuery(query, Order.class).getResultList();
    }

    public void updateOrderStatus(Long orderId, String newStatus) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            em.getTransaction().begin();

            Order order = em.find(Order.class, orderId);
            if (order != null) {
                order.setStatus(EOrderStatus.valueOf(newStatus));
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public List<Order> filterOrdersByStatus(String status) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            // JPQL lọc theo trạng thái
            String jpql = "SELECT o FROM Order o WHERE o.status = :status";
            TypedQuery<Order> query = em.createQuery(jpql, Order.class);
            query.setParameter("status", EOrderStatus.valueOf(status));
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}

