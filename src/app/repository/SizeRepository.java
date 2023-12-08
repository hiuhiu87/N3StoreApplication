/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.repository;

import app.model.Size;
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
public class SizeRepository implements CrudRepository<Size> {

    @Override
    public List<Size> getAll() {
        try (Connection con = DBConnector.getConnection()) {
            List<Size> list = new ArrayList<>();
            String sql = """
                         SELECT
                         	ID,
                         	NAME,
                         	DELETED
                         FROM
                         	N3STORESNEAKER.dbo.SIZE
                         ORDER BY ID DESC;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Size colerGet = new Size();
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
                         UPDATE N3STORESNEAKER.dbo.SIZE
                         SET DELETED = ?
                         WHERE NAME = ?;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            Size size = findByName(name);
            if (size != null) {
                if (size.getDeleted() != true) {
                    size.setDeleted(Boolean.TRUE);
                } else {
                    size.setDeleted(Boolean.FALSE);
                }

                stm.setObject(1, size.getDeleted());
                stm.setObject(2, size.getName());
            }
            int res = stm.executeUpdate();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int add(Size t) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         INSERT INTO N3STORESNEAKER.dbo.SIZE
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
    public int update(Integer id, Size t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Size findByName(String name) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         SELECT ID, NAME, DELETED
                         FROM N3STORESNEAKER.dbo.Size
                         WHERE NAME = ?;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, name);
            ResultSet rs = stm.executeQuery();
            Size size = new Size();
            while (rs.next()) {
                size.setId(rs.getInt(1));
                size.setName(rs.getString(2));
                size.setDeleted(rs.getBoolean(3));
            }
            return size;
        } catch (Exception e) {
            return null;
        }
    }

    public Size findById(int id) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         SELECT ID, NAME, DELETED
                         FROM N3STORESNEAKER.dbo.Size
                         WHERE ID = ?;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, id);
            ResultSet rs = stm.executeQuery();
            Size size = new Size();
            while (rs.next()) {
                size.setId(rs.getInt(1));
                size.setName(rs.getString(2));
                size.setDeleted(rs.getBoolean(3));
            }
            return size;
        } catch (Exception e) {
            return null;
        }
    }

}
