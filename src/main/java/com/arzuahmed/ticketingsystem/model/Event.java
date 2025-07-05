package com.arzuahmed.ticketingsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
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

    private String location;

    private LocalDateTime eventDate;

    private Integer  maxTickets;

    private Boolean availableTickets;

}
