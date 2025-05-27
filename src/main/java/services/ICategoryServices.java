package services;

import DTO.request.CategoryRequest;
import DTO.response.CategoryResponse;

import java.util.List;

public interface ICategoryServices {
    List<CategoryResponse> getListCategory();
    CategoryResponse getCategoryById(Long id);
    boolean addCategory(CategoryRequest category);
    void updateCategory(CategoryRequest category);
}
