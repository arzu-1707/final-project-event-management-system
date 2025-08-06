package com.arzuahmed.ticketingsystem.controller;

import com.arzuahmed.ticketingsystem.model.PageClass;
import com.arzuahmed.ticketingsystem.model.dto.eventDTO.EventDTO;
import com.arzuahmed.ticketingsystem.model.dto.eventDTO.EventDateDTO;
import com.arzuahmed.ticketingsystem.model.dto.placeDTO.PlaceIdDTO;
import com.arzuahmed.ticketingsystem.model.dto.ticketDTO.TicketTypeDTO;
import com.arzuahmed.ticketingsystem.model.response.CommonResponse;
import com.arzuahmed.ticketingsystem.model.dto.placeDTO.PlaceDTO;
import com.arzuahmed.ticketingsystem.model.dto.userDTO.UserEmailDTO;
import com.arzuahmed.ticketingsystem.model.response.eventResponse.EventAndPlaceResponseDTO;
import com.arzuahmed.ticketingsystem.model.response.eventResponse.EventAndSumPriceResponse;
import com.arzuahmed.ticketingsystem.model.response.eventResponse.EventAndTicketsResponseDTO;
import com.arzuahmed.ticketingsystem.model.response.eventResponse.EventResponseDTO;
import com.arzuahmed.ticketingsystem.model.response.placeResponse.PlaceResponse;
import com.arzuahmed.ticketingsystem.model.response.userResponse.UserInfoResponse;
import com.arzuahmed.ticketingsystem.model.response.userResponse.UserResponse;
import com.arzuahmed.ticketingsystem.model.wrapper.EventWithPlaceIdAndTicketTypeListDTO;
import com.arzuahmed.ticketingsystem.repository.PlaceRepositoryInterface;
import com.arzuahmed.ticketingsystem.service.impl.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Tag(name = "Admin Operations", description = "Bu metodlar ancaq admin ucundur")
@RestController
@RequestMapping("v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final EventService eventService;
    private final PlaceService placeService;
    private final TicketTypeService ticketTypeService;
    private final PlaceRepositoryInterface placeRepositoryInterface;


    //-------------------------------User ile bagli---------------------------------------------------------

    //butun user-lere baxmaq     ++++Postmanda+++  security
    @Operation(summary = "Butun Userler",description = "Butun Userler ve onlar haqqinda informasiya getirmek",
            tags = {"Admin Operations"},security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping("/all-users")
    public ResponseEntity<CommonResponse<PageClass<UserInfoResponse>>> getAllUsers(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam boolean asc
    ) {
        Sort sort = asc ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber,pageSize, sort);
        Page<UserInfoResponse> user = userService.findAllUsers(pageable);

        PageClass<UserInfoResponse> customPage = new PageClass<>(user);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success("Emeliyyat Ugurla yerine yetirildi...",customPage));

    }

    //id-e gore user axtarisi     ++++++Postman+++++   security

    @GetMapping("/user-id/{userId}")
    @Operation(summary = "UserId-e gore User axtarisi",
            description = "UserId-e gore User-in informasiyasini getirmek",
            tags = {"Admin Operations"}, security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<CommonResponse<UserResponse>> getUserById(@PathVariable Long userId){
        UserResponse userResponse =  userService.findUserById(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success("Id-si "+ userId +" olan istifadeci tapildi..",userResponse));
    }

    //ada gore user axtarisi   ++++Postman+++++  security
    @Operation(summary = "UserName-e gore axtaris",
            description = "UserName-e gore User-in melumatlarini getirir",
            tags = {"Admin Operations"}, security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping("/user-name")
    public ResponseEntity<CommonResponse<PageClass<UserResponse>>> getUserByUserName(
            @RequestParam(name = "name") String userName,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam boolean asc){
        Sort sort = asc ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable =PageRequest.of(pageNumber,pageSize, sort);
        Page<UserResponse> users = userService.findUserByUserName(userName, pageable);

        PageClass<UserResponse> user = new PageClass<>(users);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success("Ugurla basa catdi...",user));
    }

    //email-e gore user axtarisi    ++++++Postman++++++  security
    @Operation(summary = "UserEmail-e gore axtaris",
            description = "User-in Email-ne gore User-in melumatlarini getirir",
            tags = {"Admin Operations"},security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping("/user-email")
    public ResponseEntity<CommonResponse<UserResponse>> getUserByUserEmail(@RequestBody UserEmailDTO userEmailDTO){
        UserResponse user = userService.findUserResponseByEmail(userEmailDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success(userEmailDTO.getEmail()+ " emaile sahib istifadeci tapildi",
                        user));
    }

    //id-e gore user silmek    +++++Postman+++++     security
    @Operation(summary = "UserId-e gore User-in silinmesi",
            description = "User-in Id-ne gore User-i silir. " +
                    "Bu metod User-in hesabini deaktiv etmek ucun de istifade olunur",
            tags = {"Admin Operations"}, security = @SecurityRequirement(name = "bearerAuth")
    )
    @DeleteMapping("/user-id/{userId}")
    public ResponseEntity<CommonResponse<Void>> deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success("Id-si "+userId + " olan istifadeci ugurla silindi..",
                        null));
    }


    //Butun userleri silmek   security
    @Operation(summary = "Butun Userlerin silinmesi",
            description = "Butun Userler ve onlar haqqinda informasiya silmek ucun istifade olunur",
            tags = {"Admin Operations"}, security = @SecurityRequirement(name = "bearerAuth")
    )
    @DeleteMapping("/all-users")
    public ResponseEntity<CommonResponse<Void>> deleteAllUsers(){
        userService.deleteAllUsers();
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success("Butun userler silindi..", null));
    }



    //---------------------------------------------------------------------------------------------------------

    //----------------------------------------Event ile bagli--------------------------------------------------

    //Event Yaratmaq   ++++++ Postman+++    security
    @Operation(summary = "Yeni Event yaratmaq",description = "Yeni bir event yaradir",
            tags = {"Admin Operations"},security = @SecurityRequirement(name = "bearerAuth")
    )
    @PostMapping("/create/event")
    public ResponseEntity<CommonResponse<EventResponseDTO>> createEvent(@RequestBody @Valid EventDTO eventDTO){
       EventResponseDTO eventResponseDTO = eventService.createEvent(eventDTO);
       return ResponseEntity.status(HttpStatus.CREATED)
               .body(CommonResponse.success("Event ugurla elave edilmisdir...",eventResponseDTO));
    }

    //Movcud event-a movcud place-i elave etmek  ++++Postman+++   security
    @Operation(summary = "Movcud Event-in Place ile elaqelendirilmesi",
            description = "Evvelce EventId-e gore Event-in ve PlaceId-e gore Place-nin olub olmamasini yoxlayir," +
                    " sonra Event-i Place ile elaqelendirir ",
            tags = {"Admin Operations"}, security = @SecurityRequirement(name = "bearerAuth")
    )
    @PatchMapping("/events/{eventId}")
    public ResponseEntity<CommonResponse<EventAndPlaceResponseDTO>> addPlaceInEvent(@PathVariable Long eventId,
                                                                                    @RequestBody PlaceIdDTO placeIdDTO){
        EventAndPlaceResponseDTO eventAndPlaceResponseDTO = eventService.addPlaceInEvent(eventId, placeIdDTO.getPlaceId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.success(eventAndPlaceResponseDTO.getName()
                        + " adli Event-a ID-si " + eventAndPlaceResponseDTO.getPlaceId()
                        + " olan Place ugurla elave edildi..",eventAndPlaceResponseDTO));
    }

    //Event-in Place deyismek (movcud bir place ile)   ++++Postman+++   security
    @Operation(summary = "Event-in Place-ni deyisdirilmesi",
            description = "EventId-e gore Event-in Place-ni deyisir",
            tags = {"Admin Operations"}, security = @SecurityRequirement(name = "bearerAuth")
    )
    @PutMapping("/events/{eventId}/update-place")
    public ResponseEntity<CommonResponse<EventAndPlaceResponseDTO>> updatePlaceInEvent(@PathVariable Long eventId,
                                                                                       @RequestBody PlaceDTO placeDTO){
        EventAndPlaceResponseDTO event =eventService.updatePlace(eventId, placeDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.success(event.getName()
                        + " adli Event-a ID-si " + event.getPlaceId()
                        + " olan Place ile ugurla deyisdirildi...", event));

    }

    //Movcud Event-a TicketType ve count-a gore ticket elave edilmesi   +++++Postman++  security
    @Operation(summary = "Movcud Event-e Ticket elave edilmesi",
            description = "Movcud Event-e Ticketler elave edir",
            tags = {"Admin Operations"},security = @SecurityRequirement(name = "bearerAuth")
    )
    @PatchMapping("/events/{eventId}/add-tickets-ticketType")
    public ResponseEntity<CommonResponse<EventAndTicketsResponseDTO>> addTicketsAndTicketTypeInEvent(
            @PathVariable Long eventId,
            @RequestBody List<TicketTypeDTO> ticketTypeDTO){
        EventAndTicketsResponseDTO event = eventService.addTicketType(eventId, ticketTypeDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.success("Evente biletler elave edildi...",event));
    }


    //Yeni Event, TicketType (VIP, Regular ve ya tekce bir tipe uygun) ve ticketler      ++++Postman+++  security
    @Operation(summary = "Yeni Event, Biletlerin novlerine uygun Biletlerin yaradilmasi ",
            description = "Yeni event yaradir ve biletletrin novune gore ticketleri ellave edir." +
                    " Bu bir tipe de, muxtelif tipe de aid ola biler",
            tags = {"Admin Operations"}, security = @SecurityRequirement(name = "bearerAuth")
    )
    @PostMapping("/create/event/tickets/ticketType")
    public ResponseEntity<CommonResponse<EventAndTicketsResponseDTO>> createEventTicketTicketType(
            @RequestBody EventWithPlaceIdAndTicketTypeListDTO eventTicketTypeListTicket){

        EventAndTicketsResponseDTO event = eventService.createEventTicketWithTicketType(eventTicketTypeListTicket);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.success("Event biletler ile birlikde ugurla yaradildi...",event));
    }

    //Event-in Available ticket sayina baxmaq ucun  ++Postman++++  security
    @Operation(summary = "Event-de satilmamis biletlerin sayi",
            description = "Event-de satilmamis biletlerin sayini gosterir",
            tags = {"Admin Operations"}, security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping("/events/{eventId}/available-ticket/count")
    public ResponseEntity<CommonResponse<Integer>> findCountAvailableTickets(@PathVariable Long eventId){
        Integer count = eventService.findCountAvailableTickers(eventId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success("Movcud bilet sayi: " + count, count));
    }

    //Event tarixini deyisdirmek    +++++Postman+++  Security
    @Operation(summary = "Event-in tarixini deyismek",
            description = "Event-in kecireceyi vaxti deyismek ucun istifade olunur",
            tags = {"Admin Operations"}, security = @SecurityRequirement(name = "bearerAuth")
    )
    @PatchMapping("/events/{eventId}/update-event-date")
    public ResponseEntity<CommonResponse<EventResponseDTO>> updateEventDate(@PathVariable Long eventId,
                                                                            @RequestBody @Valid EventDateDTO eventDateDTO){
        EventResponseDTO event = eventService.updateEventDate(eventId, eventDateDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success("Event-in tarixi ugurla deyisdirildi..",event));
    }


    //Event-in gelirini hesablamaq       security
    @Operation(summary = "Event-in biletlerinin(satilmis) qiymetini hesablayir",
            description = "Event-in satilmis (status.Sold olan) biletlerin qiymetlerini toplayir",
            tags = {"Admin Operations"}, security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping("/events/{eventId}/sum-price")
    public ResponseEntity<CommonResponse<EventAndSumPriceResponse>> calculatePrice(@PathVariable long eventId){
        EventAndSumPriceResponse event = eventService.calculatePrice(eventId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success("Event-e uygun umumi qiymet hesablandi: "+ event.getSumPrice(),
                        event));
    }




    //Event Silmek  ++++++Postman+++  security
    @Operation(
            summary = "Event silmək",
            description = "Verilmiş ID-ə əsasən bir event-i silir",
            tags = {"Admin Operations"}, security = @SecurityRequirement(name = "bearerAuth")
    )
    @DeleteMapping("/events/{eventId}")
    public ResponseEntity<CommonResponse<Void>> deleteEventById(@PathVariable Long eventId){
        eventService.deleteEvent(eventId);
        return ResponseEntity.ok(CommonResponse.success("Id-si " + eventId
                + " olan Event ugurla silinmisdir...", null));
    }

    //Butun eventleri silme   Security
    @Operation(
            summary = "Bütün Eventləri silmək",
            description = "Sistemdəki bütün Event-ləri silir. Bu əməliyyat diqqətlə istifadə olunmalıdır.",
            tags = {"Admin Operations"}, security = @SecurityRequirement(name = "bearerAuth")
    )
    @DeleteMapping("/all-events")
    public ResponseEntity<CommonResponse<Void>> deleteAllEvents(){
        eventService.deleteAllEvents();
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success("Butun Eventler ugurla silindi...", null));
    }

//----------------------------------------------------------------------------------------------------------------



//---------------------------------------------Place ile bagli----------------------------------------------------

    //Place Elave etmek   +++++Postman+++++
    @Operation(summary = "Place yaratmaq", description = "Yeni Place yaratmaq ucun istifade olunur",
    tags = {"Admin Operations"}, security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/create/place")
    public ResponseEntity<CommonResponse<PlaceResponse>> createPlace(@RequestBody @Valid PlaceDTO placeDTO ){
        PlaceResponse placeResponse = placeService.createPlace(placeDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.success("Place ugurla yaradildi...", placeResponse));
    }

    //place Id-e gore axtaris +++++Postman+++++
    @Operation(summary = "Place-in Id-ne gore axtaris",
            description = "Place-in Id-ne gore axtaris etmek ucun istifade olunur",
            tags = {"Admin Operations"}, security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/places/{placeId}")
    public ResponseEntity<CommonResponse<PlaceResponse>> findPlaceById(@PathVariable Long placeId){
        PlaceResponse place = placeService.findPlaceById(placeId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success("Id-si " + placeId + " olan place tapildi..", place));
    }


    //place silmek   +++++Postman++++
    @Operation(summary = "Bir Place silmek", description = "Place-in Id-ne gore bir Place silir",
            tags = {"Admin Operations"}, security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("/places/{placeId}")
    public ResponseEntity<CommonResponse<Void>> deletePlaceById(@PathVariable Long placeId){
        placeService.deletePlaceById(placeId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success("Id-si " +placeId +" olan Place silindi...", null));
    }

    //Umumi place-leri silmek   +++++Postman++++
    @Operation(summary = "Butun Place-leri silmek", description = "Sistemde olan butun Place-leri silir.",
            tags = {"Admin Operations"}, security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("/all-places")
    public ResponseEntity<CommonResponse<Void>> deleteAllPlaces(){
        placeRepositoryInterface.deleteAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success("Butun Place-ler Sistemden silindi", null));
    }



//------------------------------------------------------------------------------------------------------------




}
