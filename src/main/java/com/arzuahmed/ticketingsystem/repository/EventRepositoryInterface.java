package com.arzuahmed.ticketingsystem.repository;

import com.arzuahmed.ticketingsystem.model.entity.Event;
import com.arzuahmed.ticketingsystem.model.entity.Place;
import com.arzuahmed.ticketingsystem.model.response.eventResponse.EventResponseDTO;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepositoryInterface extends JpaRepository<Event, Long> {

    Event findByName(String name);

    boolean existsEventsByEventDate(LocalDateTime eventDate);

    List<Event> findEventByNameIsLikeIgnoreCase(String name);

    List<Event> findEventsByPlace(Place place);
     Optional<Event> findEventById(Long id);


    Page<Event> findAllByNameEqualsIgnoreCase(String eventName, Pageable pageable);

    List<Event> findEventsByNameIgnoreCase(String eventName);

    List<Event> findAllByEventDateBetween(@Future(message = "Event tarixi kecmisde ola bilmez...") LocalDateTime eventDateAfter, @Future(message = "Event tarixi kecmisde ola bilmez...") LocalDateTime eventDateBefore);

    @Query("SELECT e FROM Event e WHERE e.eventDate BETWEEN :startDate AND :endDate ORDER BY e.eventDate ASC")
    List<Event> findByEventDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);


    Page<Event> findEventsByEventDateBetween(@Future(message = "Event tarixi kecmisde ola bilmez...") LocalDateTime eventDateAfter, @Future(message = "Event tarixi kecmisde ola bilmez...") LocalDateTime eventDateBefore, Pageable pageable);
}
