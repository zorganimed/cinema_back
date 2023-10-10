package org.sid.cinema_back;

import org.sid.cinema_back.service.ICinemaInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CinemaBackApplication implements CommandLineRunner {

    //@Autowired
    private ICinemaInitService iCinemaInitService;

    public CinemaBackApplication(ICinemaInitService iCinemaInitService) {
        this.iCinemaInitService = iCinemaInitService;
    }

    public static void main(String[] args) {
        SpringApplication.run(CinemaBackApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
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
