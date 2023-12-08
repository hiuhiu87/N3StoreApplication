/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.dbconnect.DBConnector;
import app.model.KhachHang;
import app.model.Voucher;
import app.response.ProductBoughtCustomerResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ACER
 */
public class KhachHang_Service {

    Connection con = null;
    PreparedStatement ps = null;
    String sql = null;
    ResultSet rs = null;

    public List<KhachHang> getAll() {
        ArrayList<KhachHang> list = new ArrayList<>();
        try (Connection conn = DBConnector.getConnection()) {
            String q = """
                       SELECT
                       	ID,
                       	FULLNAME,
                       	EMAIL,
                       	PHONE_NUMBER,
                       	Address,
                       	BIRTHDATE,
                       	CODE
                       FROM
                       	CUSTOMER
                       WHERE CODE != 'KH0'
                       ORDER BY ID DESC 
                       """;
            PreparedStatement ps1 = conn.prepareStatement(q);
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()) {
                KhachHang s = new KhachHang();
                s.setId(rs1.getInt(1));
                s.setFullName(rs1.getString(2));
                s.setEmail(rs1.getString(3));
                s.setPhoneNumber(rs1.getString(4));
                s.setAddress(rs1.getString(5));
                s.setBirthDate(rs1.getDate(6));
                s.setCode(rs1.getString(7));
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<KhachHang> getListPhanTrang(int offset, int limit) {
        ArrayList<KhachHang> list = new ArrayList<>();
        try (Connection conn = DBConnector.getConnection()) {
            String q = """
                       SELECT
                       	ID,
                       	FULLNAME,
                       	EMAIL,
                       	PHONE_NUMBER,
                       	Address,
                       	BIRTHDATE,
                       	CODE
                       FROM
                       	CUSTOMER
                       WHERE CODE != 'KH0'
                       ORDER BY ID DESC 
                       OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
                       """;
            PreparedStatement ps = conn.prepareStatement(q);
            ps.setObject(1, offset);
            ps.setObject(2, limit);
            ps.execute();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                KhachHang s = new KhachHang();
                s.setId(rs.getInt(1));
                s.setFullName(rs.getString(2));
                s.setEmail(rs.getString(3));
                s.setPhoneNumber(rs.getString(4));
                s.setAddress(rs.getString(5));
                s.setBirthDate(rs.getDate(6));
                s.setCode(rs.getString(7));
                list.add(s);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int count() {
        try (Connection conn = DBConnector.getConnection()) {
            int count = 0;
            String q = "SELECT COUNT(*) FROM CUSTOMER";
            PreparedStatement ps1 = conn.prepareStatement(q);
            ResultSet rs = ps1.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int addKhachHang(KhachHang s) {

        try (Connection conn = DBConnector.getConnection()) {
            String sql1 = "INSERT INTO CUSTOMER(FULLNAME, EMAIL, PHONE_NUMBER, Address, BIRTHDATE, CODE) VALUES(?,?,?,?,?,?)";
            PreparedStatement ps1 = conn.prepareStatement(sql1);

            ps1.setObject(1, s.getFullName());
            ps1.setObject(2, s.getEmail());

            ps1.setObject(3, s.getPhoneNumber());
            ps1.setObject(4, s.getAddress());
            ps1.setObject(5, s.getBirthDate());
            ps1.setObject(6, s.getCode());
            return ps1.executeUpdate();
            //insert delete, update:executeUpdate()
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int updateSV(KhachHang s, String code) {
        try (Connection conn = DBConnector.getConnection()) {
            String sql1 = "update CUSTOMER set FULLNAME=?,EMAIL=?,PHONE_NUMBER=?,Address=?,BIRTHDATE=? where CODE=?";
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ps1.setObject(1, s.getFullName());
            ps1.setObject(2, s.getEmail());

            ps1.setObject(3, s.getPhoneNumber());
            ps1.setObject(4, s.getAddress());
            ps1.setObject(5, s.getBirthDate());
            ps1.setObject(6, code);
            return ps1.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public KhachHang findById(int id) {
        try (Connection con = DBConnector.getConnection()) {
            String sql1 = """
                         SELECT ID, FULLNAME, EMAIL, BIRTHDATE, GENDER, DELETED, Address, PHONE_NUMBER
                         FROM N3STORESNEAKER.dbo.CUSTOMER
                         WHERE ID = ?;
                         """;
            PreparedStatement stm = con.prepareStatement(sql1);
            stm.setObject(1, id);
            ResultSet rs1 = stm.executeQuery();
            KhachHang khachHang = new KhachHang();
            while (rs1.next()) {
                khachHang.setId(rs1.getInt(1));
                khachHang.setFullName(rs1.getString(2));
                khachHang.setEmail(rs1.getString(3));
                khachHang.setBirthDate(rs1.getDate(4));
                khachHang.setGender(rs1.getBoolean(5));
                khachHang.setDeleted(rs1.getBoolean(6));
                khachHang.setAddress(rs1.getString(7));
                khachHang.setPhoneNumber(rs1.getString(8));
            }
            return khachHang;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public KhachHang findByCode(String code) {
        try (Connection conn = DBConnector.getConnection()) {
            String sql1 = """
                         SELECT ID, FULLNAME, EMAIL, BIRTHDATE, GENDER, DELETED, Address, PHONE_NUMBER
                         FROM N3STORESNEAKER.dbo.CUSTOMER
                         WHERE CODE = ?;
                         """;
            PreparedStatement stm = conn.prepareStatement(sql1);
            stm.setObject(1, code);
            ResultSet rs1 = stm.executeQuery();
            KhachHang khachHang = new KhachHang();
            while (rs1.next()) {
                khachHang.setId(rs1.getInt(1));
                khachHang.setFullName(rs1.getString(2));
                khachHang.setEmail(rs1.getString(3));
                khachHang.setBirthDate(rs1.getDate(4));
                khachHang.setGender(rs1.getBoolean(5));
                khachHang.setDeleted(rs1.getBoolean(6));
                khachHang.setAddress(rs1.getString(7));
                khachHang.setPhoneNumber(rs1.getString(8));
            }
            return khachHang;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String generateNextModelCode() {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            conn = DBConnector.getConnection();
            String sql = "SELECT MAX(CAST(SUBSTRING(CODE, 3, LEN(CODE) - 2) AS INT)) FROM CUSTOMER WHERE CODE LIKE 'KH%'";
            stm = conn.prepareStatement(sql);
            rs = stm.executeQuery();

            if (rs.next()) {
                int lastNumber = rs.getInt(1);
                System.out.println(lastNumber);

                if (lastNumber == 0) {
                    return "KH1";
                }

                while (true) {
                    lastNumber++;
                    String nextCode = "KH" + lastNumber;
                    if (!codeExistsInDatabase(nextCode)) {
                        return nextCode;
                    }
                }
            } else {
                return "KH1";
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

    private boolean codeExistsInDatabase(String code) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            conn = DBConnector.getConnection();
            String sql = "SELECT COUNT(*) FROM CUSTOMER WHERE CODE = ?";
            stm = conn.prepareStatement(sql);
            stm.setString(1, code);
            rs = stm.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }

            return false;
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (stm != null) {
                stm.close();
            }

            if (conn != null) {
                conn.close();
            }
        }
    }

    public KhachHang findKhachHangLe() {
        try (Connection conn = DBConnector.getConnection()) {
            String sql1 = """
                         SELECT ID, FULLNAME, EMAIL, BIRTHDATE, GENDER, DELETED, Address, PHONE_NUMBER, CODE
                         FROM N3STORESNEAKER.dbo.CUSTOMER
                         WHERE CODE = 'KH0';
                         """;
            PreparedStatement stm = conn.prepareStatement(sql1);
            ResultSet rs = stm.executeQuery();
            KhachHang khachHang = new KhachHang();
            while (rs.next()) {
                khachHang.setId(rs.getInt(1));
                khachHang.setFullName(rs.getString(2));
                khachHang.setEmail(rs.getString(3));
                khachHang.setBirthDate(rs.getDate(4));
                khachHang.setGender(rs.getBoolean(5));
                khachHang.setDeleted(rs.getBoolean(6));
                khachHang.setAddress(rs.getString(7));
                khachHang.setPhoneNumber(rs.getString(8));
                khachHang.setCode(rs.getString(9));
            }
            return khachHang;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int createKhachLe() {

        try (Connection conn = DBConnector.getConnection()) {
            String sql1 = "INSERT INTO CUSTOMER(FULLNAME, EMAIL, PHONE_NUMBER, Address, BIRTHDATE, CODE) VALUES(?,?,?,?,?,?)";
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            KhachHang s = new KhachHang();
            ps1.setObject(1, "Khách Lẻ");
            ps1.setObject(2, "");

            ps1.setObject(3, "");
            ps1.setObject(4, "");
            ps1.setObject(5, "");
            ps1.setObject(6, "KH0");
            return ps1.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<ProductBoughtCustomerResponse> getAllProductBoughtByCustomer(String customerCode) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         SELECT
                         	o.CODE,
                         	pd.CODE,
                         	p.NAME,
                         	s.NAME,
                         	m.NAME ,
                         	c2.NAME ,
                         	s2.NAME
                         FROM
                         	N3STORESNEAKER.dbo.ORDERS o
                         JOIN N3STORESNEAKER.dbo.ORDER_DETAIL od on
                         	o.ID = od.ID_ORDER
                         JOIN N3STORESNEAKER.dbo.PRODUCT_DETAIL pd on
                         	od.ID_PRODUCT_DETAIl = pd.ID
                         JOIN N3STORESNEAKER.dbo.PRODUCT p on
                         	pd.ID_PRODUCT = p.ID
                         JOIN N3STORESNEAKER.dbo.[SIZE] s on
                         	pd.ID_SIZE = s.ID
                         JOIN N3STORESNEAKER.dbo.MATERIAL m on
                         	pd.ID_MATERIAL = m.ID
                         JOIN N3STORESNEAKER.dbo.COLOR c2 on
                         	pd.ID_COLOR = c2.ID
                         JOIN N3STORESNEAKER.dbo.SOLE s2 on
                         	pd.ID_SOLE = s2.ID
                         JOIN N3STORESNEAKER.dbo.CUSTOMER c on
                         	o.ID_CUSTOMER = c.ID
                         WHERE c.CODE = ?
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, customerCode);
            List<ProductBoughtCustomerResponse> list = new ArrayList<>();
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                ProductBoughtCustomerResponse boughtCustomerResponse = new ProductBoughtCustomerResponse();
                boughtCustomerResponse.setOrderCode(rs.getString(1));
                boughtCustomerResponse.setProductDetailCode(rs.getString(2));
                boughtCustomerResponse.setNameProduct(rs.getString(3));
                boughtCustomerResponse.setNameSize(rs.getString(4));
                boughtCustomerResponse.setNameMaterial(rs.getString(5));
                boughtCustomerResponse.setNameColor(rs.getString(6));
                boughtCustomerResponse.setNameSole(rs.getString(7));
                list.add(boughtCustomerResponse);
            }
            return list;
        } catch (Exception e) {
            return null;
        }
    }

}
