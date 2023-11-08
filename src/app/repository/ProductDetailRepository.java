/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.repository;

import app.dbconnect.DBConnector;
import app.model.ProductDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ProductDetailRepository implements CrudRepository<ProductDetail> {

    @Override
    public List<ProductDetail> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int add(ProductDetail t) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         INSERT
                         	INTO
                         	N3STORESNEAKER.dbo.PRODUCT_DETAIL
                         (ID_SIZE,
                         ID_PRODUCT,
                         ID_MATERIAL,
                         ID_COLOR,
                         ID_SOLE,
                         IMAGE_PRODUCT,
                         SELL_PRICE,
                         QUANTITY,
                         WEIGHT,
                         DELETED,
                         ORIGIN_PRICE)
                         VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, t.getIdSize());
            stm.setObject(2, t.getIdProduct());
            stm.setObject(3, t.getIdMaterial());
            stm.setObject(4, t.getIdColor());
            stm.setObject(5, t.getIdSole());
            stm.setObject(6, t.getImageProduct());
            stm.setObject(7, t.getSellPrice());
            stm.setObject(8, t.getQuantity());
            stm.setObject(9, t.getWeight());
            stm.setObject(10, t.getDeleted());
            stm.setObject(11, t.getOriginPrice());

            int res = stm.executeUpdate();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int update(Integer id, ProductDetail t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ProductDetail findByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
