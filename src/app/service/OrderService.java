/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.model.Order;
import app.repository.OrderRepository;
import app.response.OrderResponse;
import java.util.List;

/**
 *
 * @author H.Long
 */
public class OrderService {

    OrderRepository orderRepository = new OrderRepository();
    VoucherService voucherService = new VoucherService();

    public boolean cancelOrder(String orderCode) {
        return orderRepository.updateStatusOrderToCancel(orderCode) > 0;
    }

    public List<Order> getAllOders() {
        return orderRepository.getAllOders();
    }

    public List<OrderResponse> getPaginatedOders(int offset, int limit) {
        return orderRepository.getPaginatedOders(offset, limit);
    }

    public List<OrderResponse> getAllOdersByStatus(int status) {
        return orderRepository.getAllListByStatus(status);
    }

    public int countOder() {
        return orderRepository.countOder();
    }

//    public List<Oders> getAllListPrintOders(){
//        return orderRepository.getAllListPrintOder();
//    }
    public int updateDeleted(boolean deleted, int id) {
        return orderRepository.updateDeleted(deleted, id);
    }

    public List<OrderResponse> getAllOrderView() {
        return orderRepository.getAllOrderView();
    }

    public Order findByCode(String code) {
        return orderRepository.findByCode(code);
    }

    public boolean updateTotalMoney(Double totalMoney, Integer orderId) {
        return orderRepository.updateTotalMoneyOrder(totalMoney, orderId) > 0;
    }

    public boolean payOrder(String paymentMethod, Double customerMoney, Integer orderId) {
        return orderRepository.payOrder(paymentMethod, customerMoney, orderId) > 0;
    }

    public OrderResponse getDetailOrderResponse(String code) {
        return orderRepository.getOrderResponseByCode(code);
    }

    public boolean addVoucher(int idVoucher, Double moneyReduce, String orderCode) {
        boolean resAdd = orderRepository.addVoucher(idVoucher, moneyReduce, orderCode) > 0;
        if (resAdd) {
            voucherService.updateQuantityUse(idVoucher);
        }
        return resAdd;
    }

}
