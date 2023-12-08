/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.request;

/**
 *
 * @author Admin
 */
public class UpdateOrderDetailExist {
    
    private Integer quantity;
    
    private Double totalMoney;

    public UpdateOrderDetailExist() {
    }

    public UpdateOrderDetailExist(Integer quantity, Double totalMoney) {
        this.quantity = quantity;
        this.totalMoney = totalMoney;
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