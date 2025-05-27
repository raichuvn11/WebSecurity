package data;

import business.Coupon;
import business.Furniture;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CouponDB {
    public static Coupon getCouponByID(String couponID) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            return em.find(Coupon.class, couponID); // Sử dụng phương thức find để tìm coupon theo couponID
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Trả về null nếu không tìm thấy hoặc có lỗi
        } finally {
            em.close(); // Đảm bảo đóng EntityManager sau khi sử dụng
        }
    }

    public static List<Coupon> getListCoupon(List<Furniture> listFur) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            String queryStr = "SELECT c FROM Coupon c WHERE c.startDate <= :currentDate AND c.endDate >= :currentDate" +
                    " AND c.currentUsage < c.useLimit";
            TypedQuery<Coupon> query = em.createQuery(queryStr, Coupon.class);
            query.setParameter("currentDate", new Date());
            List<Coupon> couponList = query.getResultList();

            // Lọc các coupon có thể áp dụng cho sản phẩm trong danh sách listFur
            List<Coupon> applicableCoupons = new ArrayList<>();

            for (Coupon coupon : couponList) {
                if(coupon.getUseCondition() == "product")
                {
                    for (Furniture furniture : listFur) {
                        // Kiểm tra nếu danh mục của sản phẩm nằm trong danh sách danh mục áp dụng coupon
                        if (coupon.getNameApplicableFurniture().contains(furniture.getCategory().getCategoryName())) {
                            applicableCoupons.add(coupon);
                            break; // Nếu tìm thấy ít nhất một sản phẩm áp dụng coupon, thêm coupon vào danh sách và thoát vòng lặp
                        }
                    }
                }
                else
                {
                    applicableCoupons.add(coupon);
                }
            }
            return applicableCoupons;
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Trả về null nếu có lỗi
        } finally {
            em.close(); // Đảm bảo đóng EntityManager sau khi sử dụng
        }
    }

}
