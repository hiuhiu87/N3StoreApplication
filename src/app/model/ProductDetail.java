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

    private Integer idSole;

    private String name;

    private byte[] imageProduct;

    private Double sellPrice;
    
    private Double originPrice;

    private Integer quantity;

    private Double weight;

    private Boolean deleted;

    public ProductDetail() {
    }

    public ProductDetail(Integer id, Integer idSize, Integer idProduct, Integer idBrand, Integer idMaterial, Integer idColor, Integer idSole, String name, byte[] imageProduct, Double sellPrice, Double originPrice, Integer quantity, Double weight, Boolean deleted) {
        this.id = id;
        this.idSize = idSize;
        this.idProduct = idProduct;
        this.idBrand = idBrand;
        this.idMaterial = idMaterial;
        this.idColor = idColor;
        this.idSole = idSole;
        this.name = name;
        this.imageProduct = imageProduct;
        this.sellPrice = sellPrice;
        this.originPrice = originPrice;
        this.quantity = quantity;
        this.weight = weight;
        this.deleted = deleted;
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

    public Integer getIdSole() {
        return idSole;
    }

    public void setIdSole(Integer idSole) {
        this.idSole = idSole;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImageProduct() {
        return imageProduct;
    }

    public void setImageProduct(byte[] imageProduct) {
        this.imageProduct = imageProduct;
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
