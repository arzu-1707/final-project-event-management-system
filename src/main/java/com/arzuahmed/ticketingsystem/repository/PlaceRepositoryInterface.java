package com.arzuahmed.ticketingsystem.repository;

import com.arzuahmed.ticketingsystem.model.entity.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceRepositoryInterface extends JpaRepository<Place, Long> {

    boolean existsByPlaceNameEqualsIgnoreCase(String placeName);

    Optional<Place> findByPlaceNameEqualsIgnoreCase(String placeName);

    Page<Place> findAllByPlaceNameEqualsIgnoreCase(String placeName, Pageable pageable);

    Place findPlaceByPlaceNameAndLocation(String placeName, String location);
}
