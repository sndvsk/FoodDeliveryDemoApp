package com.example.FoodDeliveryDemoApp.component.userItems.admin.domain;

import com.example.FoodDeliveryDemoApp.component.userItems.Roles;
import com.example.FoodDeliveryDemoApp.component.userItems.user.domain.User;
import com.example.FoodDeliveryDemoApp.security.token.Token;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.List;

@SuperBuilder
@NoArgsConstructor
//@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users_admins")
public class Admin extends User {

    @Column(name = "level")
    private Long level;

    @OneToOne(mappedBy = "admin")
    private Token token;

/*    public Admin(Long id, String username, String password, String email, Roles role, Instant createdAt, Instant updatedAt, Long level) {
        super(id, username, password, email, role, createdAt, updatedAt);
        this.level = level;
    }

    public Admin(Long level) {
        this.level = level;
    }

    public Admin() {
    }*/

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }
}
