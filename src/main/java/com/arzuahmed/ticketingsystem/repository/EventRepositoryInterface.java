package com.arzuahmed.ticketingsystem.repository;

import com.arzuahmed.ticketingsystem.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface EventRepositoryInterface extends JpaRepository<Event, BigDecimal> {

}
