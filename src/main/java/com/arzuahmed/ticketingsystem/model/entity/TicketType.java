package com.arzuahmed.ticketingsystem.model.entity;

import com.arzuahmed.ticketingsystem.model.enums.TICKETTYPENAME;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "ticket_Type")
@AllArgsConstructor
@NoArgsConstructor
public class TicketType {
   // id, event (relation), typeName (VIP, Regular), price

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   @ManyToOne
   @JoinColumn(name = "event_id")
   private Event event;

    @Enumerated(EnumType.STRING)   // deyerin enum tipinde oldugunu ve daxilindekilerin String oldugunu mueyyen eden annotasiyadir
    private TICKETTYPENAME ticketTypeName;

    private Double price;

}
