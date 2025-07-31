package com.arzuahmed.ticketingsystem.config.init;

import com.arzuahmed.ticketingsystem.model.entity.Role;
import com.arzuahmed.ticketingsystem.model.entity.User;
import com.arzuahmed.ticketingsystem.repository.RoleRepositoryInterface;
import com.arzuahmed.ticketingsystem.repository.UserRepositoryInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(2) // Run after RoleInitializer which has default order of 1
public class AdminUserInitializer implements CommandLineRunner {

    private final UserRepositoryInterface userRepository;
    private final RoleRepositoryInterface roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        initAdminUser();
    }

    private void initAdminUser() {
        log.info("Initializing admin user...");

        // Check if admin user already exists
        User existingAdmin = userRepository.findUsersByEmail("admin@gmail.com");
        if (existingAdmin != null) {
            log.info("Admin user already exists");
            return;
        }

        // Get the ADMIN role (should be created by RoleInitializer)
        Role adminRole = roleRepository.findByRole("ADMIN");
        if (adminRole == null) {
            log.error("ADMIN role not found. Cannot create admin user.");
            return;
        }

        // Create new admin user
        User adminUser = new User();
        adminUser.setUserName("admin");
        adminUser.setEmail("admin@gmail.com");
        adminUser.setPassword(passwordEncoder.encode("1234"));

        Set<Role> roles = new HashSet<>();
        roles.add(adminRole);
        adminUser.setRoles(roles);

        userRepository.save(adminUser);
        log.info("Admin user created successfully with username 'admin' and password '1234'");
    }

}
