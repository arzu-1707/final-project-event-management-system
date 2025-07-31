package com.arzuahmed.ticketingsystem.config.init;

import com.arzuahmed.ticketingsystem.model.entity.Role;
import com.arzuahmed.ticketingsystem.repository.RoleRepositoryInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(1) // Run before AdminUserInitializer
public class RoleInitializer implements CommandLineRunner {

    private final RoleRepositoryInterface roleRepository;

    @Override
    public void run(String... args) {
        initRoles();
    }

    private void initRoles() {
        log.info("Initializing roles...");
        if (roleRepository.findByRole("USER") == null) {
            Role userRole = new Role();
            userRole.setRole("USER");
            roleRepository.save(userRole);
            log.info("USER role created");
        } else {
            log.info("USER role already exists");
        }
        if (roleRepository.findByRole("ADMIN") == null) {
            Role adminRole = new Role();
            adminRole.setRole("ADMIN");
            roleRepository.save(adminRole);
            log.info("ADMIN role created");
        } else {
            log.info("ADMIN role already exists");
        }
        log.info("Role initialization completed");
    }
}


