/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.model.Category;
import app.model.Product;
import app.repository.CategoryRepository;
import app.repository.ProductRepository;
import app.request.AddProductRequest;
import app.response.ProductResponse;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Admin
 */
public class ProductService {

    private final ProductRepository productRepository = new ProductRepository();

    private final CategoryRepository categoryRepository = new CategoryRepository();

    public String addProduct(AddProductRequest request) {
        String name = request.getName();
        Category optionalCategory = categoryRepository.findByName(request.getCategoryName());
        if (name.trim().isEmpty()) {
            return "Tên Sản Phẩm Không Được Để Trống";
        }

        if (optionalCategory.getId() == null) {
            return "Không Tìm Thấy Danh Mục !";
        }

        Product product = new Product();
        product.setCode("SP1");
        product.setDeleted(false);
        product.setIdCategory(optionalCategory.getId());
        product.setName(name);
        int res = productRepository.add(product);
        if (res > 0) {
            return "Thêm Thành Công";
        } else {
            return "Đã Xảy ra Lỗi";
        }
    }

    public List<ProductResponse> getAllProductResponse() {
        return productRepository.getAllProductsView();
    }

}
