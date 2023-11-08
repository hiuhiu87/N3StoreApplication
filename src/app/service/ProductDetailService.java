/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.model.Color;
import app.model.Product;
import app.repository.ColorRepository;
import app.repository.CompanyRepository;
import app.repository.MaterialRepository;
import app.repository.ProductRepository;
import app.repository.SizeRepository;
import app.repository.SoleRepository;
import app.request.AddProductDetailRequest;

/**
 *
 * @author Admin
 */
public class ProductDetailService {

    private final ProductRepository productRepository = new ProductRepository();
    private final ColorRepository colorRepository = new ColorRepository();
    private final MaterialRepository materialRepository = new MaterialRepository();
    private final CompanyRepository companyRepository = new CompanyRepository();
    private final SizeRepository sizeRepository = new SizeRepository();
    private final SoleRepository soleRepository = new SoleRepository();

//    public String addProductDetail(AddProductDetailRequest request) {
//        Product checkProduct = productRepository.findByName(request.getNameProduct());
//        Color checkColor = colorRepository.findByName(request.getNameColor());
//    }

}
