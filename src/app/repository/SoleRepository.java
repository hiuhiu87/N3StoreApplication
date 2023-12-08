/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.repository;

import app.dbconnect.DBConnector;
import app.model.Sole;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class SoleRepository implements CrudRepository<Sole> {

    @Override
    public List<Sole> getAll() {
        try (Connection con = DBConnector.getConnection()) {
            List<Sole> list = new ArrayList<>();
            String sql = """
                         SELECT
                         	ID,
                         	NAME,
                         	DELETED
                         FROM
                         	N3STORESNEAKER.dbo.SOLE
                         ORDER BY ID DESC;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Sole colerGet = new Sole();
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
                            N3STORESNEAKER.dbo.SOLE
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

    @Override
    public int add(Sole t) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         INSERT INTO N3STORESNEAKER.dbo.SOLE
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

    public int updateStatus(String name) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         UPDATE N3STORESNEAKER.dbo.SOLE
                         SET DELETED = ?
                         WHERE NAME = ?;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            Sole sole = findByName(name);
            if (sole != null) {
                if (sole.getDeleted() != true) {
                    sole.setDeleted(Boolean.TRUE);
                } else {
                    sole.setDeleted(Boolean.FALSE);
                }

                stm.setObject(1, sole.getDeleted());
                stm.setObject(2, sole.getName());
            }
            int res = stm.executeUpdate();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int update(Integer id, Sole t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Sole findByName(String name) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         SELECT ID, NAME, DELETED
                         FROM N3STORESNEAKER.dbo.SOLE
                         WHERE NAME = ?;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, name);
            ResultSet rs = stm.executeQuery();
            Sole sole = new Sole();
            while (rs.next()) {
                sole.setId(rs.getInt(1));
                sole.setName(rs.getString(2));
                sole.setDeleted(rs.getBoolean(3));
            }
            return sole;
        } catch (Exception e) {
            return null;
        }
    }

    public Sole findById(int id) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         SELECT ID, NAME, DELETED
                         FROM N3STORESNEAKER.dbo.SOLE
                         WHERE ID = ?;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, id);
            ResultSet rs = stm.executeQuery();
            Sole sole = new Sole();
            while (rs.next()) {
                sole.setId(rs.getInt(1));
                sole.setName(rs.getString(2));
                sole.setDeleted(rs.getBoolean(3));
            }
            return sole;
        } catch (Exception e) {
            return null;
        }
    }

}
