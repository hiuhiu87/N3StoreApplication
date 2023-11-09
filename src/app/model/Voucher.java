/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.model;

import java.util.Date;

/**
 *
 * @author NGUYỄN ĐỨC LƯƠNG
 */
public class Voucher {

    private int id;
    private String ten;
    private String code;
    private int quantity;
    private Date start_Date;
    private Date end_Date;
    private float min_values_condition;
    private String type;
    private float values;
    private float max_values;
    private int deleted;

    public Voucher() {
    }

    public Voucher(int id, String ten, String code, int quantity, Date start_Date, Date end_Date, float min_values_condition, String type, float values, float max_values, int deleted) {
        this.id = id;
        this.ten = ten;
        this.code = code;
        this.quantity = quantity;
        this.start_Date = start_Date;
        this.end_Date = end_Date;
        this.min_values_condition = min_values_condition;
        this.type = type;
        this.values = values;
        this.max_values = max_values;
        this.deleted = deleted;
    }

    public Voucher(String ten, String code, int quantity, Date start_Date, Date end_Date, float min_values_condition, String type, float values, float max_values, int deleted) {
        this.ten = ten;
        this.code = code;
        this.quantity = quantity;
        this.start_Date = start_Date;
        this.end_Date = end_Date;
        this.min_values_condition = min_values_condition;
        this.type = type;
        this.values = values;
        this.max_values = max_values;
        this.deleted = deleted;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getStart_Date() {
        return start_Date;
    }

    public void setStart_Date(Date start_Date) {
        this.start_Date = start_Date;
    }

    public Date getEnd_Date() {
        return end_Date;
    }

    public void setEnd_Date(Date end_Date) {
        this.end_Date = end_Date;
    }

    public float getMin_values_condition() {
        return min_values_condition;
    }

    public void setMin_values_condition(float min_values_condition) {
        this.min_values_condition = min_values_condition;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getValues() {
        return values;
    }

    public void setValues(float values) {
        this.values = values;
    }

    public float getMax_values() {
        return max_values;
    }

    public void setMax_values(float max_values) {
        this.max_values = max_values;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

}
