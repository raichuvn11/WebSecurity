package services.impl;

import DAO.CategoryDAO;
import DTO.request.CategoryRequest;
import DTO.response.CategoryResponse;
import Mapper.CategoryMapper;
import business.Category;
import services.ICategoryServices;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryServiceImpl implements ICategoryServices {

    private final CategoryDAO categoryDAO;
    //     Constructor để inject CategoryDAO
    public CategoryServiceImpl() {
        this.categoryDAO = new CategoryDAO();  // Khởi tạo CategoryDAO
    }

    @Override
    public List<CategoryResponse> getListCategory() {
        //todo
        // call dbutil, get list model, transfer model to dto, return dto
        List<Category> categories = categoryDAO.getCategoryList();
        categories.sort(Comparator.comparing(Category::getId));
        return categories.stream()
                .map(CategoryResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse getCategoryById(Long id){
        Category category = categoryDAO.getCategoryByID(id);
        if (category == null) {
            return null;
        }
        return CategoryMapper.convertToDTO(category);
    }
    @Override
    public boolean addCategory(CategoryRequest categoryRequest) {
        // Kiểm tra đầu vào của DTO nếu cần
        if (categoryRequest == null) {
            throw new IllegalArgumentException("CategoryDTO cannot be null");
        }
        boolean exists = categoryDAO.existsByCategoryName(categoryRequest.getCategoryName());
        if (exists) {
            // Nếu tồn tại, trả về false để báo hiệu lỗi trùng lặp
            return false;
        }
        categoryDAO.addCategory(CategoryMapper.convertToEntity(categoryRequest));
        return true;
    }
    @Override
    public void updateCategory(CategoryRequest categoryRequest) {
        if (categoryRequest == null) {
            throw new IllegalArgumentException("CategoryDTO cannot be null");
        }
        categoryDAO.editCategory(CategoryMapper.convertToEntity(categoryRequest));
    }
}
