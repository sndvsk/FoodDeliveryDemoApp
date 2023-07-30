package com.example.FoodDeliveryDemoApp.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "test",
                        email = "test@gmail.com",
                        url = "https://test.com"
                ),
                title="FoodDeliveryDemoApp API",
                description = """
				<font size='5'>This app has a sub-functionality of the food delivery application,
				 which calculates the delivery fee for food couriers based on regional base fee,
				 vehicle type, and weather conditions.</font>""",
                version = "v1",
                license = @License(
                        name = "MIT",
                        url = "https://opensource.org/license/mit/"
                ),
                termsOfService = "Terms of service"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "PROD ENV",
                        url = "http://localhost:8080" // to change
                )
        }
/*        ,
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }*/
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {

}