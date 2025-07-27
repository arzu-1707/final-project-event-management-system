package com.arzuahmed.ticketingsystem.repository;

import com.arzuahmed.ticketingsystem.model.entity.Event;
import com.arzuahmed.ticketingsystem.model.entity.Place;
import com.arzuahmed.ticketingsystem.model.response.eventResponse.EventResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface EventRepositoryInterface extends JpaRepository<Event, Long> {

    Event findByName(String name);

    boolean existsEventsByEventDate(LocalDateTime eventDate);

    List<Event> findEventByNameIsLikeIgnoreCase(String name);

    List<Event> findEventsByPlace(Place place);
     Event findEventById(Long id);


    Page<Event> findAllByNameEqualsIgnoreCase(String eventName, Pageable pageable);
}
