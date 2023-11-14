/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.repository;

import app.dbconnect.DBConnector;
import app.model.Oders;
import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author H.Long
 */
public class OderRepository {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public List<Oders> getAllOders() {
        List<Oders> listOders = new ArrayList<>();
        String sql = """
                     SELECT
                         O.ID AS idOrder,
                         O.CODE AS code,
                         C.FULLNAME AS nameCustomer,
                         E.FULLNAME AS nameEmployee,
                         O.PHONE_NUMBER AS phoneNumber,
                         O.PAYMENT_METHOD AS paymentMethod,
                         O.CUSTOMERMONEY AS customerMoney,
                         O.TOTAL_MONEY AS totalMoney,
                         O.MONEY_REDUCED AS moneyReduce,
                         O.DATECREATE AS dateCreateDate,
                         O.STATUS AS status,
                         O.NOTE AS note,
                         O.DELETED AS deleted
                     FROM
                         ORDERS O
                     JOIN CUSTOMER C ON O.ID_CUSTOMER = C.ID
                     JOIN EMPLOYEE E ON O.ID_EMPLOYEE = E.ID;
                     """;
        try {
            con = DBConnector.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Oders oders = new Oders(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getDouble(7),
                        rs.getDouble(8),
                        rs.getDouble(9),
                        rs.getDate(10),
                        rs.getInt(11),
                        rs.getString(12),
                        rs.getBoolean(13)
                );
                listOders.add(oders);
            }
            return listOders;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Oders> getPaginatedOders(int offset, int limit) {
        List<Oders> listOdersPhanTrang = new ArrayList<>();

        String sql = """
                               SELECT
                                      O.ID AS idOrder,
                                      O.CODE AS code,
                                      C.FULLNAME AS nameCustomer,
                                      E.FULLNAME AS nameEmployee,
                                      O.PHONE_NUMBER AS phoneNumber,
                                      O.PAYMENT_METHOD AS paymentMethod,
                                      O.CUSTOMERMONEY AS customerMoney,
                                      O.TOTAL_MONEY AS totalMoney,
                                      O.MONEY_REDUCED AS moneyReduce,
                                      O.DATECREATE AS dateCreateDate,
                                      O.STATUS AS status,
                                      O.NOTE AS note,
                                      O.DELETED AS deleted
                                  FROM
                                      ORDERS O
                                  JOIN CUSTOMER C ON O.ID_CUSTOMER = C.ID
                                  JOIN EMPLOYEE E ON O.ID_EMPLOYEE = E.ID
                                  ORDER BY O.ID
                                  OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
                 """;
        try {
            con = DBConnector.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, offset);
            ps.setObject(2, limit);
            rs = ps.executeQuery();

            while (rs.next()) {
                Oders oders = new Oders(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getDouble(7),
                        rs.getDouble(8),
                        rs.getDouble(9),
                        rs.getDate(10),
                        rs.getInt(11),
                        rs.getString(12),
                        rs.getBoolean(13)
                );
                listOdersPhanTrang.add(oders);
            }
            return listOdersPhanTrang;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int countOder() {
        String sql = """
                   SELECT COUNT(*) FROM ORDERS
                   """;
        int count = 0;
        try {
            con = DBConnector.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                count = rs.getInt(1);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<Oders> getAllListByStatus(int status) {
        List<Oders> listOders = new ArrayList<>();
        String sql = """
                     SELECT
                         O.ID AS idOrder,
                         O.CODE AS code,
                         C.FULLNAME AS nameCustomer,
                         E.FULLNAME AS nameEmployee,
                         O.PHONE_NUMBER AS phoneNumber,
                         O.PAYMENT_METHOD AS paymentMethod,
                         O.CUSTOMERMONEY AS customerMoney,
                         O.TOTAL_MONEY AS totalMoney,
                         O.MONEY_REDUCED AS moneyReduce,
                         O.DATECREATE AS dateCreateDate,
                         O.STATUS AS status,
                         O.NOTE AS note,
                         O.DELETED AS deleted
                     FROM
                         ORDERS O
                     JOIN CUSTOMER C ON O.ID_CUSTOMER = C.ID
                     JOIN EMPLOYEE E ON O.ID_EMPLOYEE = E.ID
                     WHERE O.STATUS = ?
                     """;
        try {
            con = DBConnector.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, status);
            rs = ps.executeQuery();

            while (rs.next()) {
                Oders oders = new Oders(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getDouble(7),
                        rs.getDouble(8),
                        rs.getDouble(9),
                        rs.getDate(10),
                        rs.getInt(11),
                        rs.getString(12),
                        rs.getBoolean(13)
                );
                listOders.add(oders);
            }
            return listOders;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void importDataFromExcel(String file) {
        try ( FileInputStream fileInputStream = new FileInputStream(new File(file))) {
            Workbook workBook = WorkbookFactory.create(fileInputStream);
            Sheet sheet = workBook.getSheetAt(0);

            String sql = """
             INSERT INTO [dbo].[ORDERS]
                        ([CODE]
                        ,[MONEY_REDUCED]
                        ,[PAYMENT_METHOD]
                        ,[NOTE]
                        ,[PHONE_NUMBER]
                        ,[TOTAL_MONEY]
                        ,[STATUS]
                        ,[DELETED]
                        ,[ID_EMPLOYEE]
                        ,[ID_VOUCHER]
                        ,[DATECREATE]
                        ,[CUSTOMERMONEY])
                  VALUES
                        (?,?,?,?,?,?,?,?,?,?,?,?)
             """;

            con = DBConnector.getConnection();
            ps = con.prepareStatement(sql);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                System.out.println(String.valueOf(getStringCellValue(row.getCell(1))));
                ps.setString(1, String.valueOf(getStringCellValue(row.getCell(1))));
                ps.setString(2, String.valueOf(getStringCellValue(row.getCell(2))));
                ps.setString(3, String.valueOf(getStringCellValue(row.getCell(3))));
                ps.setString(4, String.valueOf(getStringCellValue(row.getCell(4))));
                ps.setString(5, String.valueOf(getStringCellValue(row.getCell(5))));
                ps.setDouble(6, Double.parseDouble(getStringCellValue(row.getCell(6))));
                ps.setDouble(7, Double.parseDouble(getStringCellValue(row.getCell(7))));
                ps.setDouble(8, Double.parseDouble(getStringCellValue(row.getCell(8))));
                ps.setTimestamp(9, new Timestamp(row.getCell(9).getDateCellValue().getTime()));
                ps.setDouble(10, Double.parseDouble(getStringCellValue(row.getCell(8))));
                String nameEmployee =  String.valueOf(getStringCellValue(row.getCell(3)));
                String nameVoucher = String.valueOf( getStringCellValue(row.getCell(4)));
//                int orderId = (int) row.getCell(0).getNumericCellValue();

//                if (!isEmployeeExists(nameEmployee)) {
//                    System.out.println("Nhân viên không tồn tại, vui lòng kiểm tra lại ở dòng " + i);
//                    continue;
//                }
//
//                if (!isVoucherExists(nameVoucher)) {
//                    System.out.println("Voucher không tồn tại, vui lòng kiểm tra lại ở dòng " + i);
//                    continue;
//                }

//                if (isOderExists(orderId)) {
//                    System.out.println("Đơn hàng đã tồn tại, vui lòng kiểm tra lại ở dòng " + i);
//                    continue;
//                }
                ps.setString(11, nameEmployee);
                ps.setString(12, nameVoucher);
                
                ps.executeUpdate();
            }
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getStringCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            default:
                return null;
        }
    }

    private boolean isEmployeeExists(String nameEmployee) throws SQLException {
        String employeeCheckSql = "SELECT * FROM EMPLOYEE WHERE [FULLNAME] = ?";
        try {
            con = DBConnector.getConnection();
            ps = con.prepareStatement(employeeCheckSql);
            ps.setString(1, nameEmployee);
            rs = ps.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isVoucherExists(String nameVoucher) throws SQLException {
        String voucherCheckSql = "SELECT * FROM VOUCHER WHERE [NAME] = ?";
        try {
            con = DBConnector.getConnection();
            ps = con.prepareStatement(voucherCheckSql);
            ps.setString(1, nameVoucher);
            rs = ps.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isOderExists(int idOder) {
        String sql = """
                     SELECT * FROM ORDERS WHERE ID = ?
                     """;
        try {
            con = DBConnector.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idOder);
            rs = ps.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
