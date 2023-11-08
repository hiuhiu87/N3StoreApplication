/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.repository;

import app.dbconnect.DBConnector;
import app.model.Product;
import app.response.ProductResponse;
import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class ProductRepository implements CrudRepository<Product>{

    public List<Product> getAll() {
        List<Product> list = new ArrayList<>();
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         SELECT ID, CODE, ID_CATEGORY, NAME, DELETED
                         FROM N3STORESNEAKER.dbo.PRODUCT;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt(1));
                product.setCode(rs.getString(2));
                product.setIdCategory(rs.getInt(3));
                product.setName(rs.getString(4));
                product.setDeleted(rs.getBoolean(5));
                list.add(product);
            }
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    public List<ProductResponse> getAllProductsView() {
        List<ProductResponse> list = new ArrayList<>();
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         SELECT
                                p.CODE,
                         	p.NAME,
                         	c.NAME,
                         	p.DELETED
                         FROM
                         	N3STORESNEAKER.dbo.PRODUCT p
                         JOIN CATEGORY c on
                         	p.ID_CATEGORY = c.ID;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                ProductResponse product = new ProductResponse();
                product.setName(rs.getString(1));
                product.setCategory(rs.getString(2));
                product.setDeleted(rs.getBoolean(3));
                list.add(product);
            }
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    public int add(Product product) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         INSERT INTO N3STORESNEAKER.dbo.PRODUCT
                         (CODE, ID_CATEGORY, NAME, DELETED)
                         VALUES(?, ?, ?, ?);
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, product.getCode());
            stm.setObject(2, product.getIdCategory());
            stm.setObject(3, product.getName());
            stm.setObject(4, product.getDeleted());
            int res = stm.executeUpdate();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int update(Integer id, Product t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Product findByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public String generateNextModelCode() {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            conn = DBConnector.getConnection();
            String sql = "SELECT MAX(CODE) FROM PRODUCT";
            stm = conn.prepareStatement(sql);
            rs = stm.executeQuery();

            if (rs.next()) {
                String lastCode = rs.getString(1);
                if (lastCode == null) {
                    return "SP1";
                }
                int lastNumber = Integer.parseInt(lastCode.substring(2));
                int nextNumber = lastNumber + 1;
                String nextCode = "SP" + nextNumber;
                return nextCode;
            } else {
                return "SP1";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (stm != null) {
                    stm.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
