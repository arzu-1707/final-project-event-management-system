package com.arzuahmed.ticketingsystem;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "Ticketing System API",
                version = "1.0",
                description = """
             Bu API hem User, hem de admin ucun nezerde tutulub. Common operation-lar hissesinde sisteme hec bir giris ve qeydiyyat teleb olunmadan mueyyen melumatlara baxmaq ucun nezerde tutulub. Bu sistemde bir admin var, qalan register olan her bir kes User kimi qeydiyyatdan kecir. Admin Operation hissesinde Api-ler Admin ucun nezerde tutulub, User Operation hissesinde ise User-e aid yeni login olunduqdan sonra istifade edilen API-lerdir.
        """,
                contact = @Contact(
                        name = "Arzu Ahmedova",
                        email = "arzuahmedova.1707@gmail.com"
                )
        )
)

@SpringBootApplication
public class TicketingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketingSystemApplication.class, args);
    }

}
