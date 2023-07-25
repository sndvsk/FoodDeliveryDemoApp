package com.example.FoodDeliveryDemoApp.component.restaurantItems.order.service;

import com.example.FoodDeliveryDemoApp.component.calculations.deliveryFee.service.DeliveryFeeService;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.order.domain.OrderItem;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.order.domain.OrderItemId;
import com.example.FoodDeliveryDemoApp.component.utils.OwnershipHelper;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.item.domain.Item;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.item.repository.ItemRepository;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.order.domain.Order;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.order.domain.OrderStatus;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.order.dto.OrderDTO;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.order.dto.OrderDTOMapper;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.order.repository.OrderRepository;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.domain.Restaurant;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.repository.RestaurantRepository;
import com.example.FoodDeliveryDemoApp.component.userItems.customer.domain.Customer;
import com.example.FoodDeliveryDemoApp.component.userItems.customer.repository.CustomerRepository;
import com.example.FoodDeliveryDemoApp.component.userItems.user.repository.UserRepository;
import com.example.FoodDeliveryDemoApp.exception.CustomNotFoundException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final DeliveryFeeService deliveryFeeService;

    public OrderServiceImpl(OrderRepository orderRepository,
                            ItemRepository itemRepository,
                            CustomerRepository customerRepository,
                            UserRepository userRepository, RestaurantRepository restaurantRepository, DeliveryFeeService deliveryFeeService) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.deliveryFeeService = deliveryFeeService;
    }

    private void validateInputs() {

    }

    private void validateRequiredInputs() {

    }

    public List<OrderDTO> getAllOrders() {
        List<Order> listOfOrders = orderRepository.findAll();
        if (listOfOrders.isEmpty()) {
            throw new CustomNotFoundException("No orders in the database.");
        }
        return OrderDTOMapper.toDtoList(listOfOrders);
    }

    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("Order not found with id " + id));
        return OrderDTOMapper.toDto(order);
    }

    public List<OrderDTO> getOrdersByCustomerId(Long userId) {
        List<Order> orders = customerRepository.findByUserId(userId)
                .map(Customer::getOrders)
                .orElseThrow(() -> new CustomNotFoundException("User not found with id " + userId));
        return OrderDTOMapper.toDtoList(orders);
    }

    @Transactional
    public List<OrderDTO> getOrdersByRestaurantId(Long restaurantId, Long ownerId) {
        // Fetch restaurant first
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new CustomNotFoundException("Restaurant not found with id " + restaurantId));
        OwnershipHelper.validateOwner(ownerId, restaurant.getOwner().getId());
        List<Order> orders = restaurant.getOrders();
        return OrderDTOMapper.toDtoList(orders);
    }

    @Transactional
    public List<OrderDTO> getOrdersByRestaurantIdAndCustomerId(Long restaurantId, Long ownerId, Long customerId) {
        // Fetch restaurant first
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new CustomNotFoundException("Restaurant not found with id " + restaurantId));

        // Validate owner
        OwnershipHelper.validateOwner(ownerId, restaurant.getOwner().getId());

        // Get and filter orders
        List<Order> orders = restaurant.getOrders();
        List<Order> customerOrders = orders.stream()
                .filter(order -> order.getCustomer().getId().equals(customerId))
                .collect(Collectors.toList());

        return OrderDTOMapper.toDtoList(customerOrders);
    }

    @Transactional
    public OrderDTO createOrder(Long customerId, Long restaurantId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomNotFoundException("Customer not found with id " + customerId));

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new CustomNotFoundException("Restaurant not found with id " + restaurantId));

        Order order = new Order(customer, restaurant);
        order.setStatus(OrderStatus.CREATED);
        order.setOrderDate(Instant.now());
        orderRepository.save(order);

        return OrderDTOMapper.toDto(order);
    }

    @Transactional
    public OrderDTO updateOrder(Long id, String city, String vehicleType, String items, Long customerId) {
        return orderRepository.findById(id)
                .map(order -> {
                    OwnershipHelper.validateCustomer(customerId, order.getCustomer().getId());
                    Map<Long, Integer> itemsMap = getItemsAndQuantities(items);
                    List<OrderItem> fetchedItems = getItemsFromMap(order, itemsMap);
                    return updateOrderAttributes(order, fetchedItems, city, vehicleType);
                })
                .map(OrderDTOMapper::toDto)
                .orElseThrow(() -> new CustomNotFoundException("Order not found with id " + id));
    }

    private Map<Long, Integer> getItemsAndQuantities(String items) {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<Long,Integer>> typeRef = new TypeReference<>() {};
        try {
            Map<Long, Integer> itemsMap = mapper.readValue(items, typeRef);
            return itemsMap;
        } catch (Exception e) {
            throw new RuntimeException("Invalid items input format", e);
        }
    }

    private List<OrderItem> getItemsFromMap(Order order, Map<Long, Integer> itemsMap) {
        return itemsMap.entrySet().stream()
                .map(entry -> {
                    Item item = itemRepository.findById(entry.getKey())
                            .orElseThrow(() -> new CustomNotFoundException("Item not found with id " + entry.getKey()));
                    OrderItemId orderItemId = new OrderItemId(order.getId(), item.getId());
                    return new OrderItem(orderItemId, order, item, entry.getValue());
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public Order updateOrderAttributes(Order order, List<OrderItem> orderItems, String city, String vehicleType) {
        Double itemPrice = calculateOrderItemPrice(orderItems);
        Double deliveryFee = deliveryFeeService.calculateAndSaveDeliveryFee(city, vehicleType, OffsetDateTime.now()).getDeliveryFee();

        Order updatedOrder = Order.builder()
                .id(order.getId())
                .customer(order.getCustomer())
                .restaurant(order.getRestaurant())
                .orderItems(orderItems)
                .orderDate(Instant.now())
                .itemPrice(itemPrice)
                .deliveryFee(deliveryFee)
                .totalPrice(itemPrice + deliveryFee)
                .status(OrderStatus.SUBMITTED)
                .build();

        orderItems.forEach(orderItem -> orderItem.setOrder(updatedOrder));

        return orderRepository.save(updatedOrder);
    }

    private Double calculateOrderItemPrice(List<OrderItem> orderItems) {
        return orderItems.stream()
                .mapToDouble(orderItem -> orderItem.getItem().getPrice() * orderItem.getQuantity())
                .sum();
    }

    @Transactional
    public String deleteOrder(Long id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            orderRepository.delete(orderOptional.get());
            return String.format("Order with id: ´%s´ was deleted", id);
        } else {
            throw new CustomNotFoundException("Order not found with id " + id);
        }
    }

}
