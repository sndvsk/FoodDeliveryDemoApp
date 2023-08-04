package com.example.FoodDeliveryDemoApp.component.userItems.owner.service;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.order.domain.Order;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.order.domain.OrderStatus;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.order.dto.OrderDTO;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.order.dto.OrderDTOMapper;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.order.repository.OrderRepository;
import com.example.FoodDeliveryDemoApp.component.userItems.owner.domain.Owner;
import com.example.FoodDeliveryDemoApp.component.userItems.owner.dto.OwnerDTO;
import com.example.FoodDeliveryDemoApp.component.userItems.owner.dto.OwnerDTOMapper;
import com.example.FoodDeliveryDemoApp.component.userItems.owner.repository.OwnerRepository;
import com.example.FoodDeliveryDemoApp.component.userItems.user.domain.User;
import com.example.FoodDeliveryDemoApp.component.userItems.user.repository.UserRepository;
import com.example.FoodDeliveryDemoApp.component.utils.OwnershipHelper;
import com.example.FoodDeliveryDemoApp.exception.CustomAccessDeniedException;
import com.example.FoodDeliveryDemoApp.exception.CustomNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public OwnerServiceImpl(OwnerRepository ownerRepository,
                            UserRepository userRepository, OrderRepository orderRepository) {
        this.ownerRepository = ownerRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public OwnerDTO getOwner(Long userId) {
        Owner owner = ownerRepository.findOwnerByUserId(userId)
                .orElseThrow(() -> new CustomNotFoundException("No user with such id: " + userId));
        return OwnerDTOMapper.toDto(owner);
    }

    @Transactional
    public OrderDTO acceptOrder(Long orderId, Long userId) {
        Owner owner = ownerRepository.findOwnerByUserId(userId)
                .orElseThrow(() -> new CustomNotFoundException("No user with such id: " + userId));
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomNotFoundException("No order with such id: " + orderId));

        OwnershipHelper.validateOwner(owner.getId(), order.getRestaurant().getOwner().getId());

        order.setStatus(OrderStatus.ACCEPTED);
        orderRepository.save(order);
        return OrderDTOMapper.toDto(order);  // Assuming you have a mapper to convert Order to OrderDTO
    }

    @Transactional
    public OrderDTO rejectOrder(Long orderId, Long userId) {
        Owner owner = ownerRepository.findOwnerByUserId(userId)
                .orElseThrow(() -> new CustomNotFoundException("No user with such id: " + userId));
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomNotFoundException("No order with such id: " + orderId));

        OwnershipHelper.validateOwner(owner.getId(), order.getRestaurant().getOwner().getId());

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
        return OrderDTOMapper.toDto(order);  // Assuming you have a mapper to convert Order to OrderDTO
    }


    public Long getIdByUsername(String username) {
        User owner = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomNotFoundException("No user with such username: " + username));
        return owner.getId();
    }

}
