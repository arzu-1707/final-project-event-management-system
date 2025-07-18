package com.arzuahmed.ticketingsystem.model.entity;

import com.arzuahmed.ticketingsystem.model.enums.STATUS;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@Table(name = "ticket")
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    //id, user (relation), event (relation), ticketType (relation),
    // purchaseDate, status (confirmed, canceled)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Positive
    private Integer ticketNo;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_type_id")
    private TicketType ticketType;

    private LocalDateTime purchaseDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private STATUS status;

}
