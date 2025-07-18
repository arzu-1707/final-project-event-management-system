package com.arzuahmed.ticketingsystem.repository;

import com.arzuahmed.ticketingsystem.model.entity.Event;
import com.arzuahmed.ticketingsystem.model.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepositoryInterface extends JpaRepository<Event, Long> {

    Event findByName(String name);

    boolean existsEventsByEventDate(LocalDateTime eventDate);

    List<Event> findEventByNameIsLikeIgnoreCase(String name);

    List<Event> findEventsByPlace(Place place);
}
