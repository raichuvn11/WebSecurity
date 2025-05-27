package DAO.customerDAO;

import DTO.customerDTO.requestDTO.FurnitureRequestDTO;
import business.Furniture;

import java.util.List;

public interface IFurnitureDAO {
    List<Furniture> getFurnituresByOrderId(Long orderId);

    List<Furniture> getFurnituresByCustomerId(FurnitureRequestDTO furnitureRequestDTO);
}