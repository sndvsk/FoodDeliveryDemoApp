package com.example.FoodDeliveryDemoApp.component.userItems.user.domain;

import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.*;

@SuperBuilder
public class UserDetailsImpl implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;
    Long id;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    //private String userId;  // new field for userId

    private final Map<String, Object> claims = new HashMap<>();

    public UserDetailsImpl(Long id, String username, String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public UserDetailsImpl() {
    }

    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));

        UserDetailsImpl userDetails = new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                authorities);
                //user.getId().toString()); // pass user Id as string

/*        if (user instanceof Owner owner) {
            userDetails.getClaims().put("restaurants", owner.getRestaurants());
            //userDetails.getClaims().put("approved", owner.getOwner().isApproved());
        } else if (user instanceof Admin admin) {
            userDetails.getClaims().put("level", admin.getLevel());
        } else if (user instanceof Customer customer) {
            userDetails.getClaims().put("orders", customer.getOrders());
            userDetails.getClaims().put("addresses", customer.getAddresses());
        }*/

        return userDetails;
    }

    public Map<String, Object> getClaims() {
        return claims;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
