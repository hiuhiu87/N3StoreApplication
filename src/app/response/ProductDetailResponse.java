/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.response;

/**
 *
 * @author Admin
 */
public class ProductDetailResponse {

    private String code;
    
    private String size;
    
    private String product;
    
    private String material;
    
    private String color;
    
    private String sole;
    
    private String sellPrice;
    
    private String originPrice;
    
    private String quantity;
    
    private Boolean deleted;

    public ProductDetailResponse() {
    }

    public ProductDetailResponse(String code, String size, String product, String material, String color, String sole, String sellPrice, String originPrice, String quantity, Boolean deleted) {
        this.code = code;
        this.size = size;
        this.product = product;
        this.material = material;
        this.color = color;
        this.sole = sole;
        this.sellPrice = sellPrice;
        this.originPrice = originPrice;
        this.quantity = quantity;
        this.deleted = deleted;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSole() {
        return sole;
    }

    public void setSole(String sole) {
        this.sole = sole;
    }

    public String getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(String sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(String originPrice) {
        this.originPrice = originPrice;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "ProductDetailResponse{" +
                "code='" + code + '\'' +
                ", size='" + size + '\'' +
                ", product='" + product + '\'' +
                ", material='" + material + '\'' +
                ", color='" + color + '\'' +
                ", sole='" + sole + '\'' +
                ", sellPrice='" + sellPrice + '\'' +
                ", originPrice='" + originPrice + '\'' +
                ", quantity='" + quantity + '\'' +
                ", deleted=" + deleted +
                '}';
    }
    
    
    
}
