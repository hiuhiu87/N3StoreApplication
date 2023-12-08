/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.model.OrderDetail;
import app.repository.OrderDetailRepository;
import app.response.OrderDetailResponse;
import java.util.List;

/**
 *
 * @author H.Long
 */
public class OrderDetailService {

    private final OrderDetailRepository oderDetailRepository = new OrderDetailRepository();

    public List<OrderDetail> getAllOderDetails(String orderId) {
        return oderDetailRepository.getAllOdersDetails(orderId);
    }

    public List<OrderDetailResponse> getPaginatedOders(int offset, int limit, String orderId) {
        return oderDetailRepository.getAllOdersDetailsPhanTrang(offset, limit, orderId);
    }

    public int countOderDetail(String orderCode) {
        return oderDetailRepository.countOderDetail(orderCode);
    }

    public int getQuantityOrderDetail(String orderCode, String productDetailCode) {
        return oderDetailRepository.getQuantityOrderDetail(orderCode, productDetailCode);
    }

    public boolean deleteFromCartCancelOrder(int orderId) {
        return oderDetailRepository.deleteProductFromCart(orderId) > 0;
    }

}
