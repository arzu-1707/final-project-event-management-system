package com.arzuahmed.ticketingsystem.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "place")
public class Place {
    //id, placeName, description, event
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String placeName;

    private String location;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Event> events = new ArrayList<>();

    //sayi yazmaq
    private Integer seatCapacity;

    public void addEvent(Event event){
        events.add(event);
        event.setPlace(this);
    }

    public void removeEvent(Event event){
        events.remove(event);
        event.setPlace(null);
    }

}
