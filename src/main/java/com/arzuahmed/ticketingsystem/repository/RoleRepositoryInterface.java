package com.arzuahmed.ticketingsystem.repository;

import com.arzuahmed.ticketingsystem.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepositoryInterface extends JpaRepository<Role, Long> {
    Role findByRole(String role);
}
