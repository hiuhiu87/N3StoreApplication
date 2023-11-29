/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.response;

/**
 *
 * @author Admin
 */
public class OrderDetailResponse {
    
    private String orderCode;
    
    private String productDetailCode;
    
    private String nameProduct;
    
    private Integer quantity;
    
    private Double price;
    
    private Double totalMoney;

    public OrderDetailResponse() {
    }

    public OrderDetailResponse(String orderCode, String productDetailCode, String nameProduct, Integer quantity, Double price, Double totalMoney) {
        this.orderCode = orderCode;
        this.productDetailCode = productDetailCode;
        this.nameProduct = nameProduct;
        this.quantity = quantity;
        this.price = price;
        this.totalMoney = totalMoney;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getProductDetailCode() {
        return productDetailCode;
    }

    public void setProductDetailCode(String productDetailCode) {
        this.productDetailCode = productDetailCode;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }
    
    
    
}
