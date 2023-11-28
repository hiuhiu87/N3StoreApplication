/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.repository;

import app.dbconnect.DBConnector;
import app.model.OrderDetail;
import app.model.Order;
import app.request.UpdateOrderDetailExist;
import app.response.CartResponse;
import app.response.OrderDetailResponse;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author H.Long
 */
public class OrderDetailRepository {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public List<OrderDetail> getAllOdersDetails(String orderCode) {
        List<OrderDetail> listOrdersDetails = new ArrayList<>();
        String sql = """
                     SELECT
                     	od.ID,
                     	ID_PRODUCT_DETAIl,
                     	QUANTITY,
                     	PRICE,
                     	od.TOTAL_MONEY,
                     	ID_ORDER
                     FROM
                     	N3STORESNEAKER.dbo.ORDER_DETAIL od
                     JOIN N3STORESNEAKER.dbo.ORDERS o on od.ID_ORDER = o.ID
                     WHERE o.CODE = ?
                     ORDER by od.ID;
                     """;
        try {
            con = DBConnector.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, orderCode);
            rs = ps.executeQuery();

            while (rs.next()) {
                OrderDetail ordersDetail = new OrderDetail();
                ordersDetail.setId(rs.getInt(1));
                ordersDetail.setIdProductdetail(rs.getInt(2));
                ordersDetail.setQuantity(rs.getInt(3));
                ordersDetail.setPrice(rs.getDouble(4));
                ordersDetail.setTotalMoney(rs.getDouble(5));
                ordersDetail.setIdOrder(rs.getInt(6));
                listOrdersDetails.add(ordersDetail);
            }
            return listOrdersDetails;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<OrderDetailResponse> getAllOdersDetailsPhanTrang(int offset, int limit, String orderCode) {
        List<OrderDetailResponse> listOrdersDetails = new ArrayList<>();
        String sql = """
                     SELECT
                        o.CODE,
                        pd.CODE,
                     	p.NAME,
                     	od.QUANTITY,
                     	PRICE,
                     	od.TOTAL_MONEY
                     FROM
                     	N3STORESNEAKER.dbo.ORDER_DETAIL od
                     JOIN N3STORESNEAKER.dbo.ORDERS o on
                     	od.ID_ORDER = o.ID
                     JOIN N3STORESNEAKER.dbo.PRODUCT_DETAIL pd on
                     	pd.ID = od.ID_PRODUCT_DETAIl
                     JOIN N3STORESNEAKER.dbo.PRODUCT p on
                     	pd.ID_PRODUCT = p.ID
                     WHERE
                     	o.CODE = ?
                     ORDER by
                     	od.ID DESC
                     OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;
                     """;
        try {
            con = DBConnector.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, orderCode);
            ps.setObject(2, offset);
            ps.setObject(3, limit);
            rs = ps.executeQuery();
            while (rs.next()) {
                OrderDetailResponse orderDetailResponse = new OrderDetailResponse();
                orderDetailResponse.setOrderCode(rs.getString(1));
                orderDetailResponse.setProductDetailCode(rs.getString(2));
                orderDetailResponse.setNameProduct(rs.getString(3));
                orderDetailResponse.setQuantity(rs.getInt(4));
                orderDetailResponse.setPrice(rs.getDouble(5));
                orderDetailResponse.setTotalMoney(rs.getDouble(6));
                listOrdersDetails.add(orderDetailResponse);
            }
            return listOrdersDetails;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int countOderDetail() {
        String sql = """
                     SELECT COUNT(*) FROM ORDER_DETAIL
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

    public List<CartResponse> getListCarts(String orderCode) {
        List<CartResponse> cartResponses = new ArrayList<>();
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         SELECT
                         	pd.CODE,
                         	p.NAME,
                         	s.NAME,
                         	m.NAME,
                         	c.NAME,
                         	s2.NAME, 
                         	pd.SELL_PRICE,
                         	od.QUANTITY,
                         	od.TOTAL_MONEY
                         FROM
                         	N3STORESNEAKER.dbo.ORDER_DETAIL od
                         JOIN N3STORESNEAKER.dbo.PRODUCT_DETAIL pd on
                         	od.ID_PRODUCT_DETAIl = pd.ID
                         JOIN N3STORESNEAKER.dbo.PRODUCT p on
                         	pd.ID_PRODUCT = p.ID
                         JOIN N3STORESNEAKER.dbo.ORDERS o on
                         	od.ID_ORDER = o.ID
                         JOIN N3STORESNEAKER.dbo.[SIZE] s on pd.ID_SIZE = s.ID 
                         JOIN N3STORESNEAKER.dbo.MATERIAL m on pd.ID_MATERIAL = m.ID 
                         JOIN N3STORESNEAKER.dbo.COLOR c on pd.ID_COLOR = c.ID 
                         JOIN N3STORESNEAKER.dbo.SOLE s2 on pd.ID_SOLE = s2.ID 
                         WHERE
                         	o.CODE = ?;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, orderCode);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                CartResponse cartResponse = new CartResponse();
                cartResponse.setProductDetailCode(rs.getString(1));
                cartResponse.setNameProduct(rs.getString(2));
                cartResponse.setNameSize(rs.getString(3));
                cartResponse.setNameMaterial(rs.getString(4));
                cartResponse.setNameColor(rs.getString(5));
                cartResponse.setNameSole(rs.getString(6));
                cartResponse.setPrice(rs.getDouble(7));
                cartResponse.setQuantity(rs.getInt(8));
                cartResponse.setTotaMoney(rs.getDouble(9));
                cartResponses.add(cartResponse);
            }
            return cartResponses;
        } catch (Exception e) {
            return null;
        }
    }

    public int addOrderDetail(OrderDetail orderDetail) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         INSERT INTO N3STORESNEAKER.dbo.ORDER_DETAIL
                         (ID_PRODUCT_DETAIl, QUANTITY, PRICE, TOTAL_MONEY, ID_ORDER)
                         VALUES(?, ?, ?, ?, ?);
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, orderDetail.getIdProductdetail());
            stm.setObject(2, orderDetail.getQuantity());
            stm.setObject(3, orderDetail.getPrice());
            stm.setObject(4, orderDetail.getTotalMoney());
            stm.setObject(5, orderDetail.getIdOrder());
            int res = stm.executeUpdate();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int updateOrderDetail(UpdateOrderDetailExist orderDetail, Integer id) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         UPDATE N3STORESNEAKER.dbo.ORDER_DETAIL
                         SET QUANTITY = ?, TOTAL_MONEY = ?
                         WHERE ID = ?;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, orderDetail.getQuantity());
            stm.setObject(2, orderDetail.getTotalMoney());
            stm.setObject(3, id);
            int res = stm.executeUpdate();
            return res;
        } catch (Exception e) {
            return 0;
        }
    }

    public int deleteOrderDetail(int productDetailCode) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         DELETE FROM N3STORESNEAKER.dbo.ORDER_DETAIL
                         WHERE ID_PRODUCT_DETAIl = ?;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, productDetailCode);
            int res = stm.executeUpdate();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getQuantityByProductDetailCode(String code) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         SELECT od.QUANTITY
                         FROM N3STORESNEAKER.dbo.ORDER_DETAIL od
                         JOIN N3STORESNEAKER.dbo.PRODUCT_DETAIL pd on od.ID_PRODUCT_DETAIl = pd.ID 
                         WHERE pd.CODE = ?;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, code);
            int quantity = 0;
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                quantity = rs.getInt(1);
                return quantity;
            }
            return quantity;
        } catch (Exception e) {
            return 0;
        }
    }

    public Double getTotalMoneyOrder(String orderCode) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         SELECT SUM(od.TOTAL_MONEY) 
                         FROM N3STORESNEAKER.dbo.ORDER_DETAIL od 
                         LEFT JOIN N3STORESNEAKER.dbo.ORDERS o on od.ID_ORDER = o.ID 
                         WHERE o.CODE = ?;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, orderCode);
            Double totalMoney = null;
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                totalMoney = rs.getDouble(1);
            }
            return totalMoney;
        } catch (Exception e) {
            return null;
        }
    }

    public int updateQuantityById(int quantity, int id) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         UPDATE N3STORESNEAKER.dbo.ORDER_DETAIL
                         SET QUANTITY = ?
                         WHERE ID = ?;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, quantity);
            stm.setObject(2, id);
            int res = stm.executeUpdate();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int updateQuantityInCart(String orderCode, String productDetailCode, int quantity) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         UPDATE od
                         SET od.QUANTITY = ?
                         FROM N3STORESNEAKER.dbo.ORDER_DETAIL od
                         JOIN N3STORESNEAKER.dbo.PRODUCT_DETAIL pd ON od.ID_PRODUCT_DETAIL = pd.ID
                         JOIN N3STORESNEAKER.dbo.ORDERS o ON od.ID_ORDER = o.ID
                         WHERE pd.CODE = ?
                         AND o.CODE = ?;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, quantity);
            stm.setObject(2, productDetailCode);
            stm.setObject(3, orderCode);
            return stm.executeUpdate();
        } catch (Exception e) {
            return 0;
        }
    }

    public int getQuantityOrderDetail(String orderCode, String productDetailCode) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         select od.QUANTITY  FROM N3STORESNEAKER.dbo.ORDER_DETAIL od 
                            join N3STORESNEAKER.dbo.ORDERS o on od.ID_ORDER = o.ID 
                            JOIN N3STORESNEAKER.dbo.PRODUCT_DETAIL pd on od.ID_PRODUCT_DETAIl = pd.ID 
                            WHERE pd.CODE = ? and o.CODE = ?
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, productDetailCode);
            stm.setObject(2, orderCode);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
