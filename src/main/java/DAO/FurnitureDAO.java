package DAO;

import data.DBUtil;
import business.Furniture;
import business.Image;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import java.util.*;

public class FurnitureDAO {
    public void addFurniture(List<Furniture> furnitures) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            int batchSize = 50; // Kích thước mỗi batch
            for (int i = 0; i < furnitures.size(); i++) {
                Furniture furniture = furnitures.get(i);

                // Đảm bảo tất cả ảnh trong danh sách `furnitureImages` được liên kết đúng
                List<Image> images = furniture.getFurnitureImages();
                if (images != null && !images.isEmpty()) {
                    for (Image image : images) {
                        image.setFurniture(furniture); // Liên kết Image với Furniture
                    }
                }

                em.persist(furniture); // Lưu đối tượng Furniture cùng với các Image liên quan

                // Flush và clear sau mỗi batch để giảm tải bộ nhớ
                if ((i + 1) % batchSize == 0) {
                    em.flush();  // Đẩy thay đổi vào DB
                    em.clear();  // Giải phóng bộ nhớ của EntityManager
                }
            }
            trans.commit();
        } catch (Exception e) {
            System.err.println("Lỗi khi thêm danh sách sản phẩm: " + e.getMessage());
            e.printStackTrace();
            if (trans.isActive()) {
                trans.rollback();
            }
        } finally {
            em.close();
        }
    }

    public int updateFurnitureByCategory(Furniture furniture) {
        // Kiểm tra đầu vào của đối tượng furniture
        if (furniture == null || furniture.getCategory() == null) {
            throw new IllegalArgumentException("Invalid furniture object or category.");
        }

        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        int updatedCount = 0; // Biến lưu trữ số lượng bản ghi cập nhật

        try {
            // Bắt đầu giao dịch
            trans.begin();

            // Thực hiện truy vấn cập nhật
            updatedCount = em.createQuery(
                            "UPDATE Furniture f SET f.furniturePrice = :price, " +
                                    "f.furnitureColor = :color, " +
                                    "f.furnitureDescription = :description, " +
                                    "f.furnitureStatus = :status " +
                                    "WHERE f.category.id = :categoryId and f.order is null")
                    .setParameter("price", furniture.getFurniturePrice())
                    .setParameter("color", furniture.getFurnitureColor())
                    .setParameter("description", furniture.getFurnitureDescription())
                    .setParameter("status", furniture.getFurnitureStatus())
                    .setParameter("categoryId", furniture.getCategory().getId())
                    .executeUpdate();

            // Commit giao dịch sau khi truy vấn thành công
            trans.commit();
        } catch (Exception e) {
            // Rollback nếu có lỗi
            if (trans.isActive()) {
                trans.rollback();
            }
            throw e; // Ném lại lỗi để có thể xử lý bên ngoài
        } finally {
            // Đảm bảo đóng EntityManager để giải phóng tài nguyên
            em.close();
        }
        // Trả về số lượng bản ghi đã cập nhật
        return updatedCount;
    }
    public void deleteImagesByCategory(Long categoryId) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.createQuery(
                            "DELETE FROM Image img WHERE img.furniture.id IN " +
                                    "(SELECT f.id FROM Furniture f WHERE f.category.id = :categoryId and f.order is null)")
                    .setParameter("categoryId", categoryId)
                    .executeUpdate();
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    public void saveImagesInBatch(List<Image> images) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            int batchSize = 20;
            for (int i = 0; i < images.size(); i++) {
                em.persist(images.get(i));
                if (i > 0 && i % batchSize == 0) {
                    em.flush();
                    em.clear();
                }
            }
            em.flush();
            em.clear();
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Furniture> getFurnitureByCategoryID(Long categoryID) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT f FROM Furniture f " +
                "WHERE f.category.id = :id";
        TypedQuery<Furniture> q = em.createQuery(qString, Furniture.class);
        q.setParameter("id", categoryID);
        List<Furniture> furnitureList = null;
        try {
            furnitureList = q.getResultList();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
        return furnitureList;
    }
    public Furniture getFurnitureByID(Long id) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT f FROM Furniture f " +
                "WHERE f.id = :ID";
        TypedQuery<Furniture> q = em.createQuery(qString, Furniture.class);
        q.setParameter("ID", id);
        Furniture furniture = null;
        try {
            furniture = q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
        return furniture;
    }
    public  Map<Furniture, Long>  getFurnitureList() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        Map<Furniture, Long> furnitureCountMap = new HashMap<>();
        try {
            // Lấy danh sách các Furniture từ cơ sở dữ liệu
            List<Furniture> furnitures = em.createQuery("SELECT f FROM Furniture f WHERE f.id IN (" +
                    "SELECT MIN(f1.id) FROM Furniture f1 where f1.order is null Group BY f1.category.id )", Furniture.class).getResultList();

            for (Furniture furniture : furnitures) {
                // Tính số lượng các đối tượng Furniture có cùng furnitureID
                Long count = (Long) em.createQuery(
                                "SELECT COUNT(f) FROM Furniture f WHERE f.category.id = :id AND f.order IS NULL")
                        .setParameter("id", furniture.getCategory().getId())
                        .getSingleResult();

                furnitureCountMap.put(furniture, count); // Thêm vào map
            }
        } catch (Exception e) {
            e.printStackTrace(); // hoặc ghi log
        } finally {
            em.close(); // Đảm bảo đóng EntityManager
        }
        return furnitureCountMap;
    }
    public List<Image> getImagesByFurnitureId(Long id) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        List<Image> images = null;
        try {
            // Truy vấn để lấy các ảnh dựa trên furnitureId
            TypedQuery<Image> query = em.createQuery(
                    "SELECT i FROM Image i WHERE i.furniture.id = :furnitureId", Image.class);
            query.setParameter("furnitureId", id);

            images = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return images;
    }
    public List<Furniture> getFurnitureByFilters(Long categoryId, String priceRange) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        List<Furniture> furnitureList = null;

        try {
            // Bắt đầu truy vấn
            StringBuilder query = new StringBuilder("SELECT f FROM Furniture f WHERE f.id IN (");
            query.append("SELECT MIN(f1.id) FROM Furniture f1 WHERE f1.category.id = f.category.id");

            // Lọc theo categoryId nếu có
            if (categoryId != null) {
                query.append(" AND f1.category.id = :categoryId");
            }

            // Lọc theo priceRange nếu có
            if (priceRange != null && !priceRange.isEmpty()) {
                switch (priceRange) {
                    case "<5000000":
                        query.append(" AND f1.furniturePrice < 5000000");
                        break;
                    case "5000000 - 10000000":
                        query.append(" AND f1.furniturePrice BETWEEN 5000000 AND 10000000");
                        break;
                    case "10000000 - 20000000":
                        query.append(" AND f1.furniturePrice BETWEEN 10000000 AND 20000000");
                        break;
                    case ">20000000":
                        query.append(" AND f1.furniturePrice > 20000000");
                        break;
                }
            }

            // Đóng ngoặc cho truy vấn con
            query.append(")");

            // Tạo và thực thi truy vấn
            TypedQuery<Furniture> q = em.createQuery(query.toString(), Furniture.class);

            // Set tham số cho categoryId nếu có
            if (categoryId != null) {
                q.setParameter("categoryId", categoryId);
            }

            furnitureList = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace(); // hoặc ghi log
        } finally {
            em.close();
        }

        return furnitureList;
    }
    public Furniture getFurnitureIfExists(String categoryName, String furnitureColor, Long furniturePrice) {
        // Tạo truy vấn để tìm sản phẩm với tên, màu sắc, và giá tương ứng
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String query = "SELECT f FROM Furniture f WHERE f.category.categoryName  = :categoryName AND f.furnitureColor = :furnitureColor AND f.furniturePrice = :furniturePrice";
        TypedQuery<Furniture> typedQuery = em.createQuery(query, Furniture.class);
        typedQuery.setParameter("categoryName", categoryName);
        typedQuery.setParameter("furnitureColor", furnitureColor);
        typedQuery.setParameter("furniturePrice", furniturePrice);

        List<Furniture> result = typedQuery.getResultList();

        if (result.isEmpty()) {
            return null; // Không tìm thấy sản phẩm
        } else {
            return result.get(0); // Trả về sản phẩm đã tồn tại
        }
    }
    public Long countFurnitureByCategoryId(Long categoryId) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            String jpql = "SELECT COUNT(f) FROM Furniture f WHERE f.category.id = :id";
            return em.createQuery(jpql, Long.class)
                    .setParameter("id", categoryId)
                    .getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        } finally {
            em.close();
        }
    }
    public List<Furniture> getFurnitureByIDs(List<Long> ids) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String query = "SELECT f FROM Furniture f WHERE f.id IN :ids";
        return em.createQuery(query, Furniture.class)
                .setParameter("ids", ids)
                .getResultList();
    }

    public void updateFurnitureList(List<Furniture> furnitureList) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        // Bắt đầu transaction
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin(); // Bắt đầu transaction

            for (Furniture furniture : furnitureList) {
                em.merge(furniture); // Sử dụng merge để cập nhật
            }

            transaction.commit(); // Commit transaction
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback(); // Rollback nếu có lỗi
            }
            throw e; // Ném lại lỗi sau khi rollback
        } finally {
            em.close(); // Đóng EntityManager
        }
    }

}
