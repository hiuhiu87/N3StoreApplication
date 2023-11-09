/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.dbconnect.DBConnector;
import app.model.Voucher;
import app.repository.VoucherInterface;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

/**
 *
 * @author NGUYỄN ĐỨC LƯƠNG
 */
public class VoucherService implements VoucherInterface {

    private Connection conn;

    public VoucherService() {
        this.conn = DBConnector.getConnection();
    }

    @Override
    public ArrayList<Voucher> getList(String name) {
        ArrayList<Voucher> list = new ArrayList<>();
        try {
            String q = "SELECT * FROM VOUCHER WHERE NAME LIKE '%" + name + "%'";
            PreparedStatement ps = conn.prepareStatement(q);
            ps.execute();
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Voucher v = new Voucher(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDate(5), rs.getDate(6), rs.getFloat(7), rs.getString(8), rs.getFloat(9), rs.getFloat(10), rs.getInt(11));
                list.add(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int add(Voucher v) {
        try {
            String q = "INSERT INTO VOUCHER(NAME,CODE,QUANTITY,START_DATE,END_DATE,MIN_VALUE_CONDITION,TYPE,VALUE,MAX_VALUE,DELETED) VALUES (?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(q);
            ps.setString(1, v.getTen());
            ps.setString(2, v.getCode());
            ps.setInt(3, v.getQuantity());
            ps.setDate(4, (Date) v.getStart_Date());
            ps.setDate(5, (Date) v.getEnd_Date());
            ps.setFloat(6, v.getMin_values_condition());
            ps.setString(7, v.getType());
            ps.setFloat(8, v.getValues());
            ps.setFloat(9, v.getMax_values());
            ps.setInt(10, v.getDeleted());
            if(ps.executeUpdate() > 0){
                System.out.println("Thêm thành công");
                return 1;
            }
        } catch (Exception e) {
            System.out.println(""+e.toString());
        }
        return -1;
    }

    @Override
    public int remove(Voucher v) {
        try {
            String q = "DELETE FROM VOUCHER WHERE ID=?";
            PreparedStatement ps = conn.prepareStatement(q);
            ps.setInt(1, v.getId());
            if(ps.executeUpdate() > 0){
                System.out.println("Xóa thành công");
                return 1;
            }
        } catch (Exception e) {
            System.out.println(""+e.toString());
        }
        return -1;
    }

    @Override
    public int update(Voucher v, int id) {
        try {
            String q = "UPDATE VOUCHER SET NAME=?,CODE=?,QUANTITY=?,START_DATE=?,END_DATE=?,MIN_VALUE_CONDITION=?,TYPE=?,VALUE=?,MAX_VALUE=?,DELETED=? WHERE ID=?";
            PreparedStatement ps = conn.prepareStatement(q);
            ps.setString(1, v.getTen());
            ps.setString(2, v.getCode());
            ps.setInt(3, v.getQuantity());
            ps.setDate(4, (Date) v.getStart_Date());
            ps.setDate(5, (Date) v.getEnd_Date());
            ps.setFloat(6, v.getMin_values_condition());
            ps.setString(7, v.getType());
            ps.setFloat(8, v.getValues());
            ps.setFloat(9, v.getMax_values());
            ps.setInt(10, v.getDeleted());
            ps.setInt(11, id);
            return ps.executeUpdate();
            
        } catch (Exception e) {
            System.out.println(""+e.toString());
            return -1;
        }
    }

}
