/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.repository;

import app.dbconnect.DBConnector;
import app.model.Material;
import java.util.List;
import app.dbconnect.DBConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class MaterialRepository implements CrudRepository<Material> {

    @Override
    public List<Material> getAll() {
        try (Connection con = DBConnector.getConnection()) {
            List<Material> list = new ArrayList<>();
            String sql = """
                         SELECT
                         	ID,
                         	NAME,
                         	DELETED
                         FROM
                         	N3STORESNEAKER.dbo.MATERIAL
                         ORDER BY ID DESC;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Material colerGet = new Material();
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

    public int updateStatus(String name) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         UPDATE N3STORESNEAKER.dbo.MATERIAL
                         SET DELETED = ?
                         WHERE NAME = ?;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            Material material = findByName(name);
            if (material != null) {
                if (material.getDeleted() != true) {
                    material.setDeleted(Boolean.TRUE);
                } else {
                    material.setDeleted(Boolean.FALSE);
                }

                stm.setObject(1, material.getDeleted());
                stm.setObject(2, material.getName());
            }
            int res = stm.executeUpdate();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int add(Material t) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         INSERT INTO N3STORESNEAKER.dbo.MATERIAL
                         (NAME, DELETED)
                         VALUES(?, ?);
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, t.getName());
            stm.setObject(2, t.getDeleted());
            int res = stm.executeUpdate();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int update(Integer id, Material t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Material findByName(String name) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         SELECT ID, NAME, DELETED
                         FROM N3STORESNEAKER.dbo.MATERIAL
                         WHERE NAME = ?;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, name);
            ResultSet rs = stm.executeQuery();
            Material material = new Material();
            while (rs.next()) {
                material.setId(rs.getInt(1));
                material.setName(rs.getString(2));
                material.setDeleted(rs.getBoolean(3));
            }
            return material;
        } catch (Exception e) {
            return null;
        }
    }

    public Material findById(int id) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         SELECT ID, NAME, DELETED
                         FROM N3STORESNEAKER.dbo.MATERIAL
                         WHERE ID = ?;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, id);
            ResultSet rs = stm.executeQuery();
            Material material = new Material();
            while (rs.next()) {
                material.setId(rs.getInt(1));
                material.setName(rs.getString(2));
                material.setDeleted(rs.getBoolean(3));
            }
            return material;
        } catch (Exception e) {
            return null;
        }
    }
}
