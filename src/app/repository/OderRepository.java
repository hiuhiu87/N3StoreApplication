/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.repository;

import app.dbconnect.DBConnector;
import app.model.Oders;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author H.Long
 */
public class OderRepository {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public List<Oders> getAllOders() {
        List<Oders> listOders = new ArrayList<>();
        String sql = """
                     SELECT
                         O.ID AS idOrder,
                         O.CODE AS code,
                         C.FULLNAME AS nameCustomer,
                         E.FULLNAME AS nameEmployee,
                         O.PHONE_NUMBER AS phoneNumber,
                         O.PAYMENT_METHOD AS paymentMethod,
                         O.CUSTOMERMONEY AS customerMoney,
                         O.TOTAL_MONEY AS totalMoney,
                         O.MONEY_REDUCED AS moneyReduce,
                         O.DATECREATE AS dateCreateDate,
                         O.STATUS AS status,
                         O.NOTE AS note,
                         O.DELETED AS deleted
                     FROM
                         ORDERS O
                     JOIN CUSTOMER C ON O.ID_CUSTOMER = C.ID
                     JOIN EMPLOYEE E ON O.ID_EMPLOYEE = E.ID;
                     """;
        try {
            con = DBConnector.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Oders oders = new Oders(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getDouble(7),
                        rs.getDouble(8),
                        rs.getDouble(9),
                        rs.getDate(10),
                        rs.getInt(11),
                        rs.getString(12),
                        rs.getBoolean(13)
                );
                listOders.add(oders);
            }
            return listOders;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Oders> getPaginatedOders(int offset, int limit) {
        List<Oders> listOdersPhanTrang = new ArrayList<>();

        String sql = """
                               SELECT
                                      O.ID AS idOrder,
                                      O.CODE AS code,
                                      C.FULLNAME AS nameCustomer,
                                      E.FULLNAME AS nameEmployee,
                                      O.PHONE_NUMBER AS phoneNumber,
                                      O.PAYMENT_METHOD AS paymentMethod,
                                      O.CUSTOMERMONEY AS customerMoney,
                                      O.TOTAL_MONEY AS totalMoney,
                                      O.MONEY_REDUCED AS moneyReduce,
                                      O.DATECREATE AS dateCreateDate,
                                      O.STATUS AS status,
                                      O.NOTE AS note,
                                      O.DELETED AS deleted
                                  FROM
                                      ORDERS O
                                  JOIN CUSTOMER C ON O.ID_CUSTOMER = C.ID
                                  JOIN EMPLOYEE E ON O.ID_EMPLOYEE = E.ID
                                  ORDER BY O.ID
                                  OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
                 """;
        try {
            con = DBConnector.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, offset);
            ps.setObject(2, limit);
            rs = ps.executeQuery();

            while (rs.next()) 
            {
                Oders oders = new Oders(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getDouble(7),
                        rs.getDouble(8),
                        rs.getDouble(9),
                        rs.getDate(10),
                        rs.getInt(11),
                        rs.getString(12),
                        rs.getBoolean(13)
                );
                listOdersPhanTrang.add(oders);
            }
            return listOdersPhanTrang;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int countOder() {
        String sql = """
                   SELECT COUNT(*) FROM ORDERS
                   """;
        int count = 0;
        try {
            con = DBConnector.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {                
                count = rs.getInt(1);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<Oders> getAllListByStatus(int status) {
        List<Oders> listOders = new ArrayList<>();
        String sql = """
                     SELECT
                         O.ID AS idOrder,
                         O.CODE AS code,
                         C.FULLNAME AS nameCustomer,
                         E.FULLNAME AS nameEmployee,
                         O.PHONE_NUMBER AS phoneNumber,
                         O.PAYMENT_METHOD AS paymentMethod,
                         O.CUSTOMERMONEY AS customerMoney,
                         O.TOTAL_MONEY AS totalMoney,
                         O.MONEY_REDUCED AS moneyReduce,
                         O.DATECREATE AS dateCreateDate,
                         O.STATUS AS status,
                         O.NOTE AS note,
                         O.DELETED AS deleted
                     FROM
                         ORDERS O
                     JOIN CUSTOMER C ON O.ID_CUSTOMER = C.ID
                     JOIN EMPLOYEE E ON O.ID_EMPLOYEE = E.ID
                     WHERE O.STATUS = ?
                     """;
        try {
            con = DBConnector.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, status);
            rs = ps.executeQuery();

            while (rs.next()) {
                Oders oders = new Oders(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getDouble(7),
                        rs.getDouble(8),
                        rs.getDouble(9),
                        rs.getDate(10),
                        rs.getInt(11),
                        rs.getString(12),
                        rs.getBoolean(13)
                );
                listOders.add(oders);
            }
            return listOders;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
