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
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class ProductRepository {

    public List<Product> getAllProducts() {
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
                         	P.NAME,
                         	C.NAME,
                         	DELETED
                         FROM
                         	N3STORESNEAKER.dbo.PRODUCT P
                         JOIN CATEGORY c on p.ID_CATEGORY = c.ID;
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

    public boolean addProduct(Product product) {
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
            return res > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
