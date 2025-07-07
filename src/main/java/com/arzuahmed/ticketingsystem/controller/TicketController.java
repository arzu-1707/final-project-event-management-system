package com.arzuahmed.ticketingsystem.controller;

import com.arzuahmed.ticketingsystem.model.dto.TicketDTO;
import com.arzuahmed.ticketingsystem.model.entity.Ticket;
import com.arzuahmed.ticketingsystem.service.TicketService;
import com.arzuahmed.ticketingsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/tickets")
public class TicketController {

    private final TicketService ticketService;

    //3. POST /v1/tickets
//Məqsəd: Yeni bilet yaratmaq (satışa çıxarmaq).
//Uyğundur? Bəli, yeni biletlərin əlavə olunması üçün əsasdır.
//
//4. PUT /v1/tickets/{ticketId}
//Məqsəd: Mövcud bileti tamamilə yeniləmək.
//Uyğundur? Bəli, bilet məlumatlarında tam dəyişiklik etmək üçün lazım ola bilər.
//
//5. PATCH /v1/tickets/{ticketId}
//Məqsəd: Mövcud bileti qismən yeniləmək.
//Uyğundur? Bəli, yalnız bir və ya bir neçə sahəni dəyişmək üçün rahatdır.
//
//6. DELETE /v1/tickets/{ticketId}
//Məqsəd: Bileti sistemdən silmək (məsələn, ləğv etmək).
//Uyğundur? Əgər sistemdə bilet silmək və ya ləğv etmək funksionallığı varsa, bəli.
//
//Ümumi nəticə:
//GET (hamısını və birini əldə etmək), POST (yaratmaq), PUT/PATCH (tam və qismən yeniləmək) əməliyyatları mütləq lazımdır.
//DELETE isə layihənin tələbinə görə əlavə oluna bilər.
    @GetMapping
    public List<Ticket> findAllTickets() {
        return ticketService.findAll();
    }

    @GetMapping("/{ticketId}")
    public Ticket findTicketById(@PathVariable Long ticketId){
        return ticketService.findTicketById(ticketId);
    }

   // @PostMapping
    //public ResponseEntity<Ticket> addTicket(@RequestBody TicketDTO ticketDTO){
     //  return ticketService.addTicket(ticketDTO);
    //}


   // @PutMapping

   // @PatchMapping




}
