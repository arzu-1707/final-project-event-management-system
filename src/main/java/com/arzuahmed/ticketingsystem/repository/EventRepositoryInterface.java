package com.arzuahmed.ticketingsystem.repository;

import com.arzuahmed.ticketingsystem.model.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface EventRepositoryInterface extends JpaRepository<Event, BigDecimal> {

    Event findEventById(Long id);

    Event findEventByName(String name);

    List<Event> findByEventDateBetween(LocalDateTime start, LocalDateTime end);

    List<Event> findByAvailableTicketsTrue();

    void deleteEventById(Long id);
}
