//package com.arzuahmed.ticketingsystem.repository;
//
//import com.arzuahmed.ticketingsystem.model.entity.Event;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import static org.assertj.core.api.Assertions.assertThat;
//
//
//
//import java.time.LocalDateTime;
//
//@DataJpaTest
//@DisplayName("Event Repository tests")
//public class EventRepositoryTest {
//
//    @Autowired
//    private TestEntityManager entityManager;
//
//    @Autowired
//    private EventRepositoryInterface eventRepositoryInterface;
//
//    private Event cinema;
//    private Event konsert;
//    private Event teatr;
//
//    @BeforeEach
//    void setup(){
//        cinema = Event.builder()
//                .name("cinema")
//                .maxTickets(200)
//                .description("Bu bir sinemadir")
//                .build();
//
//        konsert = Event.builder()
//                .name("Aygun Kazimova Konsert")
//                .maxTickets(100)
//                .description("Bu bir Aygun Kazimovanin Konsertidir")
//                .build();
//
//        teatr = Event.builder()
//                .name("kukla teatr")
//                .description("Bu usaqlar ucun nezerde tutulan kukla teatridir")
//                .maxTickets(50)
//                .build();
//    }
//
//    private void persistTestEvents(){
//        entityManager.persist(cinema);
//        entityManager.persist(konsert);
//        entityManager.persist(teatr);
//        entityManager.flush();
//    }
//
//    @Nested
//    @DisplayName("Custom Repository methods")
//    class CustomRepositoryMethodsTests{
//
//        @Test
//        @DisplayName("Returns Event By Name")
//        void findByName() {
//
//            //given
//            persistTestEvents();
//
//            //when
//            Event kuklaTeatr = eventRepositoryInterface.findByName("kukla teatr");
//
//            //then
//            assertThat(kuklaTeatr).isNotNull();
//            assertThat(kuklaTeatr.getName()).isEqualTo("kukla teatr");
//            assertThat(kuklaTeatr.getDescription()).isEqualTo("Bu usaqlar ucun nezerde tutulan kukla teatridir");
//            assertThat(kuklaTeatr.getMaxTickets()).isEqualTo(50);
//        }
//}}
