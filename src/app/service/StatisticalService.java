/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.dbconnect.DBConnector;
import app.model.Statistical;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author NGUYỄN ĐỨC LƯƠNG
 */
public class StatisticalService {

    private Connection conn;

    public StatisticalService() {
        this.conn = DBConnector.getConnection();
    }
    
    public ArrayList<Statistical> getTop5(String year) {
        ArrayList<Statistical> list = new ArrayList<>();
        try {
            String q = """
                       SELECT TOP(5) SUM(b.QUANTITY) AS SL, d.NAME
                       FROM ORDERS a
                       JOIN ORDER_DETAIL b ON a.ID = b.ID_ORDER
                       JOIN PRODUCT_DETAIL c ON b.ID_PRODUCT_DETAIL = c.ID
                       JOIN PRODUCT d ON c.ID_PRODUCT = d.ID
                       WHERE YEAR(a.DATECREATE) = ?
                       GROUP BY d.NAME
                       ORDER BY SL DESC;
                       """;
            PreparedStatement ps = conn.prepareStatement(q);
            ps.setString(1, year);
            ps.execute();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Statistical s = new Statistical(rs.getInt(1), rs.getString(2));
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<Statistical> getList() {
        ArrayList<Statistical> list = new ArrayList<>();
        try {
            String q = """
                       SELECT MONTH(DATECREATE) as Tháng,
                              d.NAME,
                              b.QUANTITY,
                              SUM(a.TOTAL_MONEY) as sum
                       FROM ORDERS a
                       JOIN ORDER_DETAIL b ON a.ID = b.ID_ORDER
                       JOIN PRODUCT_DETAIL c ON b.ID_PRODUCT_DETAIL = c.ID
                       JOIN PRODUCT d ON c.ID_PRODUCT = d.ID
                       GROUP BY d.NAME, b.QUANTITY, MONTH(DATECREATE)
                       ORDER BY Tháng DESC;
                       """;
            PreparedStatement ps = conn.prepareStatement(q);
            ps.execute();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Statistical s = new Statistical(rs.getInt(1),
                        rs.getString(2), rs.getInt(3),
                        rs.getInt(4));
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<Statistical> getCombobox(int month, String year) {
        ArrayList<Statistical> list = new ArrayList<>();
        try {
            String q = """
                       SELECT 
                           MONTH(DATECREATE) AS Tháng,
                           d.NAME,
                           b.QUANTITY,
                           SUM(a.TOTAL_MONEY) AS sum
                       FROM 
                           ORDERS a
                       JOIN 
                           ORDER_DETAIL b ON a.ID = b.ID_ORDER
                       JOIN 
                           PRODUCT_DETAIL c ON b.ID_PRODUCT_DETAIL = c.ID
                       JOIN 
                           PRODUCT d ON c.ID_PRODUCT = d.ID
                       WHERE 
                           MONTH(DATECREATE) = ? AND YEAR(DATECREATE) = ?
                       GROUP BY 
                           d.NAME, b.QUANTITY, MONTH(DATECREATE)
                       ORDER BY 
                           Tháng DESC;
                       """;
            PreparedStatement ps = conn.prepareStatement(q);
            ps.setInt(1, month);
            ps.setString(2, year);
            ps.execute();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Statistical s = new Statistical(rs.getInt(1),
                        rs.getString(2), rs.getInt(3),
                        rs.getInt(4));
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<Statistical> getComboboxYear(String year) {
        ArrayList<Statistical> list = new ArrayList<>();
        try {
            String q = """
                       SELECT 
                           MONTH(DATECREATE) AS Tháng,
                           d.NAME,
                           b.QUANTITY,
                           SUM(a.TOTAL_MONEY) AS sum
                       FROM 
                           ORDERS a
                       JOIN 
                           ORDER_DETAIL b ON a.ID = b.ID_ORDER
                       JOIN 
                           PRODUCT_DETAIL c ON b.ID_PRODUCT_DETAIL = c.ID
                       JOIN 
                           PRODUCT d ON c.ID_PRODUCT = d.ID
                       WHERE 
                           YEAR(DATECREATE) = ?
                       GROUP BY 
                           d.NAME, b.QUANTITY, MONTH(DATECREATE)
                       ORDER BY 
                           Tháng DESC;
                       """;
            PreparedStatement ps = conn.prepareStatement(q);
            ps.setString(1, year);
            ps.execute();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Statistical s = new Statistical(rs.getInt(1),
                        rs.getString(2), rs.getInt(3),
                        rs.getInt(4));
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int sumDay(Date start, Date end) {
        try {
            String q = """
                       SELECT 
                           SUM(a.TOTAL_MONEY) AS sum
                       FROM 
                           ORDERS a
                       JOIN 
                           ORDER_DETAIL b ON a.ID = b.ID_ORDER
                       JOIN 
                           PRODUCT_DETAIL c ON b.ID_PRODUCT_DETAIL = c.ID
                       JOIN 
                           PRODUCT d ON c.ID_PRODUCT = d.ID
                       WHERE 
                           DATECREATE BETWEEN ? AND ?;
                       """;
            PreparedStatement ps = conn.prepareStatement(q);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            ps.setString(1, sdf.format(start));
            ps.setString(2, sdf.format(end));

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("sum");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public ArrayList<Statistical> getListBieuDo(String year) {
        ArrayList<Statistical> list = new ArrayList<>();
        try {
            String q = """
                       SELECT 
                           MONTH(DATECREATE) AS Tháng,
                           SUM(c.QUANTITY) AS soluong,
                           SUM(a.TOTAL_MONEY) AS sum
                       FROM 
                           ORDERS a
                       JOIN 
                           ORDER_DETAIL b ON a.ID = b.ID_ORDER
                       JOIN 
                           PRODUCT_DETAIL c ON b.ID_PRODUCT_DETAIL = c.ID
                       JOIN 
                           PRODUCT d ON c.ID_PRODUCT = d.ID
                       WHERE 
                           YEAR(DATECREATE) = ?
                       GROUP BY 
                           MONTH(DATECREATE);
                       """;
            PreparedStatement ps = conn.prepareStatement(q);
            ps.setString(1, year);
            ps.execute();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Statistical s = new Statistical(rs.getInt(1),
                        rs.getInt(2), rs.getInt(3));
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public ArrayList<Statistical> getListKhoangDate(Date start, Date end) {
        ArrayList<Statistical> list = new ArrayList<>();
        try {
            String q = """
                       SELECT MONTH(DATECREATE) as Tháng,
                                                     d.NAME,
                                                     b.QUANTITY,
                                                     SUM(a.TOTAL_MONEY) as sum
                                              FROM ORDERS a
                                              JOIN ORDER_DETAIL b ON a.ID = b.ID_ORDER
                                              JOIN PRODUCT_DETAIL c ON b.ID_PRODUCT_DETAIL = c.ID
                                              JOIN PRODUCT d ON c.ID_PRODUCT = d.ID
                       					   where DATECREATE between ? and ?
                                              GROUP BY d.NAME, b.QUANTITY, MONTH(DATECREATE)
                       """;
            PreparedStatement ps = conn.prepareStatement(q);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            ps.setString(1, sdf.format(start));
            ps.setString(2, sdf.format(end));
            ps.execute();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Statistical s = new Statistical(rs.getInt(1),
                        rs.getString(2), rs.getInt(3),
                        rs.getInt(4));
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
