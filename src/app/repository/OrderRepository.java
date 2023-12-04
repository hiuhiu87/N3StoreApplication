/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.repository;

import app.dbconnect.DBConnector;
import app.model.Order;
import app.response.OrderResponse;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author H.Long
 */
public class OrderRepository {

    private final OrderDetailRepository orderDetailRepository = new OrderDetailRepository();
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public int updateStatusOrderToCancel(String orderCode) {
        try (Connection conn = DBConnector.getConnection()) {
            String sql = """
                         UPDATE N3STORESNEAKER.dbo.ORDERS
                         SET STATUS = 3
                         WHERE CODE = ?;
                         """;
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setObject(1, orderCode);
            return stm.executeUpdate();
        } catch (Exception e) {
            return 0;
        }
    }

    public List<Order> getAllOders() {
        List<Order> listOders = new ArrayList<>();
        String sql = """
                     SELECT ID, 
                     CODE, 
                     ID_CUSTOMER, 
                     MONEY_REDUCED, 
                     PAYMENT_METHOD, 
                     PHONE_NUMBER, 
                     TOTAL_MONEY, 
                     STATUS, 
                     DELETED, 
                     ID_EMPLOYEE, 
                     ID_VOUCHER, 
                     DATECREATE, 
                     CUSTOMERMONEY
                     FROM N3STORESNEAKER.dbo.ORDERS;
                     """;
        try {
            con = DBConnector.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Order oders = new Order();
                oders.setId(rs.getInt(1));
                oders.setCode(rs.getString(2));
                oders.setIdCustomer(rs.getInt(3));
                oders.setMoneyReduce(rs.getDouble(4));
                oders.setPaymentMethod(rs.getString(5));
                oders.setPhoneNumber(rs.getString(6));
                oders.setTotalMoney(rs.getDouble(7));
                oders.setStatus(rs.getInt(8));
                oders.setDeleted(rs.getBoolean(9));
                oders.setIdEmployee(rs.getInt(10));
                oders.setIdVoucher(rs.getInt(11));
                oders.setCreateDate(rs.getDate(12));
                oders.setCustomerMoney(rs.getDouble(13));
                listOders.add(oders);
            }
            return listOders;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<OrderResponse> getPaginatedOders(int offset, int limit) {
        List<OrderResponse> listOrderPagnation = new ArrayList<>();

        String sql = """
                               SELECT
                               	O.CODE AS code,
                               	C.FULLNAME AS nameCustomer,
                               	E.FULLNAME AS nameEmployee,
                               	O.PHONE_NUMBER AS phoneNumber,
                               	O.TOTAL_MONEY AS totalMoney,
                               	O.DATECREATE AS dateCreateDate,
                               	O.STATUS AS status
                               FROM
                               	N3STORESNEAKER.dbo.ORDERS O
                               JOIN N3STORESNEAKER.dbo.CUSTOMER C ON
                               	O.ID_CUSTOMER = C.ID
                               JOIN N3STORESNEAKER.dbo.EMPLOYEE E ON
                               	O.ID_EMPLOYEE = E.ID
                               ORDER BY
                               	O.ID
                                OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
                 """;

        try {
            con = DBConnector.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, offset);
            ps.setObject(2, limit);
            rs = ps.executeQuery();

            while (rs.next()) {
                OrderResponse order = new OrderResponse();
                order.setCode(rs.getString(1));
                order.setNameCustomer(rs.getString(2));
                order.setNameEmployee(rs.getString(3));
                order.setPhoneNumber(rs.getString(4));
                order.setTotalMoney(orderDetailRepository.getTotalMoneyOrder(rs.getString(1)));
                order.setCreateDate(rs.getString(6));
                order.setStatus(rs.getInt(7));
                listOrderPagnation.add(order);
            }
            return listOrderPagnation;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<OrderResponse> getAllOrderView() {
        List<OrderResponse> listOrderPagnation = new ArrayList<>();

        String sql = """
                               SELECT TOP 5
                               	O.CODE AS code,
                               	C.FULLNAME AS nameCustomer,
                               	E.FULLNAME AS nameEmployee,
                               	O.PHONE_NUMBER AS phoneNumber,
                               	O.TOTAL_MONEY AS totalMoney,
                               	O.DATECREATE AS dateCreateDate,
                               	O.STATUS AS status
                               FROM
                               	N3STORESNEAKER.dbo.ORDERS O
                               JOIN N3STORESNEAKER.dbo.CUSTOMER C ON
                               	O.ID_CUSTOMER = C.ID
                               JOIN N3STORESNEAKER.dbo.EMPLOYEE E ON
                               	O.ID_EMPLOYEE = E.ID
                               ORDER BY
                               	O.ID DESC
                 """;

        try {
            con = DBConnector.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                OrderResponse order = new OrderResponse();
                order.setCode(rs.getString(1));
                order.setNameCustomer(rs.getString(2));
                order.setNameEmployee(rs.getString(3));
                order.setPhoneNumber(rs.getString(4));
                order.setTotalMoney(orderDetailRepository.getTotalMoneyOrder(rs.getString(1)));
                order.setCreateDate(rs.getString(6));
                order.setStatus(rs.getInt(7));
                listOrderPagnation.add(order);
            }
            return listOrderPagnation;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public OrderResponse getOrderResponseByCode(String code) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                               SELECT
                               	O.CODE AS code,
                               	C.FULLNAME AS nameCustomer,
                               	E.FULLNAME AS nameEmployee,
                               	O.PHONE_NUMBER AS phoneNumber,
                               	O.TOTAL_MONEY AS totalMoney,
                               	O.DATECREATE AS dateCreateDate,
                               	O.STATUS AS status
                               FROM
                               	N3STORESNEAKER.dbo.ORDERS O
                               JOIN N3STORESNEAKER.dbo.CUSTOMER C ON
                               	O.ID_CUSTOMER = C.ID
                               JOIN N3STORESNEAKER.dbo.EMPLOYEE E ON
                               	O.ID_EMPLOYEE = E.ID
                         WHERE O.CODE = ?
                 """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, code);
            OrderResponse orderResponse = new OrderResponse();
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                orderResponse.setCode(rs.getString(1));
                orderResponse.setNameCustomer(rs.getString(2));
                orderResponse.setNameEmployee(rs.getString(3));
                orderResponse.setPhoneNumber(rs.getString(4));
                orderResponse.setTotalMoney(orderDetailRepository.getTotalMoneyOrder(rs.getString(1)));
                orderResponse.setCreateDate(rs.getString(6));
                orderResponse.setStatus(rs.getInt(7));
            }
            return orderResponse;
        } catch (Exception e) {
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

    public List<OrderResponse> getAllListByStatus(int status) {
        List<OrderResponse> listOrderResponse = new ArrayList<>();
        String sql = """
                     SELECT
                     	O.CODE AS code,
                     	C.FULLNAME AS nameCustomer,
                     	E.FULLNAME AS nameEmployee,
                     	O.PHONE_NUMBER AS phoneNumber,
                     	O.TOTAL_MONEY AS totalMoney,
                     	O.DATECREATE AS dateCreateDate,
                     	O.STATUS AS status
                     FROM
                     	N3STORESNEAKER.dbo.ORDERS O
                     JOIN N3STORESNEAKER.dbo.CUSTOMER C ON
                     	O.ID_CUSTOMER = C.ID
                     JOIN N3STORESNEAKER.dbo.EMPLOYEE E ON
                     	O.ID_EMPLOYEE = E.ID
                     WHERE
                     	O.STATUS = ?
                     ORDER BY
                     	O.ID DESC
                     """;
        try {
            con = DBConnector.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, status);
            rs = ps.executeQuery();
            while (rs.next()) {
                OrderResponse order = new OrderResponse();
                order.setCode(rs.getString(1));
                order.setNameCustomer(rs.getString(2));
                order.setNameEmployee(rs.getString(3));
                order.setPhoneNumber(rs.getString(4));
                order.setTotalMoney(orderDetailRepository.getTotalMoneyOrder(rs.getString(1)));
                order.setCreateDate(rs.getString(6));
                order.setStatus(rs.getInt(7));
                listOrderResponse.add(order);
            }
            return listOrderResponse;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

//    public List<Oders> getAllListPrintOder() {
//        String sql = """
//                         SELECT
//                         CUST.FULLNAME AS 'CustomerName',
//                         EMP.FULLNAME AS 'EmployeeName',
//                         ORD.CODE AS 'OrderCode',
//                         ORD.DATECREATE AS 'DateCreated',
//                         DET.QUANTITY AS 'OrderDetailQuantity',
//                         PROD_DETAIL.SELL_PRICE AS 'ProductDetailSellPrice',
//                         DET.TOTAL_MONEY AS 'OrderDetailTotalMoney',
//                         ORD.MONEY_REDUCED AS 'MoneyReducedTotal',
//                         ORD.CUSTOMERMONEY AS 'CustomerMoney',
//                         ORD.PAYMENT_METHOD AS 'PaymentMethod',
//                         PROD.NAME AS 'NameProduct'
//                     FROM
//                         N3STORESNEAKER.dbo.ORDERS ORD
//                     JOIN
//                         N3STORESNEAKER.dbo.CUSTOMER CUST ON ORD.ID_CUSTOMER = CUST.ID
//                     JOIN
//                         N3STORESNEAKER.dbo.EMPLOYEE EMP ON ORD.ID_EMPLOYEE = EMP.ID
//                     JOIN
//                         N3STORESNEAKER.dbo.ORDER_DETAIL DET ON ORD.ID = DET.ID_ORDER
//                     JOIN
//                         N3STORESNEAKER.dbo.PRODUCT_DETAIL PROD_DETAIL ON DET.ID_PRODUCT_DETAIL = PROD_DETAIL.ID
//                     JOIN
//                         N3STORESNEAKER.dbo.PRODUCT PROD ON PROD_DETAIL.ID_PRODUCT = PROD.ID;
//                     """;
//
//        List<Oders> listOders = new ArrayList<>();
//        try {
//            con = DBConnector.getConnection();
//            ps = con.prepareStatement(sql);
//            rs = ps.executeQuery();
//            while (rs.next()) {
//                Order oderPrint = new Order();
//                oderPrint.setNameCustomer(rs.getString(1));
//                oderPrint.setNameEmployee(rs.getString(2));
//                oderPrint.setCode(rs.getString(3));
//                oderPrint.setDateCreateDate(rs.getDate(4));
//                oderPrint.setQuantityProduct(rs.getInt(5));
//                oderPrint.setSellProduct(rs.getDouble(6));
//                oderPrint.setTotalMoney(rs.getDouble(7));
//                oderPrint.setMoneyReduce(rs.getDouble(8));
//                oderPrint.setCustomerMoney(rs.getDouble(9));
//                oderPrint.setPaymentMethod(rs.getString(10));
//                oderPrint.setNameProduct(rs.getString(11));
//                listOders.add(oderPrint);
//            }
//            return listOders;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
    public int updateDeleted(boolean deleted, int id) {
        String sql = """
                     UPDATE ORDERS SET DELETED = ? WHERE ID = ?
                     """;
        try {
            con = DBConnector.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, deleted);
            ps.setObject(2, id);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int updateCustomerForOrder(int idCustomer, String phoneNumber, String orderCode) {
        String sql = """
                     UPDATE ORDERS SET ID_CUSTOMER  = ?, PHONE_NUMBER = ? WHERE CODE = ?
                     """;
        try {
            con = DBConnector.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, idCustomer);
            ps.setObject(2, phoneNumber);
            ps.setObject(3, orderCode);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public String generateNextModelCode() {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            conn = DBConnector.getConnection();
            String sql = "SELECT MAX(CAST(SUBSTRING(CODE, 3, LEN(CODE) - 2) AS INT)) FROM ORDERS";
            stm = conn.prepareStatement(sql);
            rs = stm.executeQuery();

            if (rs.next()) {
                int lastNumber = rs.getInt(1);
                System.out.println(lastNumber);

                if (lastNumber == 0) {
                    return "HD1";
                }

                // Loop until finding the next available code
                while (true) {
                    lastNumber++;
                    String nextCode = "HD" + lastNumber;
                    if (!codeExistsInDatabase(nextCode)) {
                        return nextCode;
                    }
                }
            } else {
                return "HD1";
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
            String sql = "SELECT COUNT(*) FROM ORDERS WHERE CODE = ?";
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

    public int createOrder(Order order) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         INSERT INTO N3STORESNEAKER.dbo.ORDERS
                         (CODE, ID_CUSTOMER, STATUS, ID_EMPLOYEE, DATECREATE)
                         VALUES(?, ?, ?, ?, ?);
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, order.getCode());
            stm.setObject(2, order.getIdCustomer());
            stm.setObject(3, order.getStatus());
            stm.setObject(4, order.getIdEmployee());
            stm.setObject(5, order.getCreateDate());
            int res = stm.executeUpdate();
            return res;
        } catch (Exception e) {
            return 0;
        }
    }

    public Order findByCode(String code) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         SELECT
                         	ID,
                         	CODE,
                         	ID_CUSTOMER,
                         	MONEY_REDUCED,
                         	PAYMENT_METHOD,
                         	PHONE_NUMBER,
                         	TOTAL_MONEY,
                         	STATUS,
                         	ID_EMPLOYEE,
                         	ID_VOUCHER,
                         	DATECREATE,
                         	CUSTOMERMONEY
                         FROM
                         	N3STORESNEAKER.dbo.ORDERS
                         WHERE
                         	CODE = ?;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, code);
            Order order = new Order();
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                order.setId(rs.getInt(1));
                order.setCode(rs.getString(2));
                order.setIdCustomer(rs.getInt(3));
                order.setMoneyReduce(rs.getDouble(4));
                order.setPaymentMethod(rs.getString(5));
                order.setPhoneNumber(rs.getString(6));
                order.setTotalMoney(rs.getDouble(7));
                order.setStatus(rs.getInt(8));
                order.setIdEmployee(rs.getInt(9));
                order.setIdVoucher(rs.getInt(10));
                order.setCreateDate(rs.getDate(11));
                order.setCustomerMoney(rs.getDouble(12));
            }
            return order;
        } catch (Exception e) {
            return null;
        }
    }

