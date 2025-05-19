package data;

import ENumeration.EOrderStatus;
import business.Customer;
import business.Feedback;
import business.Furniture;
import business.Order;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;

public class OrderDB {
    public static boolean insertOrder(Order order) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            // Lưu trữ đơn hàng vào cơ sở dữ liệu
            em.persist(order);
            // Cập nhật order_id cho các đối tượng Furniture hiện có
            for (Furniture furniture : order.getListFurniture()) {
                // Nếu furniture đã tồn tại trong DB, chỉ cập nhật order_id mà không tạo mới
                if (furniture.getId() != null) {
                    furniture.setOrder(order); // Cập nhật lại order_id của furniture
                    em.merge(furniture); // Merge để cập nhật furniture vào DB
                }
            }
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

    public static ArrayList<Order> loadOrders(Customer customer) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT o FROM Order o WHERE o.customer = :customer";
        TypedQuery<Order> q = em.createQuery(qString, Order.class);
        q.setParameter("customer", customer);

        ArrayList<Order> orders;
        try {
            orders = new ArrayList<>(q.getResultList());
        } finally {
            em.close();
        }
        return orders;
    }

    public static ArrayList<Order> filterOrders(Customer customer, EOrderStatus orderStatus) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT o FROM Order o WHERE o.customer = :customer AND o.status = :orderStatus";
        TypedQuery<Order> q = em.createQuery(qString, Order.class);
        q.setParameter("customer", customer);
        q.setParameter("orderStatus", orderStatus);

        ArrayList<Order> orders;
        try {
            orders = new ArrayList<>(q.getResultList());
        } finally {
            em.close();
        }
        return orders;
    }

    public static Order getOrder(Long id){
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        Order order = null;
        String query = "SELECT o FROM Order o " + "WHERE o.id = :id";
        TypedQuery<Order> q = em.createQuery(query, Order.class);
        q.setParameter("id", id);
        try{
            order = q.getSingleResult();
            return order;
        }
        catch(NoResultException e){
            return null;
        }
        finally{
            em.close();
        }
    }

    public static boolean updateOrderStatus(Long orderId, EOrderStatus orderStatus) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "UPDATE Order o SET o.status = :status WHERE o.id = :orderId";

        // Bắt đầu giao dịch
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            // Tạo truy vấn và thiết lập tham số
            TypedQuery<Order> q = em.createQuery(qString, Order.class);
            q.setParameter("status", orderStatus);
            q.setParameter("orderId", orderId);

            // Cập nhật dữ liệu
            int rowsUpdated = q.executeUpdate();

            // Commit giao dịch nếu có thay đổi
            transaction.commit();

            // Nếu ít nhất một bản ghi bị ảnh hưởng, trả về true
            return rowsUpdated > 0;
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback(); // Rollback nếu có lỗi
            }
            return false;
        } finally {
            em.close(); // Đảm bảo đóng EntityManager
        }
    }
}
