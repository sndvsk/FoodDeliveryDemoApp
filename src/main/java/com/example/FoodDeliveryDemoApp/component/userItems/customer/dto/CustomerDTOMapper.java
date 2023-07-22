package com.example.FoodDeliveryDemoApp.component.userItems.customer.dto;

import com.example.FoodDeliveryDemoApp.component.userItems.customer.domain.Customer;
import com.example.FoodDeliveryDemoApp.component.userItems.user.dto.UserDTOMapper;
import com.example.FoodDeliveryDemoApp.component.address.dto.AddressDTOMapper;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.order.dto.OrderDTOMapper;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerDTOMapper {

    public static CustomerDTO toDto(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setUserDTO(UserDTOMapper.toDto(customer.getUser()));
        dto.setAddressDTO(AddressDTOMapper.toDto(customer.getAddress()));
        dto.setOrderDTOs(customer.getOrders().stream()
                .map(OrderDTOMapper::toDto)
                .collect(Collectors.toList()));
        return dto;
    }

    public static List<CustomerDTO> toDtoList(List<Customer> customers) {
        return customers.stream()
                .map(CustomerDTOMapper::toDto)
                .collect(Collectors.toList());
    }
}
