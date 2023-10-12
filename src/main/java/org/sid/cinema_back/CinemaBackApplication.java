package org.sid.cinema_back;

import org.sid.cinema_back.entities.Film;
import org.sid.cinema_back.entities.Salle;
import org.sid.cinema_back.entities.Ticket;
import org.sid.cinema_back.service.ICinemaInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class CinemaBackApplication implements CommandLineRunner {

    //@Autowired
    private ICinemaInitService iCinemaInitService;
    
    private RepositoryRestConfiguration restConfiguration;

    public CinemaBackApplication(ICinemaInitService iCinemaInitService,
                                 RepositoryRestConfiguration restConfiguration) {
        this.iCinemaInitService = iCinemaInitService;
        this.restConfiguration = restConfiguration;
    }

    public static void main(String[] args) {
        SpringApplication.run(CinemaBackApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        restConfiguration.exposeIdsFor(Film.class, Salle.class, Ticket.class);
        iCinemaInitService.initVilles();
        iCinemaInitService.initCinemas();
        iCinemaInitService.initSalles();
        iCinemaInitService.initPlaces();
        iCinemaInitService.initSeances();
        iCinemaInitService.initCategories();
        iCinemaInitService.initFilms();
        iCinemaInitService.initProjections();
        iCinemaInitService.initTickets();

    }
}
