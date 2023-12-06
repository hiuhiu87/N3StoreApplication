/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.request;

/**
 *
 * @author Admin
 */
public class UpdateProductDetailRequest {

    private String description;

    private Integer quantity;

    private Double sellPrice;

    private Double originPrice;

    public UpdateProductDetailRequest() {
    }

    public UpdateProductDetailRequest(String description, Integer quantity, Double sellPrice, Double originPrice) {
        this.description = description;
        this.quantity = quantity;
        this.sellPrice = sellPrice;
        this.originPrice = originPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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

}
