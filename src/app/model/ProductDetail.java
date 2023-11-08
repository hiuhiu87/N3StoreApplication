/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.model;

/**
 *
 * @author Admin
 */
public class ProductDetail {

    private Integer id;

    private Integer idSize;

    private Integer idProduct;

    private Integer idBrand;

    private Integer idMaterial;

    private Integer idColor;

    private Integer idSolor;
    
    private String name;
    
    private Double price;
    
    private Integer quantity;
    
    private Double weight;
    
    private Boolean deleted;

    public ProductDetail(Integer id, Integer idSize, Integer idProduct, Integer idBrand, Integer idMaterial, Integer idColor, Integer idSolor, String name, Double price, Integer quantity, Double weight, Boolean deleted) {
        this.id = id;
        this.idSize = idSize;
        this.idProduct = idProduct;
        this.idBrand = idBrand;
        this.idMaterial = idMaterial;
        this.idColor = idColor;
        this.idSolor = idSolor;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.weight = weight;
        this.deleted = deleted;
    }

    public ProductDetail() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdSize() {
        return idSize;
    }

    public void setIdSize(Integer idSize) {
        this.idSize = idSize;
    }

    public Integer getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
    }

    public Integer getIdBrand() {
        return idBrand;
    }

    public void setIdBrand(Integer idBrand) {
        this.idBrand = idBrand;
    }

    public Integer getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(Integer idMaterial) {
        this.idMaterial = idMaterial;
    }

    public Integer getIdColor() {
        return idColor;
    }

    public void setIdColor(Integer idColor) {
        this.idColor = idColor;
    }

    public Integer getIdSolor() {
        return idSolor;
    }

    public void setIdSolor(Integer idSolor) {
        this.idSolor = idSolor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
    
    

}
