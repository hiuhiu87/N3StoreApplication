/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.dbconnect.DBConnector;
import app.model.Voucher;
import app.repository.VoucherInterface;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 *
 * @author NGUYỄN ĐỨC LƯƠNG
 */
public class VoucherService implements VoucherInterface {

    private Connection conn;

    public VoucherService() {
        this.conn = DBConnector.getConnection();
    }

    @Override
    public ArrayList<Voucher> getList(String name) {
        ArrayList<Voucher> list = new ArrayList<>();
        try {
            String q = "SELECT ID,NAME,CODE,QUANTITY,START_DATE,END_DATE"
                    + ",MIN_VALUE_CONDITION,TYPE,VALUE,MAX_VALUE"
                    + ",DELETED "
                    + "FROM VOUCHER "
                    + "WHERE NAME LIKE '%" + name + "%'";
            PreparedStatement ps = conn.prepareStatement(q);
            ps.execute();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Voucher v = new Voucher(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getInt(4),
                        rs.getDate(5), rs.getDate(6),
                        rs.getFloat(7), rs.getString(8),
                        rs.getFloat(9), rs.getFloat(10),
                        rs.getInt(11));
                list.add(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<Voucher> getListAll() {
        ArrayList<Voucher> list = new ArrayList<>();
        try {
            String q = "SELECT ID,NAME,CODE,QUANTITY,START_DATE,END_DATE"
                    + ",MIN_VALUE_CONDITION,TYPE,VALUE,MAX_VALUE,DELETED "
                    + "FROM VOUCHER ";
            PreparedStatement ps = conn.prepareStatement(q);
            ps.execute();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Voucher v = new Voucher(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getInt(4),
                        rs.getDate(5), rs.getDate(6),
                        rs.getFloat(7), rs.getString(8),
                        rs.getFloat(9), rs.getFloat(10),
                        rs.getInt(11));
                list.add(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<Voucher> getListAllByMinValue(Double totalMoney) {
        ArrayList<Voucher> list = new ArrayList<>();
        try {
            String q = "SELECT ID,NAME,CODE,QUANTITY,START_DATE,END_DATE"
                    + ",MIN_VALUE_CONDITION,TYPE,VALUE,MAX_VALUE,DELETED "
                    + "FROM VOUCHER WHERE MIN_VALUE_CONDITION <= ? AND DELETED = 0";
            PreparedStatement ps = conn.prepareStatement(q);
            ps.setObject(1, totalMoney);
            ps.execute();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Voucher v = new Voucher(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getInt(4),
                        rs.getDate(5), rs.getDate(6),
                        rs.getFloat(7), rs.getString(8),
                        rs.getFloat(9), rs.getFloat(10),
                        rs.getInt(11));
                list.add(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<Voucher> getListPhanTrang(int offset, int limit) {
        ArrayList<Voucher> list = new ArrayList<>();
        try {
            String q = "SELECT ID,NAME,CODE,QUANTITY,START_DATE,END_DATE"
                    + ",MIN_VALUE_CONDITION,TYPE,VALUE,MAX_VALUE,DELETED "
                    + "FROM VOUCHER "
                    + "ORDER BY ID DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
            PreparedStatement ps = conn.prepareStatement(q);
            ps.setObject(1, offset);
            ps.setObject(2, limit);
            ps.execute();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Voucher v = new Voucher(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getInt(4),
                        rs.getDate(5), rs.getDate(6),
                        rs.getFloat(7), rs.getString(8),
                        rs.getFloat(9), rs.getFloat(10),
                        rs.getInt(11));
                list.add(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int count() {
        try {
            int count = 0;
            String q = "SELECT COUNT(*) FROM VOUCHER";
            PreparedStatement ps = conn.prepareStatement(q);
            ps.execute();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int add(Voucher v) {
        try {
            String q = "INSERT INTO VOUCHER(NAME,CODE,QUANTITY,START_DATE"
                    + ",END_DATE,MIN_VALUE_CONDITION,TYPE,VALUE,MAX_VALUE, DELETED) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(q);
            ps.setString(1, v.getTen());
            ps.setString(2, v.getCode());
            ps.setInt(3, v.getQuantity());
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            String star_date = sdf1.format(v.getStart_Date());
            ps.setString(4, star_date);
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            String end_date = sdf2.format(v.getEnd_Date());
            ps.setString(5, end_date);
            ps.setFloat(6, v.getMin_values_condition());
            ps.setString(7, v.getType());
            ps.setFloat(8, v.getValues());
            ps.setFloat(9, v.getMax_values());
            ps.setBoolean(10, false);
            if (ps.executeUpdate() > 0) {
                System.out.println("Thêm thành công");
                return 1;
            }
        } catch (Exception e) {
            System.out.println("" + e.toString());
        }
        return -1;
    }

    public Voucher getListL(String name) {
        try {
            String q = "SELECT ID,NAME,CODE,QUANTITY,START_DATE,END_DATE"
                    + ",MIN_VALUE_CONDITION,TYPE,VALUE,MAX_VALUE"
                    + ",DELETED "
                    + "FROM VOUCHER "
                    + "WHERE NAME LIKE '%" + name + "%'";
            PreparedStatement ps = conn.prepareStatement(q);
            ps.execute();
            ResultSet rs = ps.executeQuery();
            Voucher v = new Voucher();
            while (rs.next()) {
                v.setId(rs.getInt(1));
                v.setTen(rs.getString(2));
                v.setCode(rs.getString(3));
                v.setQuantity(rs.getInt(4));
                v.setStart_Date(rs.getDate(5));
                v.setEnd_Date(rs.getDate(6));
                v.setMin_values_condition(rs.getFloat(7));
                v.setType(rs.getString(8));
                v.setValues(rs.getFloat(9));
                v.setMax_values(rs.getFloat(10));
                v.setDeleted(rs.getInt(11));
            }
            return v;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int remove(String name) {
        try {
            String sql = """
                         UPDATE N3STORESNEAKER.dbo.VOUCHER
                         SET DELETED = ?
                         WHERE NAME = ?;
                         """;
            PreparedStatement stm = conn.prepareStatement(sql);
            Voucher voucher = getListL(name);
            if (voucher != null) {
                if (voucher.getDeleted() != 1) {
                    voucher.setDeleted(1);
                } else {
                    voucher.setDeleted(0);
                }

                stm.setObject(1, voucher.getDeleted());
                stm.setObject(2, voucher.getTen());
            }
            int res = stm.executeUpdate();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int update(Voucher v, int id) {
        try {
            String q = "UPDATE VOUCHER SET NAME=?,CODE=?,QUANTITY=?,START_DATE=?"
                    + ",END_DATE=?,MIN_VALUE_CONDITION=?,TYPE=?,VALUE=?"
                    + ",MAX_VALUE=?,DELETED=? WHERE ID=?";
            PreparedStatement ps = conn.prepareStatement(q);
            ps.setString(1, v.getTen());
            ps.setString(2, v.getCode());
            ps.setInt(3, v.getQuantity());
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            String star_date = sdf1.format(v.getStart_Date());
            ps.setString(4, star_date);
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            String end_date = sdf2.format(v.getEnd_Date());
            ps.setString(5, end_date);
            ps.setFloat(6, v.getMin_values_condition());
            ps.setString(7, v.getType());
            ps.setFloat(8, v.getValues());
            ps.setFloat(9, v.getMax_values());
            ps.setInt(10, v.getDeleted());
            ps.setInt(11, id);
            return ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("" + e.toString());
            return -1;
        }
    }

    private boolean codeExistslnDatabase(String code) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBConnector.getConnection();
            String q = "SELECT COUNT(*) FROM VOUCHER WHERE CODE=?";
            stm = conn.prepareStatement(q);
            stm.setObject(1, code);
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
                rs.close();
            }
            if (conn != null) {
                rs.close();
            }
        }
    }

    public String generateNextModelCode() {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBConnector.getConnection();
            String q = "SELECT MAX(CAST(SUBSTRING(CODE,8,LEN(CODE)-3)AS NVARCHAR(100))) "
                    + "FROM VOUCHER";
            stm = conn.prepareStatement(q);
            rs = stm.executeQuery();
            if (rs.next()) {
                int lastNumber = rs.getInt(1);
                System.out.println(lastNumber);
                if (lastNumber == 0) {
                    return "VOUCHER1";
                }
                while (true) {
                    lastNumber++;
                    String nextCode = "VOUCHER" + lastNumber;
                    if (!codeExistslnDatabase(nextCode)) {
                        return nextCode;
                    }
                }
            } else {
                return "VOUCHER1";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    rs.close();
                }
                if (conn != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public List<Voucher> checkNgay() {
        List<Voucher> list = new ArrayList<>();
        try {
            String q = "SELECT ID , END_DATE FROM VOUCHER";
            PreparedStatement ps = conn.prepareStatement(q);
            ps.execute();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int ID = rs.getInt(1);
                java.util.Date ngayHetHan = rs.getDate(2);
                Voucher vc = new Voucher();
                vc.setEnd_Date(ngayHetHan);
                vc.setId(ID);
                list.add(vc);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateDeleted(boolean deleted, int id) {
        try {
            String q = "UPDATE VOUCHER SET DELETED = ? WHERE ID = ?";

            PreparedStatement ps = conn.prepareStatement(q);
            ps.setBoolean(1, deleted);
            ps.setObject(2, id);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Voucher findById(int id) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = "SELECT ID,NAME,CODE,QUANTITY,START_DATE,END_DATE"
                    + ",MIN_VALUE_CONDITION,TYPE,VALUE,MAX_VALUE,DELETED "
                    + "FROM VOUCHER WHERE ID = ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Voucher v = new Voucher(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getInt(4),
                        rs.getDate(5), rs.getDate(6),
                        rs.getFloat(7), rs.getString(8),
                        rs.getFloat(9), rs.getFloat(10),
                        rs.getInt(11));
                return v;
            }
            return null;
        } catch (Exception e) {
            return null;

        }
    }

    public boolean updateQuantityUse(int id) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         UPDATE N3STORESNEAKER.dbo.VOUCHER
                         SET QUANTITY = QUANTITY - 1
                         WHERE ID = ?;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, id);
            return stm.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateQuantityDontUse(int id) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         UPDATE N3STORESNEAKER.dbo.VOUCHER
                         SET QUANTITY = QUANTITY + 1
                         WHERE ID = ?;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, id);
            return stm.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
        }
    }

}
