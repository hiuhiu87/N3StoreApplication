/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.repository;

import app.dbconnect.DBConnector;
import app.model.KhachHang;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author H.Long
 */
public class SellRepository {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public List<KhachHang> getAllKhachHang() {
        String sql = """
                     SELECT [ID]
                           ,[FULLNAME]
                           ,[EMAIL]
                           ,[GENDER]
                           ,[DELETED]
                           ,[Address]
                           ,[PHONE_NUMBER]
                       FROM [N3STORESNEAKER].[dbo].[CUSTOMER]
                       ORDER BY ID DESC
                     """;
        List<KhachHang> listKhachHang = new ArrayList<>();
        try {
            con = DBConnector.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                KhachHang khachHang = new KhachHang(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getBoolean(4),
                        rs.getBoolean(5),
                        rs.getString(6),
                        rs.getString(7)
                );
                listKhachHang.add(khachHang);
            }
            return listKhachHang;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int addKhachHang(KhachHang kh) {
        String sql = """
                     INSERT INTO [dbo].[CUSTOMER]
                                ([FULLNAME]
                                ,[EMAIL]
                                ,[BIRTHDATE]
                                ,[GENDER]
                                ,[DELETED]
                                ,[Address]
                                ,[PHONE_NUMBER])
                          VALUES
                                (?,?,?,?,?,?,?)
                     """;
        try {
            con = DBConnector.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, kh.getFullName());
            ps.setObject(2, kh.getEmail());
            ps.setObject(3, kh.getBirthDate());
            ps.setObject(4, kh.isGender());
            ps.setObject(5, kh.isDeleted());
            ps.setObject(6, kh.getAddress());
            ps.setObject(7, kh.getPhoneNumber());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int updateKhachHang(KhachHang kh, int id) {
        String sql = """
                     UPDATE [dbo].[CUSTOMER]
                        SET [FULLNAME] = ?
                           ,[EMAIL] = ?
                           ,[GENDER] = ?
                           ,[DELETED] = ?
                           ,[Address] = ?
                           ,[PHONE_NUMBER] = ?
                      WHERE ID = ?
                     """;
        try {
            con = DBConnector.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, kh.getFullName());
            ps.setObject(2, kh.getEmail());
            ps.setObject(3, kh.isGender());
            ps.setObject(4, kh.isDeleted());
            ps.setObject(5, kh.getAddress());
            ps.setObject(6, kh.getPhoneNumber());
            ps.setObject(7, id);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int checkProductDetailInOrderDetail(String productDetailCode, String orderCode) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         SELECT od.QUANTITY 
                         FROM N3STORESNEAKER.dbo.ORDER_DETAIL od 
                         JOIN N3STORESNEAKER.dbo.PRODUCT_DETAIL pd on od.ID_PRODUCT_DETAIl = od.ID_PRODUCT_DETAIl
                         JOIN N3STORESNEAKER.dbo.ORDERS o on od.ID_ORDER = o.ID 
                         WHERE pd.CODE = ? AND o.CODE = ?;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, productDetailCode);
            stm.setObject(2, orderCode);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
