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
        sql = "select * from EMPLOYEE";
        listnv.clear();
         try {
             sql = "SELECT ID,FULLNAME,EMAIL,BIRTHDATE,GENDER,ROLE,PHONE_NUMBER,DIACHI FROM EMPLOYEE";
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
                         rs.getString(8));
                 listnv.add(nv);
                 
             }
         } catch (Exception e) {
             e.printStackTrace();
             return null;
         }
         return listnv;
         }
      public int addStudent(NhanVien nv) {
      sql = "INSERT INTO EMPLOYEE(FULLNAME,EMAIL,BIRTHDATE,GENDER,ROLE,PHONE_NUMBER,DIACHI) VALUES (?,?,?,?,?,?,?)";
          try {
            con = DBConnector.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1,nv.getTen());
            ps.setObject(2,nv.getEmail());
            ps.setObject(3,nv.getNgaySinh());
            ps.setObject(4,nv.isGender());
            ps.setObject(5,nv.isRoLe());
            ps.setObject(6,nv.getSdt());
            ps.setObject(7,nv.getDiaChi());
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
            ps.setObject(1,nv.getTen());
            ps.setObject(2,nv.getEmail());
            ps.setObject(3,nv.getNgaySinh());
            ps.setObject(4,nv.isGender());
            ps.setObject(5,nv.isRoLe());
            ps.setObject(6,nv.getSdt());
            ps.setObject(7,nv.getDiaChi());
            return ps.executeUpdate();
          } catch (Exception e) {
              e.printStackTrace();
              return 0;
          }
      }
       public int Delete(int ID) {
        try {
            con = DBConnector.getConnection();
            sql = "Delete From EMPLOYEE where ID=?";                    
            ps = con.prepareStatement(sql);
            ps.setObject(1, ID);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
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
    }
    

