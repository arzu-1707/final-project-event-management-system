package com.arzuahmed.ticketingsystem.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    //id, placeName, location, event, seatCapacity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String placeName;

    private String location;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    @Builder.Default
    private List<Event> events = new ArrayList<>();

    //oturacaq yerlerinin sayi sayi yazmaq
    private Integer seatCapacity;

    public void addEvent(Event event){
        events.add(event);
    }

    public void removeEvent(Event event){
        events.remove(event);
        event.setPlace(null);
    }

}
