package com.example.FoodDeliveryDemoApp.component.restaurantItems.order.service;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.item.domain.Item;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.item.repository.ItemRepository;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.order.domain.Order;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.order.repository.OrderRepository;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.domain.Restaurant;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.repository.RestaurantRepository;
import com.example.FoodDeliveryDemoApp.component.userItems.customer.domain.Customer;
import com.example.FoodDeliveryDemoApp.component.userItems.customer.repository.CustomerRepository;
import com.example.FoodDeliveryDemoApp.exception.CustomNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            ItemRepository itemRepository,
                            CustomerRepository customerRepository,
                            RestaurantRepository restaurantRepository) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.customerRepository = customerRepository;
        this.restaurantRepository = restaurantRepository;
    }

    private void validateInputs() {

    }

    private void validateRequiredInputs() {

    }

    public List<Order> getAllOrders() {
        List<Order> listOfOrders = orderRepository.findAll();
        if (listOfOrders.isEmpty()) {
            throw new CustomNotFoundException("No orders in the database.");
        }
        return listOfOrders;
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("Order not found with id " + id));
    }

    public List<Order> getOrdersByCustomerId(Long customerId) {
        return customerRepository.findById(customerId)
                .map(Customer::getOrders)
                .orElseThrow(() -> new CustomNotFoundException("Customer not found with id " + customerId));
    }

    public List<Order> getOrdersByRestaurantId(Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .map(Restaurant::getOrders)
                .orElseThrow(() -> new CustomNotFoundException("Restaurant not found with id " + restaurantId));
    }

    public Order createOrder(Long customerId, Long restaurantId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomNotFoundException("Customer not found with id " + customerId));

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new CustomNotFoundException("Restaurant not found with id " + restaurantId));

        Order order = new Order(customer, restaurant);
        return orderRepository.save(order);
    }

    public Order updateOrder(Long id, String items) {
        return orderRepository.findById(id).map(order -> {
            // Assuming the items string is a comma-separated list of item ids
            List<Long> itemIds = Arrays.stream(items.split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());

            // Fetch the items from the database
            List<Item> fetchedItems = itemRepository.findAllById(itemIds);

            // Update the items in the order
            order.setItems(fetchedItems);

            return orderRepository.save(order);
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
