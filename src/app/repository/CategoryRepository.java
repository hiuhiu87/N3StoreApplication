/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.repository;

import app.dbconnect.DBConnector;
import app.model.Category;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class CategoryRepository {

    public List<Category> getAll() {
        List<Category> list = new ArrayList<>();
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         SELECT ID, NAME, DELETED
                         FROM N3STORESNEAKER.dbo.CATEGORY;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt(1));
                category.setName(rs.getString(2));
                category.setDeleted(rs.getBoolean(3));
                list.add(category);
            }
            return list;
        } catch (Exception e) {
            return list;
        }
    }

    public int addCategory(Category category) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         INSERT INTO N3STORESNEAKER.dbo.CATEGORY
                         (ID, NAME, DELETED)
                         VALUES(?, ?);
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, category.getName());
            stm.setObject(2, category.getDeleted());
            int res = stm.executeUpdate();
            return res;
        } catch (Exception e) {
            return 0;
        }
    }

    public int updateCategory(Integer id, Category category) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         UPDATE N3STORESNEAKER.dbo.CATEGORY
                         SET NAME = ?, DELETED = ?
                         WHERE ID= ?;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, category.getName());
            stm.setObject(2, category.getName());
            stm.setObject(3, category.getName());
            int res = stm.executeUpdate();
            return res;
        } catch (Exception e) {
            return 0;
        }
    }

    public Category findByName(String name) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         SELECT ID, NAME, DELETED
                         FROM N3STORESNEAKER.dbo.CATEGORY
                         WHERE NAME = ?;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, name);
            ResultSet rs = stm.executeQuery();
            Category category = new Category();
            while (rs.next()) {
                category.setId(rs.getInt(1));
                category.setName(rs.getString(2));
                category.setDeleted(rs.getBoolean(3));
            }
            return category;
        } catch (Exception e) {
            return null;
        }
    }

}
