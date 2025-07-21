package com.arzuahmed.ticketingsystem.model.entity;

import com.arzuahmed.ticketingsystem.mapper.Mapper;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @NotNull
    private String name;

    @NotNull
    private String description;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    @JsonManagedReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})   // Lazy olmaga gore exception atirdi ona gore elave etdim..
    private Place place;

    @Column(unique = true)

    @JsonFormat(pattern = "yyyy-MM-dd  HH:mm")
    private LocalDateTime eventDate ;

    @OneToMany(mappedBy = "event", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default  // eger builder etsek classi default deyerler islemeyecek . ona gore bu annotasiya istifade olunur
    @JsonManagedReference
    private List<Ticket> tickets = new ArrayList<>();

    private Integer  maxTickets;

    private Integer availableTickets;

    public List<Ticket> addTickets(){
        for (int i = 1; i <= maxTickets; i++) {
            Ticket ticket = Mapper.ticketMapper();
            ticket.setTicketNo(i);
            ticket.setEvent(this);
            tickets.add(ticket);
        }
        return tickets;
    }

}
