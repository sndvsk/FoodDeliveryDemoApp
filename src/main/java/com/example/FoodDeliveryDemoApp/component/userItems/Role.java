package com.example.FoodDeliveryDemoApp.component.userItems;

import com.example.FoodDeliveryDemoApp.component.userItems.user.Permission;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum Role {

    ADMIN(Arrays.asList(
            Permission.ADMIN_READ,
            Permission.ADMIN_UPDATE,
            Permission.ADMIN_DELETE,
            Permission.ADMIN_CREATE,
            Permission.OWNER_READ,
            Permission.OWNER_UPDATE,
            Permission.OWNER_DELETE,
            Permission.OWNER_CREATE,
            Permission.CUSTOMER_READ,
            Permission.CUSTOMER_UPDATE,
            Permission.CUSTOMER_DELETE,
            Permission.CUSTOMER_CREATE
    )),
    OWNER(Arrays.asList(
            Permission.OWNER_READ,
            Permission.OWNER_UPDATE,
            Permission.OWNER_DELETE,
            Permission.OWNER_CREATE
    )),
    CUSTOMER(Arrays.asList(
            Permission.CUSTOMER_READ,
            Permission.CUSTOMER_UPDATE,
            Permission.CUSTOMER_DELETE,
            Permission.CUSTOMER_CREATE
    ));

    @Getter
    private final List<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}

