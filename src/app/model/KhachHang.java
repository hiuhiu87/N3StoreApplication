/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.model;

import java.util.Date;

/**
 *
 * @author ACER
 */
public class KhachHang {

    private int id;
    private String fullName;
    private String email;
    private boolean gender;
    private String phoneNumber;
    private String address;
    private Date birthDate;
    private boolean deleted;
    private String code;

    public KhachHang() {
    }

    public KhachHang(
             int id,
             String fullName,
             String email,
             boolean gender,
             boolean deleted,
             String address,
             String phoneNumber
    ) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.deleted = deleted;
    }
    
     public KhachHang(
             String fullName,
             String email,
             boolean gender,
             boolean deleted,
             String address,
             String phoneNumber
    ) {
        this.fullName = fullName;
        this.email = email;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.deleted = deleted;
    }

    public KhachHang(int id, String fullName, String email, String phoneNumber, String address, Date birthDate) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.birthDate = birthDate;
    }

    public KhachHang(int id, String fullName, String email, boolean gender, String phoneNumber, String address, Date birthDate, boolean deleted, String code) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.birthDate = birthDate;
        this.deleted = deleted;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    public boolean isGender() {
        return gender;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Object[] toDaTaRow() {//lay du lieu vao dong cua bang
        return new Object[]{this.id, this.fullName, this.email, this.phoneNumber, this.address, this.birthDate};
    }

    public Object[] toDaTaRowLong() {//lay du lieu vao dong cua bang
        
        return new Object[]{
            this.id,
            this.fullName,
            this.email,
            this.gender ? "Nam" : "Nữ",
            this.phoneNumber,
            this.address,
            this.deleted ? "Đang hoạt động" : "Ngưng hoạt động"
        };
    }
}
