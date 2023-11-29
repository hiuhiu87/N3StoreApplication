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
public class Order {

    private Integer id;
    private String code;
    private Integer idCustomer;
    private Integer idEmployee;
    private Integer idVoucher;
    private String phoneNumber;
    private String paymentMethod;
    private Double customerMoney;
    private Double totalMoney;
    private Double moneyReduce;
    private Date createDate;
    private Integer status;
    private Double sellProduct;
    private Boolean deleted;

    public Order() {
    }

    public Order(Integer id, String code, Integer idCustomer, Integer idEmployee, Integer idVoucher, String phoneNumber, String paymentMethod, Double customerMoney, Double totalMoney, Double moneyReduce, Date createDate, Integer status, Double sellProduct, Boolean deleted) {
        this.id = id;
        this.code = code;
        this.idCustomer = idCustomer;
        this.idEmployee = idEmployee;
        this.idVoucher = idVoucher;
        this.phoneNumber = phoneNumber;
        this.paymentMethod = paymentMethod;
        this.customerMoney = customerMoney;
        this.totalMoney = totalMoney;
        this.moneyReduce = moneyReduce;
        this.createDate = createDate;
        this.status = status;
        this.sellProduct = sellProduct;
        this.deleted = deleted;
    }

    public Integer getIdVoucher() {
        return idVoucher;
    }

    public void setIdVoucher(Integer idVoucher) {
        this.idVoucher = idVoucher;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(Integer idCustomer) {
        this.idCustomer = idCustomer;
    }

    public Integer getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Integer idEmployee) {
        this.idEmployee = idEmployee;
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

    public Double getCustomerMoney() {
        return customerMoney;
    }

    public void setCustomerMoney(Double customerMoney) {
        this.customerMoney = customerMoney;
    }

    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Double getMoneyReduce() {
        return moneyReduce;
    }

    public void setMoneyReduce(Double moneyReduce) {
        this.moneyReduce = moneyReduce;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getSellProduct() {
        return sellProduct;
    }

    public void setSellProduct(Double sellProduct) {
        this.sellProduct = sellProduct;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

}
