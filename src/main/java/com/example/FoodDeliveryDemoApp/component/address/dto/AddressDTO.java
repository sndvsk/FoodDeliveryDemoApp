package com.example.FoodDeliveryDemoApp.component.address.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

    @JsonProperty("country")
    @NotBlank(message = "Country cannot be blank.")
    @Pattern(regexp = "^[\\p{L} ']+$", message = "Country contains invalid characters.")
    @Size(min = 3, max = 255, message = "Country must be between 3 and 255 characters.")
    private String country;

    @JsonProperty("county")
    @NotBlank(message = "County cannot be blank.")
    @Pattern(regexp = "^[\\p{L} ']+$", message = "County contains invalid characters.")
    @Size(min = 3, max = 255, message = "County must be between 3 and 255 characters.")
    private String county;

    @JsonProperty("city")
    @NotBlank(message = "City cannot be blank.")
    @Pattern(regexp = "^[\\p{L} ']+$", message = "City contains invalid characters.")
    @Size(min = 3, max = 255, message = "City must be between 3 and 255 characters.")
    private String city;

    @JsonProperty("zip_code")
    @NotNull(message = "ZIP Code cannot be null.")
    @Pattern(regexp = "^\\d{5,15}$", message = "ZIP Code contains invalid characters.")
    private Integer zipCode;

    @JsonProperty("street")
    @NotBlank(message = "Street cannot be blank.")
    @Pattern(regexp = "^[\\p{L}\\p{N} ]*$", message = "Street contains invalid characters.")
    @Size(min = 3, max = 255, message = "Street must be between 3 and 255 characters.")
    private String street;

    @JsonProperty("house_number")
    @NotBlank(message = "House Number cannot be blank.")
    @Pattern(regexp = "^\\d+[-\\s/]*\\d*$", message = "House Number contains invalid characters.")
    @Size(max = 255, message = "House Number cannot be more than 255 characters.")
    private String houseNumber;

    @JsonProperty("apt_number")
    @Pattern(regexp = "^[\\p{L}\\p{N}]*$", message = "Apartment Number contains invalid characters.")
    @Size(max = 255, message = "Apartment Number cannot be more than 255 characters.")
    private String aptNumber;
}
