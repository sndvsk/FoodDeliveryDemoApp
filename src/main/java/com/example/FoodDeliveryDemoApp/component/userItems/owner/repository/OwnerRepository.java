package com.example.FoodDeliveryDemoApp.component.userItems.owner.repository;

import com.example.FoodDeliveryDemoApp.component.userItems.owner.domain.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {

    Optional<List<Owner>> findByApproved(boolean approved);

    @Query(value = "SELECT * FROM users_owners WHERE user_id = :userId", nativeQuery = true)
    Optional<Owner> findOwnerByUserId(@Param("userId") Long userId);

}
