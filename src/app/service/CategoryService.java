/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.model.Category;
import app.repository.CategoryRepository;
import java.util.List;

/**
 *
 * @author Admin
 */
public class CategoryService {

    private final CategoryRepository categoryRepository = new CategoryRepository();

    public List<Category> getAll() {
        List<Category> categories = categoryRepository.getAll();
        if (categories == null) {
            throw new IllegalStateException("Failed to retrieve categories from the repository");
        }
        return categories;
    }

    public String addCategory(Category category) {
        try {
            if (category.getName().trim().isEmpty()) {
                throw new IllegalArgumentException("Tên Danh Mục Không Được Để Chóng");
            }
            if (category.getName().length() > 255) {
                throw new IllegalArgumentException("Tên Danh Mục Quá Dài");
            }
            int res = categoryRepository.add(category);
            if (res > 0) {
                return "Thêm Danh Mục Thành Công";
            } else {
                throw new RuntimeException("Đã Xảy Ra Lỗi");
            }
        } catch (Exception e) {
            throw new RuntimeException("Đã Xảy Ra Lỗi", e);
        }
    }


    public boolean changeStatus(String name) {
        return categoryRepository.updateStatus(name) > 0;
    }

}
