package com.arzuahmed.ticketingsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.EnableMBeanExport;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "tickerType")
@AllArgsConstructor
@NoArgsConstructor
public class TicketType {
   // id, event (relation), typeName (VIP, Regular), price, quantity

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   @ManyToOne
   @JoinColumn(name = "event_id")
   private Event event;

    private enum typeName{
        VIP, REGULAR
    };

    private Double price;

    private Integer quantity;
}
