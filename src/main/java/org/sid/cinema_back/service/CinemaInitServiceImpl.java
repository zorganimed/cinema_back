package org.sid.cinema_back.service;

import jakarta.transaction.Transactional;
import org.sid.cinema_back.dao.*;
import org.sid.cinema_back.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Service
@Transactional
public class CinemaInitServiceImpl implements ICinemaInitService{

    private VilleRepository villeRepository;
    private CinemaRepository cinemaRepository;
    private SalleRepository salleRepository;
    private PlaceRepository placeRepository;
    private SeanceRepository seanceRepository;
    private FilmRepository filmRepository;
    private ProjectionRepository projectionRepository;
    private TicketRepository ticketRepository;
    private CategorieRepository categorieRepository;

    public CinemaInitServiceImpl(VilleRepository villeRepository, CinemaRepository cinemaRepository,
                                 SalleRepository salleRepository, PlaceRepository placeRepository,
                                 SeanceRepository seanceRepository, FilmRepository filmRepository,
                                 ProjectionRepository projectionRepository, TicketRepository ticketRepository,
                                 CategorieRepository categorieRepository) {
        this.villeRepository = villeRepository;
        this.cinemaRepository = cinemaRepository;
        this.salleRepository = salleRepository;
        this.placeRepository = placeRepository;
        this.seanceRepository = seanceRepository;
        this.filmRepository = filmRepository;
        this.projectionRepository = projectionRepository;
        this.ticketRepository = ticketRepository;
        this.categorieRepository = categorieRepository;
    }

    @Override
    public void initVilles() {
        Stream.of("Tunis","Sousse","Nabeul","Bizerte").forEach(nameVille->{
            Ville ville = new Ville();
            ville.setName(nameVille);
            villeRepository.save(ville);
        });
    }

    @Override
    public void initCinemas() {

        villeRepository.findAll().forEach(v->{
            Stream.of("MegaRama","IMAX","FOUNUON","SHAHRAZAD","DAOULIZ")
                    .forEach(nameCinema->{
                        Cinema cinema = new Cinema();
                        cinema.setName(nameCinema);
                        cinema.setVille(v);
                        cinema.setNombreSalles(5+(int)Math.random()*7);
                        cinemaRepository.save(cinema);
                    });
        });
    }

    @Override
    public void initSalles() {
        cinemaRepository.findAll().forEach(cinema->{
            for (int i = 0; i < cinema.getNombreSalles(); i++) {
                Salle salle = new Salle();
                salle.setNom("Salle "+(i+1));
                salle.setCinema(cinema);
                salle.setNombrePlace(20+(int)Math.random()*10);
                salleRepository.save(salle);
            }
        });


    }

    @Override
    public void initPlaces() {
        salleRepository.findAll().forEach(salle->{
            for (int i = 0; i < salle.getNombrePlace(); i++) {
                Place place = new Place();
               // place.setLatitude(Math.random()*3);
                place.setNumero(i+1);
                place.setSalle(salle);
                placeRepository.save(place);
            }
        });

    }

    @Override
    public void initSeances() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Stream.of("12:00","15:00","17:00","19:00","21:00").forEach(s->{

            Seance seance = new Seance();
            try {
                seance.setHeureDebut(dateFormat.parse(s));
                seanceRepository.save(seance);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        });
    }

    @Override
    public void initCategories() {
        Stream.of("Histoire","Action","Fiction","Drama","Comedy","Culture").forEach(nameCategorie->{
            Categorie categorie = new Categorie();
            categorie.setName(nameCategorie);
            categorieRepository.save(categorie);
        });

    }

    @Override
    public void initFilms() {
        double[] durees = new double[]{1,1.5,2,2.5,3};
        List<Categorie> categories = categorieRepository.findAll();
        Stream.of("Game of Thrones","Seigneur des anneaux","Spider Man","Iron Man","Cat Women").forEach(titreFilm->{
            Film  film = new Film();
            film.setTitre(titreFilm);
            film.setDuree(durees[new Random().nextInt(durees.length)]);
            film.setPhoto(titreFilm.replaceAll(" ","")+".jpg");
            film.setCategorie(categories.get(new Random().nextInt(categories.size())));
            filmRepository.save(film);
        });

    }

    @Override
    public void initProjections() {

        double[] prices = new double[]{30,50,60,70,90,100};
        List<Film> films = filmRepository.findAll();
        villeRepository.findAll().forEach(ville->{
            ville.getCinemas().forEach(cinema -> {
                cinema.getSalles().forEach(salle->{
                     int index = new Random().nextInt(films.size());
                    //filmRepository.findAll().forEach(film->{
                    Film film = films.get(index);
                        seanceRepository.findAll().forEach(seance->{
                            Projection projection = new Projection();
                            projection.setDateProjection(new Date());
                            projection.setFilm(film);
                            projection.setPrix(prices[new Random().nextInt(prices.length)]);
                            projection.setSalle(salle);
                            projection.setSeance(seance);
                            projectionRepository.save(projection);
                        });
                   // });
                });
            });
        });
    }

    @Override
    public void initTickets() {

        projectionRepository.findAll().forEach(projection->{
            projection.getSalle().getPlaces().forEach(place->{
                Ticket ticket = new Ticket();
                ticket.setPlace(place);
                ticket.setPrix(projection.getPrix());
                ticket.setProjection(projection);
                ticket.setReserve(false);
                ticketRepository.save(ticket);
            });
        });
    }
}
