//package com.example.admin.Service;
//
//import com.example.admin.Entity.Order;
//import com.example.admin.Entity.OrderItem;
//import com.example.admin.Repository.OrderRepository;
//import com.example.admin.Repository.OrderRepositoryUser;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class OrderServiceUser {
//
//    @Autowired
//    private OrderRepositoryUser orderRepositoryUser;
//
//    public List<OrderItem> getAllOrders() {
//        return orderRepositoryUser.findAll();
//    }
//
//    public OrderItem getOrderById(Long id) {
//        return orderRepositoryUser.findById(id).orElse(null);
//    }
//
//    public OrderItem createOrder(OrderItem orderItem) {
//        return orderRepositoryUser.save(orderItem);
//    }
//
//    public OrderItem updateOrder(Long id, OrderItem orderDetails) {
//        OrderItem orderItem = orderRepositoryUser.findById(id).orElse(null);
//        if (orderItem != null) {
//            orderItem.setOrderStatus(orderDetails.getOrderStatus());
//            orderItem.setPaymentStatus(orderDetails.getPaymentStatus());
//            orderItem.setRazorpayOrderId(orderDetails.getRazorpayOrderId());
//            orderItem.setRazorpayPaymentId(orderDetails.getRazorpayPaymentId());
//            orderItem.setRazorpaySignature(orderDetails.getRazorpaySignature());
//            // Update other fields as necessary
//            return orderRepositoryUser.save(orderItem);
//        }
//        return null;
//    }
//
//    public void deleteOrder(Long id) {
//        orderRepositoryUser.deleteById(id);
//    }
//}
