/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.request;

import java.util.Date;

/**
 *
 * @author Admin
 */
public class NewOrderRequest {
    
    private String code;

    private Integer idCustomer;

    private Integer idEmployee;

    public NewOrderRequest() {
    }

    public NewOrderRequest(String code, Integer idCustomer, Integer idEmployee) {
        this.code = code;
        this.idCustomer = idCustomer;
        this.idEmployee = idEmployee;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
