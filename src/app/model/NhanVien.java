/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.model;

import java.util.Date;

/**
 *
 * @author Admin
 */
public class NhanVien {

    private Integer id;
    private String ten;
    private Date ngaySinh;
    private String email;
    private boolean gender;
    private boolean roLe;
    private String sdt;
    private String diaChi;
    private boolean deleted;

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean Deleted) {
        this.deleted = Deleted;
    }

    public NhanVien() {
    }

    public NhanVien(Integer id, String ten, Date ngaySinh, String email, boolean gender, boolean roLe, String sdt, String diaChi, boolean deleted) {
        this.id = id;
        this.ten = ten;
        this.ngaySinh = ngaySinh;
        this.email = email;
        this.gender = gender;
        this.roLe = roLe;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.deleted = deleted;
    }

    public NhanVien(Integer id, String ten, Date ngaySinh, String email, boolean gender, boolean roLe, String Sdt, String diaChi) {
        this.id = id;
        this.ten = ten;
        this.ngaySinh = ngaySinh;
        this.email = email;
        this.gender = gender;
        this.roLe = roLe;
        this.sdt = Sdt;
        this.diaChi = diaChi;

    }

    public Integer getID() {
        return id;
    }

    public void setID(Integer id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public boolean isRoLe() {
        return roLe;
    }

    public void setRoLe(boolean roLe) {
        this.roLe = roLe;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String Sdt) {
        this.sdt = Sdt;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public Object[] toDataRow() {
        return new Object[]{this.id, this.ten, this.getEmail(), this.getNgaySinh(), this.gender, this.roLe, this.sdt, this.diaChi};
    }
}
