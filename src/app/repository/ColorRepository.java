/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.repository;

import app.dbconnect.DBConnector;
import app.model.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ColorRepository implements CrudRepository<Color> {

    public List<Color> getAll() {
        try (Connection con = DBConnector.getConnection()) {
            List<Color> list = new ArrayList<>();
            String sql = """
                         SELECT
                         	ID,
                         	NAME,
                         	DELETED
                         FROM
                         	N3STORESNEAKER.dbo.COLOR
                         ORDER BY ID DESC;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Color colerGet = new Color();
                colerGet.setId(rs.getInt(1));
                colerGet.setName(rs.getString(2));
                colerGet.setDeleted(rs.getBoolean(3));
                list.add(colerGet);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> getAllName() {
        try (Connection con = DBConnector.getConnection()) {
            List<String> list = new ArrayList<>();
            String sql = """
                         SELECT
                            NAME
                         FROM
                            N3STORESNEAKER.dbo.COLOR
                         WHERE DELETED = 0
                         ORDER BY ID DESC;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                String name = rs.getString(1);
                list.add(name);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int updateStatus(String name) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         UPDATE N3STORESNEAKER.dbo.COLOR
                         SET DELETED = ?
                         WHERE NAME = ?;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            Color color = findByName(name);
            if (color != null) {
                if (color.getDeleted() != true) {
                    color.setDeleted(Boolean.TRUE);
                } else {
                    color.setDeleted(Boolean.FALSE);
                }

                stm.setObject(1, color.getDeleted());
                stm.setObject(2, color.getName());
            }
            int res = stm.executeUpdate();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int add(Color color) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         INSERT INTO N3STORESNEAKER.dbo.COLOR
                         (NAME, DELETED)
                         VALUES(?, ?);
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, color.getName());
            stm.setObject(2, color.getDeleted());
            int res = stm.executeUpdate();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int update(Integer id, Color t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Color findByName(String name) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         SELECT ID, NAME, DELETED
                         FROM N3STORESNEAKER.dbo.COLOR
                         WHERE NAME = ?;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, name);
            ResultSet rs = stm.executeQuery();
            Color color = new Color();
            while (rs.next()) {
                color.setId(rs.getInt(1));
                color.setName(rs.getString(2));
                color.setDeleted(rs.getBoolean(3));
            }
            return color;
        } catch (Exception e) {
            return null;
        }
    }

    public Color findById(int id) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         SELECT ID, NAME, DELETED
                         FROM N3STORESNEAKER.dbo.COLOR
                         WHERE ID = ?;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, id);
            ResultSet rs = stm.executeQuery();
            Color color = new Color();
            while (rs.next()) {
                color.setId(rs.getInt(1));
                color.setName(rs.getString(2));
                color.setDeleted(rs.getBoolean(3));
            }
            return color;
        } catch (Exception e) {
            return null;
        }
    }

}
