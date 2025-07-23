package com.arzuahmed.ticketingsystem.model.dto.eventDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDateDTO {
    @JsonFormat(pattern = "yyyy.MM.dd  HH:mm")
    @Future(message = "Event tarixi kecmisde ola bilmez...")
    private LocalDateTime eventDate;
}
