/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.response;

/**
 *
 * @author Admin
 */
public class ProductBoughtCustomerResponse {

    private String orderCode;

    private String productDetailCode;

    private String nameProduct;

    private String nameSize;

    private String nameMaterial;

    private String nameColor;

    private String nameSole;

    public ProductBoughtCustomerResponse() {
    }

    public ProductBoughtCustomerResponse(String orderCode, String productDetailCode, String nameProduct, String nameSize, String nameMaterial, String nameColor, String nameSole) {
        this.orderCode = orderCode;
        this.productDetailCode = productDetailCode;
        this.nameProduct = nameProduct;
        this.nameSize = nameSize;
        this.nameMaterial = nameMaterial;
        this.nameColor = nameColor;
        this.nameSole = nameSole;
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

    public String getNameSize() {
        return nameSize;
    }

    public void setNameSize(String nameSize) {
        this.nameSize = nameSize;
    }

    public String getNameMaterial() {
        return nameMaterial;
    }

    public void setNameMaterial(String nameMaterial) {
        this.nameMaterial = nameMaterial;
    }

    public String getNameColor() {
        return nameColor;
    }

    public void setNameColor(String nameColor) {
        this.nameColor = nameColor;
    }

    public String getNameSole() {
        return nameSole;
    }

    public void setNameSole(String nameSole) {
        this.nameSole = nameSole;
    }

}
