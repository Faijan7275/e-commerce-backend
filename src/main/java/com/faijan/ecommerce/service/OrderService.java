package com.faijan.ecommerce.service;

import java.util.List;

import com.faijan.ecommerce.dto.request.OrderRequest;
import com.faijan.ecommerce.dto.request.UpdateOrderStatusRequest;
import com.faijan.ecommerce.dto.response.OrderResponse;

public interface OrderService {
	
	public OrderResponse placeOrder(Long userId, OrderRequest request);
	
	public OrderResponse getOrder(Long orderId, Long userId);
	
	public List<OrderResponse> getOrdersByUser(Long userId);
	
	public List<OrderResponse> getAllOrders ();
	
	public OrderResponse updateOrderStatus(Long orderId, UpdateOrderStatusRequest status);
	
	public void cancelOrder(Long orderId, Long userId);
	

}
