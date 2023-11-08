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
public class ProductRepository implements CrudRepository<Product> {

    public List<Product> getAll() {
        List<Product> list = new ArrayList<>();
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         SELECT ID, CODE, ID_CATEGORY, NAME, ID_BRAND, DELETED
                         FROM N3STORESNEAKER.dbo.PRODUCT
                         ORDER BY ID DESC;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt(1));
                product.setCode(rs.getString(2));
                product.setIdCategory(rs.getInt(3));
                product.setName(rs.getString(4));
                product.setIdBrand(rs.getInt(5));
                product.setDeleted(rs.getBoolean(6));
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
                         	b.NAME,
                         	COUNT(pd.id) AS QUANTITY,
                         	p.DELETED
                         FROM
                         	N3STORESNEAKER.dbo.PRODUCT p
                         LEFT JOIN CATEGORY c on
                         	p.ID_CATEGORY = c.ID
                         LEFT JOIN BRAND b on
                         	p.ID_BRAND = b.ID
                         LEFT JOIN PRODUCT_DETAIL pd on
                         	p.id = pd.ID_PRODUCT
                         GROUP BY p.CODE, p.NAME, c.NAME, p.DELETED, b.NAME;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                ProductResponse product = new ProductResponse();
                product.setCode(rs.getString(1));
                product.setName(rs.getString(2));
                product.setCategory(rs.getString(3));
                product.setCompany(rs.getString(4));
                product.setQuantity(rs.getInt(5));
                product.setDeleted(rs.getBoolean(6));
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
                         (CODE, ID_CATEGORY, NAME, ID_BRAND, DELETED)
                         VALUES(?, ?, ?, ?, ?);
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, product.getCode());
            stm.setObject(2, product.getIdCategory());
            stm.setObject(3, product.getName());
            stm.setObject(4, product.getIdBrand());
            stm.setObject(5, product.getDeleted());
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
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         SELECT ID, CODE, ID_CATEGORY, NAME, ID_BRAND, DELETED
                         FROM N3STORESNEAKER.dbo.PRODUCT;
                         WHERE NAME = ?;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, name);
            ResultSet rs = stm.executeQuery();
            Product product = new Product();
            while (rs.next()) {
                product.setId(rs.getInt(1));
                product.setCode(rs.getString(2));
                product.setIdCategory(rs.getInt(3));
                product.setName(rs.getString(4));
                product.setIdBrand(rs.getInt(5));
                product.setDeleted(rs.getBoolean(6));
            }
            return product;
        } catch (Exception e) {
            return null;
        }
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
