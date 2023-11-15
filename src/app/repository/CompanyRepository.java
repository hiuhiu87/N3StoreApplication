/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.repository;

import app.dbconnect.DBConnector;
import app.model.Company;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class CompanyRepository implements CrudRepository<Company> {

    @Override
    public List<Company> getAll() {
        try (Connection con = DBConnector.getConnection()) {
            List<Company> list = new ArrayList<>();
            String sql = """
                         SELECT
                         	ID,
                         	NAME,
                         	DELETED
                         FROM
                         	N3STORESNEAKER.dbo.BRAND
                         ORDER BY ID DESC;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Company companyGet = new Company();
                companyGet.setId(rs.getInt(1));
                companyGet.setName(rs.getString(2));
                companyGet.setDeleted(rs.getBoolean(3));
                list.add(companyGet);
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
                         UPDATE N3STORESNEAKER.dbo.BRAND
                         SET DELETED = ?
                         WHERE NAME = ?;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            Company company = findByName(name);
            if (company != null) {
                if (company.getDeleted() != true) {
                    company.setDeleted(Boolean.TRUE);
                } else {
                    company.setDeleted(Boolean.FALSE);
                }

                stm.setObject(1, company.getDeleted());
                stm.setObject(2, company.getName());
            }
            int res = stm.executeUpdate();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int add(Company t) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         INSERT INTO N3STORESNEAKER.dbo.BRAND
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
    public int update(Integer id, Company t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Company findByName(String name) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         SELECT ID, NAME, DELETED
                         FROM N3STORESNEAKER.dbo.BRAND
                         WHERE NAME = ?;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, name);
            ResultSet rs = stm.executeQuery();
            Company company = new Company();
            while (rs.next()) {
                company.setId(rs.getInt(1));
                company.setName(rs.getString(2));
                company.setDeleted(rs.getBoolean(3));
            }
            return company;
        } catch (Exception e) {
            return null;
        }
    }

}
