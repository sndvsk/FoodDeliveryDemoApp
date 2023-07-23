package com.example.FoodDeliveryDemoApp.component.calculations.deliveryFee.dto;

import com.example.FoodDeliveryDemoApp.component.calculations.deliveryFee.domain.DeliveryFee;
import java.util.List;
import java.util.stream.Collectors;

public class DeliveryFeeDTOMapper {

    public static DeliveryFeeDTO toDto(DeliveryFee deliveryFee) {
        DeliveryFeeDTO dto = new DeliveryFeeDTO();
        dto.setId(deliveryFee.getId());
        dto.setCity(deliveryFee.getCity());
        dto.setVehicleType(deliveryFee.getVehicleType());
        dto.setDeliveryFee(deliveryFee.getDeliveryFee());
        dto.setRest_timestamp(deliveryFee.getRest_timestamp());
        return dto;
    }

    public static List<DeliveryFeeDTO> toDtoList(List<DeliveryFee> deliveryFees) {
        return deliveryFees.stream()
                .map(DeliveryFeeDTOMapper::toDto)
                .collect(Collectors.toList());
    }

}
