package Mapper.customerConvert;

import DTO.customerDTO.responseDTO.FurnitureOfOrderResponseDTO;
import business.Furniture;
import org.modelmapper.ModelMapper;

public class FurnitureConvert {
    private static ModelMapper modelMapper = new ModelMapper();

    public static FurnitureOfOrderResponseDTO convertToDTO(Furniture furniture) {
        FurnitureOfOrderResponseDTO furnitureDTO = modelMapper.map(furniture, FurnitureOfOrderResponseDTO.class);
        if (furniture.getFurnitureImages() != null && !furniture.getFurnitureImages().isEmpty()) {
            furnitureDTO.setAvatar(furniture.getFurnitureImages().get(0).getBase64Data());
        }
        return furnitureDTO;
    }
}
