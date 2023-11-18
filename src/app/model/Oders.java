/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt 
to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java
to edit this template
 */
package app.model;

import java.util.Date;

/**
 *
 * @author H.Long
 */
public class Oders {

    private int idOrder;
    private String code;
    private String nameCustomer;
    private String nameEmployee;
    private String phoneNumber;
    private String paymentMethod;
    private double customerMoney;
    private double totalMoney;
    private double moneyReduce;
    private Date dateCreateDate;
    private int status;
    private String note;
    private int quantityProduct;
    private double sellProduct;
    private String nameProduct;
    private boolean deleted;

    public Oders() {
    }

    public Oders(
            int idOrder
            , String code
            , String nameCustomer
            , String nameEmployee
            ,String phoneNumber
            , String paymentMethod
            , double customerMoney
            , double totalMoney
            , double moneyReduce
            , Date dateCreateDate
            , int status
            , String note
            , boolean deleted
    ) 
    {
        this.idOrder = idOrder;
        this.code = code;
        this.nameCustomer = nameCustomer;
        this.nameEmployee = nameEmployee;
        this.phoneNumber = phoneNumber;
        this.paymentMethod = paymentMethod;
        this.customerMoney = customerMoney;
        this.totalMoney = totalMoney;
        this.moneyReduce = moneyReduce;
        this.dateCreateDate = dateCreateDate;
        this.status = status;
        this.note = note;
        this.deleted = deleted;
    }

    public Oders( String code
            , String nameCustomer
            , String nameEmployee
            ,  String paymentMethod
            , double customerMoney
            , double totalMoney
            , double moneyReduce
            , Date dateCreateDate
            ,  int quantityProduct
            , double sellProduct
            , String nameProduct
    ) 
    {
        this.code = code;
        this.nameCustomer = nameCustomer;
        this.nameEmployee = nameEmployee;
        this.paymentMethod = paymentMethod;
        this.customerMoney = customerMoney;
        this.totalMoney = totalMoney;
        this.moneyReduce = moneyReduce;
        this.dateCreateDate = dateCreateDate;
        this.quantityProduct = quantityProduct;
        this.sellProduct = sellProduct;
        this.nameProduct = nameProduct;
    }

    public int getQuantityProduct() {
        return quantityProduct;
    }

    public void setQuantityProduct(int quantityProduct) {
        this.quantityProduct = quantityProduct;
    }

    public double getSellProduct() {
        return sellProduct;
    }

    public void setSellProduct(double sellProduct) {
        this.sellProduct = sellProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }
    
    
    
    

    public String getNameEmployee() {
        return nameEmployee;
    }

    public void setNameEmployee(String nameEmployee) {
        this.nameEmployee = nameEmployee;
    }

    public double getCustomerMoney() {
        return customerMoney;
    }

    public void setCustomerMoney(double customerMoney) {
        this.customerMoney = customerMoney;
    }

   

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public double getMoneyReduce() {
        return moneyReduce;
    }

    public void setMoneyReduce(double moneyReduce) {
        this.moneyReduce = moneyReduce;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Date getDateCreateDate() {
        return dateCreateDate;
    }

    public void setDateCreateDate(Date dateCreateDate) {
        this.dateCreateDate = dateCreateDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    
    

    public Object[] toDataOrder() {
        String statusString = (this.status == 1) ? "Chờ thanh toán" : (this.status == 2) ? "Đã thanh toán" : "Hủy" ;
        return new Object[]{
            this.idOrder,
            this.code,
            this.nameCustomer,
            this.nameEmployee,
            this.phoneNumber,
            this.paymentMethod,
            this.customerMoney,
            this.totalMoney,
            this.moneyReduce,
            this.dateCreateDate,
            statusString,
            this.note,
            this.deleted ? "Đang hoạt động" : "Ngưng hoạt động"
        };
    }

}
