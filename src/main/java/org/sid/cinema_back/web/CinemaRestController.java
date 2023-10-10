package org.sid.cinema_back.web;

import jakarta.transaction.Transactional;
import lombok.Data;
import org.sid.cinema_back.dao.FilmRepository;
import org.sid.cinema_back.dao.TicketRepository;
import org.sid.cinema_back.entities.Film;
import org.sid.cinema_back.entities.Ticket;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")//@CrossOrigin("http://localhost:4200/")
public class CinemaRestController {

    private FilmRepository filmRepository;
    private TicketRepository ticketRepository;

    public CinemaRestController(FilmRepository filmRepository, TicketRepository ticketRepository) {
        this.filmRepository = filmRepository;
        this.ticketRepository = ticketRepository;
    }

    @GetMapping(path = "/imageFilm/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] image(@PathVariable(name = "id") Long id) throws IOException {
        Film film = filmRepository.findById(id).get();
        String photoName = film.getPhoto();
        File file = new File(System.getProperty("user.home")+"/cinema/images/"+photoName);
        Path path = Paths.get(file.toURI());

        return Files.readAllBytes(path);
    }

    @PostMapping("/payerTickets")
    @Transactional
    public List<Ticket> payerTickets(@RequestBody TicketForm ticketForm){

        System.out.println("inside payerTickets");
        List<Ticket> ls = new ArrayList<>();
        ticketForm.getTickets().forEach(idTicket->{
            System.out.println(idTicket);
            Ticket ticket = ticketRepository.findById(idTicket).get();
            ticket.setNomClient(ticketForm.getNomClient());
            ticket.setReserve(true);
            ticket.setCodePayement(ticketForm.getCodePayment());
            ticketRepository.save(ticket);
            ls.add(ticket);
        });
        return ls;
    }
}

@Data
class TicketForm {

    private String nomClient;
    private int codePayment;
    private List<Long> tickets = new ArrayList<>();

}
