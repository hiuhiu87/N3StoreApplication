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
public class Statistical {

    private int thang;
    private String ten;
    private int soLuong;
    private int tongTien;
    private Date ngayStart;
    private Date ngayEnd;

    public Statistical() {
    }

    public Statistical(int thang, String ten, int soLuong, int tongTien) {
        this.thang = thang;
        this.ten = ten;
        this.soLuong = soLuong;
        this.tongTien = tongTien;
    }

    public Statistical(int thang, int soLuong, int tongTien) {
        this.thang = thang;
        this.soLuong = soLuong;
        this.tongTien = tongTien;
    }

    
 
    public int getThang() {
        return thang;
    }

    public void setThang(int thang) {
        this.thang = thang;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public Date getNgayStart() {
        return ngayStart;
    }

    public void setNgayStart(Date ngayStart) {
        this.ngayStart = ngayStart;
    }

    public Date getNgayEnd() {
        return ngayEnd;
    }

    public void setNgayEnd(Date ngayEnd) {
        this.ngayEnd = ngayEnd;
    }

}
