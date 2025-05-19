package DAO.customerDAO.impl;



import DAO.customerDAO.IFurnitureDAO;
import DTO.customerDTO.requestDTO.FurnitureRequestDTO;
import ENumeration.EOrderStatus;
import business.Furniture;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class FurnitureDAOImpl implements IFurnitureDAO {
    private EntityManagerFactory emf;

    public FurnitureDAOImpl() {
        this.emf = Persistence.createEntityManagerFactory("employeePU");
    }
    @Override
    public List<Furniture> getFurnituresByOrderId(Long orderId) {
        EntityManager em = emf.createEntityManager();
        String jpql = "SELECT f FROM Furniture f WHERE f.order.id = :orderId";
        TypedQuery<Furniture> query = em.createQuery(jpql, Furniture.class);
        query.setParameter("orderId", orderId);
        return query.getResultList();
    }
    @Override
    public List<Furniture> getFurnituresByCustomerId(FurnitureRequestDTO furnitureRequestDTO) {
        EntityManager em = emf.createEntityManager();

        StringBuilder jpql = new StringBuilder("SELECT f FROM Furniture f " +
                "JOIN f.order o " +
                "JOIN f.category c " +
                "WHERE o.customer.personID = :customerId ");
        jpql.append("AND o.status IN (:status1, :status2, :status3) ");

        if (furnitureRequestDTO.getCategoryName() != null && !furnitureRequestDTO.getCategoryName().isEmpty()) {
            String[] searchTerms = furnitureRequestDTO.getCategoryName().split("\\s+");  // Tách chuỗi theo khoảng trắng
//AND (c.categoryName LIKE :categoryName0 OR c.categoryName LIKE :categoryName1 OR c.categoryName LIKE :categoryName2)
            for (int i = 0; i < searchTerms.length; i++) {
                if (i == 0) {
                    jpql.append("AND (c.categoryName LIKE :categoryName" + i + " ");
                } else {
                    jpql.append("OR c.categoryName LIKE :categoryName" + i + " ");
                }
            }
            jpql.append(") ");
        }

        if (furnitureRequestDTO.getPriceStart() != null) {
            jpql.append("AND f.furniturePrice >= :priceStart ");
        }
        if (furnitureRequestDTO.getPriceEnd() != null) {
            jpql.append("AND f.furniturePrice <= :priceEnd ");
        }

        // Tạo câu truy vấn
        TypedQuery<Furniture> query = em.createQuery(jpql.toString(), Furniture.class);
        query.setParameter("customerId", furnitureRequestDTO.getCustomerId());
        query.setParameter("status1", EOrderStatus.FEEDBACKED);
        query.setParameter("status2", EOrderStatus.DELIVERED);
        query.setParameter("status3", EOrderStatus.DELIVERING);

        if (furnitureRequestDTO.getCategoryName() != null && !furnitureRequestDTO.getCategoryName().isEmpty()) {
            String[] searchTerms = furnitureRequestDTO.getCategoryName().split("\\s+");  // Tách chuỗi tìm kiếm
            for (int i = 0; i < searchTerms.length; i++) {
                query.setParameter("categoryName" + i, "%" + searchTerms[i] + "%");  // Thêm dấu % để sử dụng LIKE
            }
        }
        if (furnitureRequestDTO.getPriceStart() != null) {
            query.setParameter("priceStart", furnitureRequestDTO.getPriceStart());
        }
        if (furnitureRequestDTO.getPriceEnd() != null) {
            query.setParameter("priceEnd", furnitureRequestDTO.getPriceEnd());
        }
        return query.getResultList();
    }

}
