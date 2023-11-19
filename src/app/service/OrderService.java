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

    OrderRepository oderRepository = new OrderRepository();

    public List<Order> getAllOders() {
        return oderRepository.getAllOders();
    }

    public List<OrderResponse> getPaginatedOders(int offset, int limit) {
        return oderRepository.getPaginatedOders(offset, limit);
    }

    public List<OrderResponse> getAllOdersByStatus(int status) {
        return oderRepository.getAllListByStatus(status);
    }

    public int countOder() {
        return oderRepository.countOder();
    }

//    public List<Oders> getAllListPrintOders(){
//        return oderRepository.getAllListPrintOder();
//    }
    public int updateDeleted(boolean deleted, int id) {
        return oderRepository.updateDeleted(deleted, id);
    }

    public List<OrderResponse> getAllOrderView() {
        return oderRepository.getAllOrderView();
    }

    public Order findByCode(String code) {
        return oderRepository.findByCode(code);
    }

}
