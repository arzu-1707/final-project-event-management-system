package com.arzuahmed.ticketingsystem.model.dto.ticketDTO;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketCreateDTO {

    @Column(unique = true)
    private Integer ticketCount;

    private Long ticketTypeId;


}
