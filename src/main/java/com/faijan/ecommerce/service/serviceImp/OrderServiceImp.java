package com.faijan.ecommerce.service.serviceImp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.faijan.ecommerce.dto.request.OrderRequest;
import com.faijan.ecommerce.dto.request.UpdateOrderStatusRequest;
import com.faijan.ecommerce.dto.response.OrderItemResponse;
import com.faijan.ecommerce.dto.response.OrderResponse;
import com.faijan.ecommerce.entity.Cart;
import com.faijan.ecommerce.entity.CartItem;
import com.faijan.ecommerce.entity.Order;
import com.faijan.ecommerce.entity.OrderItem;
import com.faijan.ecommerce.entity.OrderStatus;
import com.faijan.ecommerce.entity.User;
import com.faijan.ecommerce.exception.ResourceNotFoundException;
import com.faijan.ecommerce.repository.CartRepository;
import com.faijan.ecommerce.repository.OrderRepository;
import com.faijan.ecommerce.repository.UserRepository;
import com.faijan.ecommerce.service.OrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImp implements OrderService{
	
	private final OrderRepository orderRepository;
	private final CartRepository cartRepository;
	private final UserRepository userRepository;

	@Override
	public OrderResponse placeOrder(Long userId, OrderRequest request) {
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("There is no user with this id."));
		Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new ResourceNotFoundException("There is no cart associated with this user."));
		List<CartItem> cartItems = cart.getCartItems();
		if(cartItems.isEmpty()) {
			throw new RuntimeException("The cart is empty");
		}
		Order newOrder = new Order();
		newOrder.setUser(user);
		newOrder.setStatus(OrderStatus.PLACED);
		newOrder.setOrderDate(LocalDateTime.now());
		newOrder.setShippingAddress(request.getAddress());
		newOrder.setPhoneNumber(user.getPhoneNumber());
		BigDecimal totalPrice = BigDecimal.valueOf(0);
		List<OrderItem> orderItems = new ArrayList<>();
		for(CartItem cartItem : cartItems) {
			OrderItem orderItem = new OrderItem();
			int quantity = cartItem.getQuantity();
			BigDecimal price = cartItem.getProduct().getPrice();
			orderItem.setOrder(newOrder);
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setProductName(cartItem.getProduct().getName());
			orderItem.setPrice(price);
			orderItem.setQuantity(quantity);
			orderItems.add(orderItem);
			totalPrice = totalPrice.add(price.multiply(BigDecimal.valueOf(quantity)));
		}
		newOrder.setTotalAmount(totalPrice);
		newOrder.setOrderItems(orderItems);
		Order saved = orderRepository.save(newOrder);
		cart.getCartItems().clear();
		cartRepository.save(cart);
		return toOrderResponse(saved);
	}

	@Override
	public OrderResponse getOrder(Long orderId, Long userId) {
		Order existingOrder = orderRepository.findByIdAndUser_Id(orderId, userId).orElseThrow(() -> new ResourceNotFoundException("There is no order with this userId and orderId"));
		return toOrderResponse(existingOrder);
	}

	@Override
	public List<OrderResponse> getOrdersByUser(Long userId) {
		List<OrderResponse> userOrders = orderRepository.findByUserId(userId).stream().map(this::toOrderResponse).toList();
		return userOrders;
	}

	@Override
	public List<OrderResponse> getAllOrders() {
		List<OrderResponse> existingOrders = orderRepository.findAll().stream().map(this::toOrderResponse).toList();
		return existingOrders;
	}

	@Override
	public OrderResponse updateOrderStatus(Long orderId, UpdateOrderStatusRequest request) {
		Order existingOrder = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("There is no order with this Id and userId."));
		existingOrder.setStatus(request.getStatus());
		Order saved = orderRepository.save(existingOrder);
		return toOrderResponse(saved);
	}

	private OrderResponse toOrderResponse(Order existingOrder) {
		
		OrderResponse response = OrderResponse.builder()
				.id(existingOrder.getId())
				.userId(existingOrder.getUser().getId())
				.orderItems(existingOrder.getOrderItems().stream().map(this::toOrderItemResponse).toList())
				.totalAmount(existingOrder.getTotalAmount())
				.status(existingOrder.getStatus())
				.orderDate(existingOrder.getOrderDate())
				.address(existingOrder.getShippingAddress())
				.build();
		
		return response;
	}
	
	private OrderItemResponse toOrderItemResponse(OrderItem orderItem) {
		OrderItemResponse response = OrderItemResponse.builder()
				.id(orderItem.getId())
				.productName(orderItem.getProductName())
				.price(orderItem.getPrice())
				.quantity(orderItem.getQuantity())
				.subtotal(orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())))
				.build();
		
		return response;
	}

	@Override
	public void cancelOrder(Long orderId, Long userId) {

		Order existingOrder = orderRepository.findByIdAndUser_Id(orderId, userId).orElseThrow(() -> new ResourceNotFoundException("There is no order with this Id and userId."));
		if(existingOrder.getStatus() == OrderStatus.DELIVERED || existingOrder.getStatus() == OrderStatus.SHIPPED) {
			throw new RuntimeException("Cannot cancel an order that has already deliverd or shipped.");
		}
		existingOrder.setStatus(OrderStatus.CANCELED);
		orderRepository.save(existingOrder);
	}

}
