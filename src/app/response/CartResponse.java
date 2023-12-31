/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.response;

/**
 *
 * @author Admin
 */
public class CartResponse {

    private String productDetailCode;

    private String productName;

    private String nameSize;

    private String nameMaterial;

    private String nameColor;

    private String nameSole;

    private Double price;

    private Integer quantity;

    private Double totalMoney;

    public CartResponse() {
    }

    public CartResponse(String productDetailCode, String productName, String nameSize, String nameMaterial, String nameColor, String nameSole, Double price, Integer quantity, Double totalMoney) {
        this.productDetailCode = productDetailCode;
        this.productName = productName;
        this.nameSize = nameSize;
        this.nameMaterial = nameMaterial;
        this.nameColor = nameColor;
        this.nameSole = nameSole;
        this.price = price;
        this.quantity = quantity;
        this.totalMoney = totalMoney;
    }

    public String getProductDetailCode() {
        return productDetailCode;
    }

    public void setProductDetailCode(String productDetailCode) {
        this.productDetailCode = productDetailCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }

}

// private void addColumnOrderDetail() {
//        tblModelOrderDetail.addColumn("Mã CTSP");
//        tblModelOrderDetail.addColumn("Tên Sản Phẩm");
//        tblModelOrderDetail.addColumn("Đơn Giá");
//        tblModelOrderDetail.addColumn("Số Lượng");
//        tblModelOrderDetail.addColumn("Thành Tièn");
//    }
