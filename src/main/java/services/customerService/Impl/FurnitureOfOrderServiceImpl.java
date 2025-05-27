package services.customerService.Impl;

import DAO.customerDAO.IFurnitureDAO;
import DAO.customerDAO.impl.FurnitureDAOImpl;
import DTO.customerDTO.requestDTO.FurnitureRequestDTO;
import DTO.customerDTO.responseDTO.FurnitureOfOrderResponseDTO;
import business.Furniture;
import Mapper.customerConvert.FurnitureConvert;
import services.customerService.IFurnitureOfOrderService;

import java.util.*;

public class FurnitureOfOrderServiceImpl implements IFurnitureOfOrderService {
    FurnitureConvert furnitureConvert = new FurnitureConvert();
    IFurnitureDAO furnitureDAO = new FurnitureDAOImpl();
    @Override
    public List<FurnitureOfOrderResponseDTO> getProductOfOrder(Long orderID) {
        List<Furniture> furnitures = furnitureDAO.getFurnituresByOrderId(orderID);
        Map<Long, FurnitureOfOrderResponseDTO> groupedMap = new HashMap<>();
        for (Furniture furniture : furnitures) {
            Long categoryId = furniture.getCategory().getId();
            // Lấy hoặc tạo mới ProductOfOrderResponseDTO cho Category này
            FurnitureOfOrderResponseDTO dto = groupedMap.getOrDefault(categoryId, new FurnitureOfOrderResponseDTO());
            if (dto.getCategoryName() == null) {
                dto = FurnitureConvert.convertToDTO(furniture);
            }
            if (dto.getTotalPrice() == null) {
                dto.setTotalPrice(0L);
            }
            if (dto.getQuantity() == null) {
                dto.setQuantity(0L);
            }
            dto.setTotalPrice(dto.getTotalPrice() + furniture.getFurniturePrice());
            dto.setQuantity(dto.getQuantity() + 1);
            groupedMap.put(categoryId, dto);
        }
        return new ArrayList<>(groupedMap.values());
    }

    @Override
    public Long totalPriceOfOrder(Long orderID) {
        List<Furniture> furnitures = furnitureDAO.getFurnituresByOrderId(orderID);
        Long totalPrice = 0L;
        for (Furniture furniture : furnitures) {
            totalPrice += furniture.getFurniturePrice();
        }
        return totalPrice;
    }

    @Override
    public List<FurnitureOfOrderResponseDTO> getFurnituresByCustomerId(FurnitureRequestDTO furnitureRequestDTO) {
        List<Furniture> furnitures = furnitureDAO.getFurnituresByCustomerId(furnitureRequestDTO);
        Map<Long, FurnitureOfOrderResponseDTO> groupedMap = new HashMap<>();
        for (Furniture furniture : furnitures) {
            Long categoryId = furniture.getCategory().getId();
            // Lấy hoặc tạo mới ProductOfOrderResponseDTO cho Category này
            FurnitureOfOrderResponseDTO dto = groupedMap.getOrDefault(categoryId, new FurnitureOfOrderResponseDTO());
            if (dto.getCategoryName() == null) {
                dto = FurnitureConvert.convertToDTO(furniture);
            }
            if (dto.getTotalPrice() == null) {
                dto.setTotalPrice(0L);
            }
            if (dto.getQuantity() == null) {
                dto.setQuantity(0L);
            }
            dto.setTotalPrice(dto.getTotalPrice() + furniture.getFurniturePrice());
            dto.setQuantity(dto.getQuantity() + 1);
            groupedMap.put(categoryId, dto);
        }
        List<FurnitureOfOrderResponseDTO> resultList = new ArrayList<>(groupedMap.values());

        Collections.sort(resultList, new Comparator<FurnitureOfOrderResponseDTO>() {
            @Override
            public int compare(FurnitureOfOrderResponseDTO o1, FurnitureOfOrderResponseDTO o2) {
                return o2.getQuantity().compareTo(o1.getQuantity()); // So sánh theo quantity giảm dần
            }
        });

        return resultList;
    }
}
