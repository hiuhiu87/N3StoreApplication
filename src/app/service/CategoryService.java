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

}
