package com.arzuahmed.ticketingsystem.repository;

import com.arzuahmed.ticketingsystem.model.entity.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlaceRepositoryInterface extends JpaRepository<Place, Long> {

    boolean existsByPlaceNameEqualsIgnoreCase(String placeName);

    List<Place> findPlaceByPlaceNameEqualsIgnoreCase(String placeName);


   // List<Place> findPlaceByPlaceNameAndLocation(String placeName, String location);

    Page<Place> findAllByPlaceNameEqualsIgnoreCase(String placeName, Pageable pageable);

    Place findPlaceByPlaceNameAndLocation(String placeName, String location);
}
