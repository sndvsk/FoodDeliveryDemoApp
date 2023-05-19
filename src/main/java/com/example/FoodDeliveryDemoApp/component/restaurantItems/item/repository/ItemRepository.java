package com.example.FoodDeliveryDemoApp.component.restaurantItems.item.repository;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.item.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

}