    public int updateTotalMoneyOrder(Double totalMoney, Integer orderId) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         UPDATE N3STORESNEAKER.dbo.ORDERS
                         SET TOTAL_MONEY = ?
                         WHERE ID = ?;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, totalMoney);
            stm.setObject(2, orderId);
            return stm.executeUpdate();
        } catch (Exception e) {
            return 0;
        }
    }

    public int payOrder(String paymentMethod, Double customerMoney, Integer orderId) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         UPDATE N3STORESNEAKER.dbo.ORDERS
                         SET PAYMENT_METHOD = ?, STATUS = 2, CUSTOMERMONEY= ?
                         WHERE ID = ?;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, paymentMethod);
            stm.setObject(2, customerMoney);
            stm.setObject(3, orderId);
            return stm.executeUpdate();
        } catch (Exception e) {
            return 0;
        }
    }

    public int addVoucher(int idVoucher, Double moneyReduce, String orderCode) {
        try (Connection con = DBConnector.getConnection()) {
            String sql = """
                         UPDATE N3STORESNEAKER.dbo.ORDERS
                         SET MONEY_REDUCED = ?, ID_VOUCHER = ?
                         WHERE CODE = ?;
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setObject(1, moneyReduce);
            stm.setObject(2, idVoucher);
            stm.setObject(3, orderCode);
            return stm.executeUpdate();
        } catch (Exception e) {
            return 0;
        }
    }

}
