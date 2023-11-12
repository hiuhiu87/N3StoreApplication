/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.request;

/**
 *
 * @author Admin
 */
public class AddProductDetailRequest {

    private String nameProduct;

    private Double sellPrice;

    private Double originPrice;

    private String nameColor;

    private String nameSole;

    private String nameMaterial;

    private String nameSize;
    
    private Integer quantity;

    private String description;

    public AddProductDetailRequest() {
    }

    public AddProductDetailRequest(String nameProduct, Double sellPrice, Double originPrice, String nameColor, String nameSole, String nameMaterial, String nameSize, Integer quantity, String description) {
        this.nameProduct = nameProduct;
        this.sellPrice = sellPrice;
        this.originPrice = originPrice;
        this.nameColor = nameColor;
        this.nameSole = nameSole;
        this.nameMaterial = nameMaterial;
        this.nameSize = nameSize;
        this.quantity = quantity;
        this.description = description;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Double getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(Double originPrice) {
        this.originPrice = originPrice;
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

    public String getNameMaterial() {
        return nameMaterial;
    }

    public void setNameMaterial(String nameMaterial) {
        this.nameMaterial = nameMaterial;
    }

    public String getNameSize() {
        return nameSize;
    }

    public void setNameSize(String nameSize) {
        this.nameSize = nameSize;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
   
}
