package com.arzuahmed.ticketingsystem.repository;

import com.arzuahmed.ticketingsystem.model.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlaceRepositoryInterface extends JpaRepository<Place, Long> {

    boolean existsByPlaceNameEqualsIgnoreCase(String placeName);

    List<Place> findPlaceByPlaceNameEqualsIgnoreCase(String placeName);


}
