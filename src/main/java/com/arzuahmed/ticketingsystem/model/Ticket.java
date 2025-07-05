package com.arzuahmed.ticketingsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.apache.bcel.generic.LOOKUPSWITCH;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "ticker")
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    //id, user (relation), event (relation), ticketType (relation),
    // purchaseDate, status (confirmed, canceled)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<User> user;

    @ManyToOne
    private Event event;

    @OneToOne
    private TicketType ticketType;

    private LocalDateTime purchaseDate;

    private enum status{
        CONFIRMED,
        CANCELED
    };

}
