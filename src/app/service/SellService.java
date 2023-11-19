/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.model.KhachHang;
import app.model.Order;
import app.model.OrderDetail;
import app.repository.OrderDetailRepository;
import app.repository.OrderRepository;
import app.repository.SellRepository;
import app.request.NewOrderRequest;
import app.response.CartResponse;
import app.util.XDate;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 *
 * @author H.Long
 */
public class SellService {

    private final SellRepository sellRepository = new SellRepository();
    private final OrderRepository orderRepository = new OrderRepository();
    private final OrderDetailRepository orderDetailRepository = new OrderDetailRepository();

    public List<KhachHang> getAllKhachHang() {
        return sellRepository.getAllKhachHang();
    }

    public int addKhachHang(KhachHang kh) {
        return sellRepository.addKhachHang(kh);
    }

    public int updateKhachHang(KhachHang kh, int id) {
        return sellRepository.updateKhachHang(kh, id);
    }

    public boolean createNewOrder(NewOrderRequest newOrderRequest) {
        Order order = new Order();
        order.setCode(newOrderRequest.getCode());
        order.setIdCustomer(newOrderRequest.getIdCustomer());
        order.setStatus(1);
        order.setIdEmployee(newOrderRequest.getIdEmployee());
        order.setCreateDate(new Date());
        return orderRepository.createOrder(order) > 0;
    }

    public List<CartResponse> getAllListCart(String orderCode) {
        return orderDetailRepository.getListCarts(orderCode);
    }

    public String generateCodeOrder() {
        return orderRepository.generateNextModelCode();
    }

    public boolean addToCart(OrderDetail orderDetail) {
        return orderDetailRepository.addOrderDetail(orderDetail) > 0;
    }

    public boolean deleteFromCart(int productDetailCode) {
        return orderDetailRepository.deleteOrderDetail(productDetailCode) > 0;
    }

}
