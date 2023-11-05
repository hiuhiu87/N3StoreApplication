/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.repository;

import app.dbconnect.DBConnector;
import app.model.OderDetail;
import app.model.Oders;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author H.Long
 */
public class OderDetailRepository {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public List<OderDetail> getAllOdersDetails() {
        List<OderDetail> listOdersDetails = new ArrayList<>();
        String sql = """
                        SELECT
                         O.CODE,
                         CUST.FULLNAME AS NAMECUSTOMER,
                         EMP.FULLNAME AS NAMEEMPLOYEE,
                         P.NAME AS NAMEPRODUCT,
                         O.PAYMENT_METHOD,
                         OD.TOTAL_MONEY,
                         OD.PRICE,
                         OD.QUANTITY,
                         O.DATECREATE,
                         V.NAME AS VOUCHER
                     FROM
                         ORDER_DETAIL OD
                     JOIN CUSTOMER CUST ON OD.ID_ORDER = CUST.ID
                     JOIN EMPLOYEE EMP ON OD.ID_ORDER = EMP.ID
                     JOIN PRODUCT P ON OD.ID_PRODUCT_DETAIl = P.ID
                     JOIN ORDERS O ON OD.ID_ORDER = O.ID
                     JOIN VOUCHER V ON O.ID_VOUCHER = V.ID;
                     """;
        try {
            con = DBConnector.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                OderDetail odersDetail = new OderDetail(
                        rs.getString(1),
                         rs.getString(2),
                         rs.getString(3),
                         rs.getString(4),
                         rs.getString(5),
                         rs.getDouble(6),
                         rs.getDouble(7),
                         rs.getInt(8),
                         rs.getDate(9),
                         rs.getString(10)
                );
                listOdersDetails.add(odersDetail);
            }
            return listOdersDetails;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
