/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.dbconnect.DBConnector;
import app.model.KhachHang;
import app.model.Voucher;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ACER
 */
public class KhachHang_Service {

    Connection con = null;
    PreparedStatement ps = null;
    String sql = null;
    ResultSet rs = null;

    public List<KhachHang> getAll() {
        sql = "SELECT ID,FULLNAME,EMAIL,PHONE_NUMBER,Address,BIRTHDATE FROM CUSTOMER";
        List<KhachHang> lstSV = new ArrayList<>();
        try {
            con = DBConnector.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                //  public SinhVien(String maSV, String tenSV, int tuoi, int kyHoc, String nganhHoc, double diemTB, b
                KhachHang s = new KhachHang(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6));
                lstSV.add(s);
            }
            return lstSV;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
 public ArrayList<KhachHang> getListPhanTrang(int offset, int limit) {
        ArrayList<KhachHang> list = new ArrayList<>();
        try {
            String q = "SELECT ID,FULLNAME,EMAIL,PHONE_NUMBER,Address,BIRTHDATE FROM CUSTOMER ORDER BY ID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
            PreparedStatement ps = con.prepareStatement(q);
            ps.setObject(1, offset);
            ps.setObject(2, limit);
            ps.execute();
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                   KhachHang s = new KhachHang(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6));
                list.add(s);
              
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public int count(){
        try {
            int count =0;
            String q = "SELECT COUNT(*) FROM CUSTOMER";
            PreparedStatement ps = con.prepareStatement(q);
            ps.execute();
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                count = rs.getInt(1);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    public int addSach(KhachHang s) {
        sql = "INSERT INTO CUSTOMER(FULLNAME,EMAIL,PHONE_NUMBER,Address,BIRTHDATE) VALUES(?,?,?,?,?)";
        try {
            con = DBConnector.getConnection();
            ps = con.prepareStatement(sql);

            ps.setObject(1, s.getFULLNAME());
            ps.setObject(2, s.getEMAIL());

            ps.setObject(3, s.getPHONE_NUMBER());
            ps.setObject(4, s.getAddress());
            ps.setObject(5, s.getBIRTHDATE());
            return ps.executeUpdate();
            //insert delete, update:executeUpdate()
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int updateSV(KhachHang cd, int id) {
        //truyền vào đối tượng mới, khóa chính đối tượng cũ
        //MASACH,TENSACH,THELOAI,DONGIA
        sql = "update CUSTOMER set FULLNAME=?,EMAIL=?,PHONE_NUMBER=?,Address=?,BIRTHDATE=? where id=?";
        try {
            con = DBConnector.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, cd.getFULLNAME());
            ps.setObject(2, cd.getEMAIL());

            ps.setObject(3, cd.getPHONE_NUMBER());
            ps.setObject(4, cd.getAddress());
            ps.setObject(5, cd.getBIRTHDATE());
            ps.setObject(6, id);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
