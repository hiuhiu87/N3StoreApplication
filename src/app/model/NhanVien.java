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

    Integer ID;
    String Ten;
    Date NgaySinh;
    String Email;
    boolean Gender;
    boolean RoLe;
    String Sdt;
    String DiaChi;
    boolean Deleted;
    String password;

    public boolean isDeleted() {
        return Deleted;
    }

    public void setDeleted(boolean Deleted) {
        this.Deleted = Deleted;
    }

    public NhanVien() {
    }

    public NhanVien(Integer ID, String Ten, Date NgaySinh, String Email, boolean Gender, boolean RoLe, String Sdt, String DiaChi, boolean Deleted) {
        this.ID = ID;
        this.Ten = Ten;
        this.NgaySinh = NgaySinh;
        this.Email = Email;
        this.Gender = Gender;
        this.RoLe = RoLe;
        this.Sdt = Sdt;
        this.DiaChi = DiaChi;
        this.Deleted = Deleted;
    }

    public NhanVien(String Ten, Date NgaySinh, String Email, boolean Gender, boolean RoLe, String Sdt, String DiaChi, String password) {
        this.Ten = Ten;
        this.NgaySinh = NgaySinh;
        this.Email = Email;
        this.Gender = Gender;
        this.RoLe = RoLe;
        this.Sdt = Sdt;
        this.DiaChi = DiaChi;
        this.password = password;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String Ten) {
        this.Ten = Ten;
    }

    public Date getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(Date NgaySinh) {
        this.NgaySinh = NgaySinh;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public boolean isGender() {
        return Gender;
    }

    public void setGender(boolean Gender) {
        this.Gender = Gender;
    }

    public boolean isRoLe() {
        return RoLe;
    }

    public void setRoLe(boolean RoLe) {
        this.RoLe = RoLe;
    }

    public String getSdt() {
        return Sdt;
    }

    public void setSdt(String Sdt) {
        this.Sdt = Sdt;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public Object[] toDataRow() {
        return new Object[]{this.ID, this.Ten, this.getEmail(), this.getNgaySinh(), this.Gender, this.RoLe, this.Sdt, this.DiaChi};
    }
}
