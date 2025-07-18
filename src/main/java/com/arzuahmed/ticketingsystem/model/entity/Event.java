package com.arzuahmed.ticketingsystem.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Builder
@Table(name = "event")
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    //id, name, description, location, eventDate, maxTickets, availableTickets
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    @JsonManagedReference
    private Place place;

    @Column(unique = true)
    private LocalDateTime eventDate;

    @OneToMany(mappedBy = "event", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ticket> tickets;

    private Integer  maxTickets;

    private Boolean availableTickets;

}
