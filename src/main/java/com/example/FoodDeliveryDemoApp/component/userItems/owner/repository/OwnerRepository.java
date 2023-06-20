package com.example.FoodDeliveryDemoApp.component.userItems.owner.repository;

import com.example.FoodDeliveryDemoApp.component.userItems.owner.domain.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {

/*    Optional<Owner> findByUsername(String username);

    Optional<Owner> findByEmail(String email);*/

    Optional<List<Owner>> findByApproved(boolean approved);

/*    boolean existsByUsername(String username);

    boolean existsByEmail(String email);*/
}
