package com.example.FoodDeliveryDemoApp.component.restaurantItems.order.service;

import com.example.FoodDeliveryDemoApp.component.calculations.deliveryFee.service.DeliveryFeeService;
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
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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


    public List<OrderDTO> getOrdersByRestaurantId(Long restaurantId) {
        List<Order> orders = restaurantRepository.findById(restaurantId)
                .map(Restaurant::getOrders)
                .orElseThrow(() -> new CustomNotFoundException("Restaurant not found with id " + restaurantId));
        return OrderDTOMapper.toDtoList(orders);
    }

    public OrderDTO createOrder(Long customerId, Long restaurantId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomNotFoundException("Customer not found with id " + customerId));

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new CustomNotFoundException("Restaurant not found with id " + restaurantId));

        Order order = new Order(customer, restaurant);
        order.setStatus(OrderStatus.CREATED);
        orderRepository.save(order);

        return OrderDTOMapper.toDto(order);
    }

    public OrderDTO updateOrder(Long id, String city, String vehicleType, String items) {
        return orderRepository.findById(id).map(order -> {
            // Assuming the items string is a comma-separated list of item ids
            List<Long> itemIds = Arrays.stream(items.split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());

            // Fetch the items from the database
            List<Item> fetchedItems = itemRepository.findAllById(itemIds);

            // Update the items in the order
            order.setItems(fetchedItems);
            order.setOrderDate(Instant.now());

            Double itemPrice = order.getItemPrice();
            Double deliveryFee = deliveryFeeService.calculateAndSaveDeliveryFee(city, vehicleType,
                    OffsetDateTime.ofInstant(order.getOrderDate(), ZoneId.systemDefault())).getDeliveryFee();
            Double totalPrice = itemPrice + deliveryFee;

            order.setItemPrice(itemPrice);
            order.setDeliveryFee(deliveryFee);
            order.setTotalPrice(totalPrice);
            order.setStatus(OrderStatus.SUBMITTED);
            orderRepository.save(order);

            return OrderDTOMapper.toDto(order);
        }).orElseThrow(() -> new CustomNotFoundException("Order not found with id " + id));
    }

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
