/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.model.Color;
import app.model.Material;
import app.model.Product;
import app.model.ProductDetail;
import app.model.Size;
import app.model.Sole;
import app.repository.ColorRepository;
import app.repository.MaterialRepository;
import app.repository.ProductDetailRepository;
import app.repository.ProductRepository;
import app.repository.SizeRepository;
import app.repository.SoleRepository;
import app.request.AddProductDetailRequest;
import app.request.UpdateProductDetailRequest;
import app.response.ProductDetailResponse;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ProductDetailService {

    private final ProductRepository productRepository = new ProductRepository();
    private final ColorRepository colorRepository = new ColorRepository();
    private final MaterialRepository materialRepository = new MaterialRepository();
    private final SizeRepository sizeRepository = new SizeRepository();
    private final SoleRepository soleRepository = new SoleRepository();
    private final ProductDetailRepository detailRepository = new ProductDetailRepository();

    public String addProductDetail(AddProductDetailRequest request) {
        Product checkProduct = productRepository.findByName(request.getNameProduct());
        Color checkColor = colorRepository.findByName(request.getNameColor());
        Material checkMaterial = materialRepository.findByName(request.getNameMaterial());
        Size checkSize = sizeRepository.findByName(request.getNameSize());
        Sole checkSole = soleRepository.findByName(request.getNameSole());
        Double checkOriginPrice = request.getOriginPrice();
        Double checkSellPrice = request.getSellPrice();
        byte[] image = null;

        if (checkColor.getId() == null) {
            return "Không Tìm Thấy Màu Sắc";
        }

        if (checkProduct.getId() == null) {
            return "Không Tìm Thấy Sản Phẩm";
        }

        if (checkMaterial.getId() == null) {
            return "Không Tìm Thấy Chất Liệu";
        }

        if (checkSole.getId() == null) {
            return "Không Tìm Thấy Đế Giày";
        }

        if (checkSize.getId() == null) {
            return "Không Tìm Thấy Kích Cỡ";
        }

        if (checkSellPrice.isNaN()) {
            return "Giá Bán Phải Là Số";
        }

        if (checkOriginPrice.isNaN()) {
            return "Giá Gốc Phải Là Số";
        }

        ProductDetail productDetail = new ProductDetail();
        productDetail.setCode(detailRepository.generateNextModelCode());
        productDetail.setIdSole(checkSole.getId());
        productDetail.setIdColor(checkColor.getId());
        productDetail.setIdProduct(checkProduct.getId());
        productDetail.setIdMaterial(checkMaterial.getId());
        productDetail.setIdSize(checkSize.getId());
        productDetail.setDeleted(Boolean.FALSE);
        productDetail.setDescription(request.getDescription());
        productDetail.setImageProduct(null);
        productDetail.setSellPrice(checkSellPrice);
        productDetail.setOriginPrice(checkOriginPrice);
        productDetail.setQuantity(request.getQuantity());
        int res = detailRepository.add(productDetail);

        if (res > 0) {
            return "Thêm Sản Phẩm Thành Công";
        } else {
            return "Đã Xảy Ra Lỗi Vui Lòng Thử Lại";
        }

    }

    public List<ProductDetailResponse> getAllListProducts() {
        return detailRepository.getAllViews();
    }

    public List<ProductDetailResponse> findByAtri(String nameSize, String nameColor, String nameMaterial, String nameSole) {
        return detailRepository.findListByAtribute(nameSize, nameColor, nameMaterial, nameSole);
    }

    public List<ProductDetailResponse> selectListDetailByNameProduct(String nameProduct) {
        return detailRepository.findListByNameProduct(nameProduct);
    }

    public boolean updateProductDetail(String code, UpdateProductDetailRequest detailRequest) {
        return detailRepository.updateProductDetail(code, detailRequest) > 0;
    }

    public int countProductRecord() {
        return detailRepository.countProductRecord();
    }

    public int countProductRecordWithNameProduct(String name) {
        Product product = productRepository.findByName(name);
        return detailRepository.countProductRecordWithNameProduct(product.getId());
    }

    public List<ProductDetailResponse> getListProductDetailViewPagntion(int offset, int limit) {
        return detailRepository.getAllViewsPagnation(offset, limit);
    }

    public List<ProductDetailResponse> getListProductDetailViewPagntionWithNameProduct(int offset, int limit, String nameProduct) {
        return detailRepository.getAllViewsPagnationWithNameProduct(offset, limit, nameProduct);
    }

    public ProductDetail findByCode(String code) {
        return detailRepository.findByName(code);
    }

    public boolean updateStatus(String code) {
        return detailRepository.updateStatus(code) > 0;
    }

    public int getQuantityProductDetailByCode(String code) {
        return detailRepository.getQuantityProductDetailByCode(code);
    }

    public boolean updateQuantityInStore(String code, int quantity) {
        return detailRepository.updateQuantity(code, quantity) > 0;
    }

    public ProductDetail findByAtribute(String nameProduct, String nameSize, String nameMaterial, String nameSole, String nameColor) {
        return detailRepository.findByAtribute(nameProduct, nameSize, nameMaterial, nameSole, nameColor);
    }

    public ProductDetail findById(int id) {
        return detailRepository.findById(id);
    }

}
