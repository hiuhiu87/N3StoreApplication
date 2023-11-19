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
public class OrderDetail {

    private Integer id;

    private Integer idProductdetail;

    private Integer quantity;

    private Double price;

    private Double totalMoney;

    private Integer idOrder;

    public OrderDetail() {
    }

    public OrderDetail(Integer id, Integer idProductdetail, Integer quantity, Double price, Double totalMoney, Integer idOrder) {
        this.id = id;
        this.idProductdetail = idProductdetail;
        this.quantity = quantity;
        this.price = price;
        this.totalMoney = totalMoney;
        this.idOrder = idOrder;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdProductdetail() {
        return idProductdetail;
    }

    public void setIdProductdetail(Integer idProductdetail) {
        this.idProductdetail = idProductdetail;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Integer getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Integer idOrder) {
        this.idOrder = idOrder;
    }

}
