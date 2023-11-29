/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.dbconnect.DBConnector;
import app.model.*;
import app.model.NhanVien;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class NhanVienService {

    Connection con = null;
    PreparedStatement ps = null;
    String sql = null;
    ResultSet rs = null;
    List<NhanVien> listnv = new ArrayList<>();

    public List<NhanVien> getAll() {

        listnv.clear();
        try {
            sql = "SELECT ID,FULLNAME,EMAIL,BIRTHDATE,GENDER,ROLE,PHONE_NUMBER,DIACHI,DELETED FROM EMPLOYEE";
            con = DBConnector.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                NhanVien nv = new NhanVien(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getDate(4),
                        rs.getString(3),
                        rs.getBoolean(5),
                        rs.getBoolean(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getBoolean(9));
                listnv.add(nv);

            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return listnv;
    }

    public ArrayList<NhanVien> getListPhanTrang(int offset, int limit) {
        ArrayList<NhanVien> list = new ArrayList<>();

        try {

            sql = """
                  SELECT ID,FULLNAME,EMAIL,BIRTHDATE,GENDER,ROLE,PHONE_NUMBER,DIACHI,DELETED FROM EMPLOYEE
                  ORDER BY ID OFFSET ? ROWS FETCH NEXT ?  ROWS ONLY
                  """;
            ps = con.prepareStatement(sql);
            ps.setObject(1, offset);
            ps.setObject(2, limit);
            ps.execute();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NhanVien nv = new NhanVien(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getDate(4),
                        rs.getString(3),
                        rs.getBoolean(5),
                        rs.getBoolean(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getBoolean(9));
                list.add(nv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int count() {
        try {
            int count = 0;
            sql = "SELECT COUNT(*) FROM EMPLOYEE";
            ps = con.prepareStatement(sql);
            ps.execute();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int addStudent(NhanVien nv) {
        sql = "INSERT INTO EMPLOYEE(FULLNAME,EMAIL,BIRTHDATE,GENDER,ROLE,PHONE_NUMBER,DIACHI) VALUES (?,?,?,?,?,?,?)";
        try {
            con = DBConnector.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, nv.getTen());
            ps.setObject(2, nv.getEmail());
            ps.setObject(3, nv.getNgaySinh());
            ps.setObject(4, nv.isGender());
            ps.setObject(5, nv.isRoLe());
            ps.setObject(6, nv.getSdt());
            ps.setObject(7, nv.getDiaChi());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int updatenv(NhanVien nv, int id) {

        sql = " UPDATE EMPLOYEE set FULLNAME = ?, EMAIL = ?,BIRTHDATE = ?,GENDER = ?,ROLE = ?,DIACHI = ? WHERE ID=?\"";
        try {
            con = DBConnector.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, nv.getTen());
            ps.setObject(2, nv.getEmail());
            ps.setObject(3, nv.getNgaySinh());
            ps.setObject(4, nv.isGender());
            ps.setObject(5, nv.isRoLe());
            ps.setObject(6, nv.getSdt());
            ps.setObject(7, nv.getDiaChi());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int Delete(int id) {
        try {
            String q = "UPDATE EMPLOYEE SET DELETED = CASE WHEN DELETED = 0 THEN 1 WHEN DELETED = 1 THEN 0 END WHERE ID = ?;";
            PreparedStatement ps = con.prepareStatement(q);
            ps.setInt(1, id);
            if (ps.executeUpdate() > 0) {
                System.out.println("Xóa thành công");
                return 1;
            }
        } catch (Exception e) {
            System.out.println("" + e.toString());
        }
        return -1;
    }

    public int updateSV(NhanVien nv, int id) {
        sql = "UPDATE EMPLOYEE SET FULLNAME = ?,EMAIL = ?,BIRTHDATE = ?,GENDER = ?,ROLE = ?,PHONE_NUMBER = ?,DIACHI = ? WHERE ID =?";
        try {
            con = DBConnector.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, nv.getTen());
            ps.setObject(2, nv.getEmail());
            ps.setObject(3, nv.getNgaySinh());
            ps.setObject(4, nv.isGender());
            ps.setObject(5, nv.isRoLe());
            ps.setObject(6, nv.getSdt());
            ps.setObject(7, nv.getDiaChi());
            ps.setObject(8, id);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public NhanVien findById(int id) {
        try (Connection con = DBConnector.getConnection()) {
            sql = """
                         SELECT ID, FULLNAME, EMAIL, BIRTHDATE, GENDER, ROLE, PHONE_NUMBER, DIACHI, DELETED
                         FROM N3STORESNEAKER.dbo.EMPLOYEE
                         WHERE ID = ?;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, id);
            rs = stm.executeQuery();
            NhanVien nhanVien = new NhanVien();
            while (rs.next()) {
                nhanVien.setID(rs.getInt(1));
                nhanVien.setTen(rs.getString(2));
                nhanVien.setEmail(rs.getString(3));
                nhanVien.setNgaySinh(rs.getDate(4));
                nhanVien.setGender(rs.getBoolean(5));
                nhanVien.setRoLe(rs.getBoolean(6));
                nhanVien.setSdt(rs.getString(7));
                nhanVien.setDiaChi(rs.getString(8));
                nhanVien.setDeleted(rs.getBoolean(9));
            }
            return nhanVien;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
