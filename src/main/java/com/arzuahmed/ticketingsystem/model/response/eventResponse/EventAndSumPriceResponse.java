package com.arzuahmed.ticketingsystem.model.response.eventResponse;

import com.arzuahmed.ticketingsystem.model.response.ticketResponse.TicketTypeAndPrice;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventAndSumPriceResponse {

    private Long eventId;

    private String eventName;

    @JsonFormat(pattern = "dd.MM.yyyy HH.mm")
    private LocalDateTime eventDate;

    private String placeName;

    private  String location;

   private Double sumPrice;


}
