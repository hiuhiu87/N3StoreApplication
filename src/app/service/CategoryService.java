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
        return categoryRepository.getAll();
    }

    public String addCategory(Category category) {
        if (category.getName().trim().isEmpty()) {
            return "Tên Danh Mục Không Được Để Chóng";
        }
        int res = categoryRepository.add(category);
        if (res > 0) {
            return "Thêm Danh Mục Thành Công";
        } else {
            return "Đã Xảy Ra Lỗi";
        }
    }

    public boolean changeStatus(String name) {
        return categoryRepository.updateStatus(name) > 0;
    }

}
