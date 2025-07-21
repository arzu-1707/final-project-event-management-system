package com.arzuahmed.ticketingsystem.model.entity;

import com.arzuahmed.ticketingsystem.model.enums.STATUS;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ticket",
        uniqueConstraints = @UniqueConstraint(columnNames =
                {"event_id", "ticket_no"})) // her event_id-e gore ticket_no unikaldir...

public class Ticket {
    //id, user (relation), event (relation), ticketType (relation),
    // purchaseDate, status (confirmed, canceled), ticketNo

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(1)
    @Positive
    @Column(name = "ticket_no")
    private Integer ticketNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    @JsonBackReference
    private Event event;

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_type_id")
    @JsonBackReference
    private TicketType ticketType;

    @JsonFormat(pattern = "dd.MM.yyyy  HH:mm")
    private LocalDateTime purchaseDate;


    @Enumerated(EnumType.STRING)
    private STATUS status;


}
