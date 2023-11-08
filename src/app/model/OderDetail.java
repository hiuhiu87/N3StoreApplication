/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.model;

import java.util.Date;

/**
 *
 * @author H.Long
 */
public class OderDetail {
    private String code;
    private String nameCustomer;
    private String nameEmployee;
    private String nameProduct;
    private String paymentMethod;
    private double totalMoney;
    private double price;
    private int quantity;
    private Date dateCreate;
    private String voucher;

    public OderDetail() {
    }

    public OderDetail(String code, String nameCustomer, String nameEmployee, String nameProduct, String paymentMethod, double totalMoney, double price, int quantity, Date dateCreate, String voucher) {
        this.code = code;
        this.nameCustomer = nameCustomer;
        this.nameEmployee = nameEmployee;
        this.nameProduct = nameProduct;
        this.paymentMethod = paymentMethod;
        this.totalMoney = totalMoney;
        this.price = price;
        this.quantity = quantity;
        this.dateCreate = dateCreate;
        this.voucher = voucher;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    public String getNameEmployee() {
        return nameEmployee;
    }

    public void setNameEmployee(String nameEmployee) {
        this.nameEmployee = nameEmployee;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }
    
    public Object[] toDataOderDetail(){
   
     return new Object[]{
        this.code,
        this.nameCustomer,
        this.nameEmployee,
        this.nameProduct,
        this.paymentMethod,
        this.totalMoney,
        this.price,
        this.quantity,
        this.dateCreate,
        this.voucher,
     };
    }
}
