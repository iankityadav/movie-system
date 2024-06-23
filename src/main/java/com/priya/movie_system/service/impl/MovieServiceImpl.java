package com.priya.movie_system.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.priya.movie_system.model.Actor;
import com.priya.movie_system.model.Director;
import com.priya.movie_system.model.Movie;
import com.priya.movie_system.model.ProductionCrew;
import com.priya.movie_system.repository.MovieRepository;
import com.priya.movie_system.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public Movie createMovie(Movie movie) {
        Movie newMovie = new Movie();
        newMovie.setGenre(movie.getGenre());
        newMovie.setTitle(movie.getTitle());
        newMovie.setReleaseYear(movie.getReleaseYear());
        ObjectMapper mapper = new ObjectMapper();

        // Set Director
        Director director = movie.getDirector();
        director.setMovie(newMovie);
        newMovie.setDirector(director);

        //Set Actor
        Set<Actor> actors = new HashSet<>();
        for(Actor actor: movie.getActors()){
            actor.setMovie(newMovie);
            actors.add(actor);
        }
        newMovie.setActors(actors);

        // Set Crew
        Set<ProductionCrew> productionCrews = new HashSet<>();
        for(ProductionCrew productionCrew: movie.getProductionCrews()){
            productionCrew.getMovies().add(newMovie);
            productionCrews.add(productionCrew);
        }
        newMovie.setProductionCrews(productionCrews);
        System.out.println(newMovie);
        Movie m = movieRepository.save(newMovie);
        System.out.println(m);
        return m;
    }

    @Override
    public Movie getMovieById(Long id) {
        return null;
    }

    @Override
    public Movie updateMovieById(Long id) {
        return null;
    }

    @Override
    public void deleteMovieById(Long id) {

    }

    @Override
    public Director getDirectorByMovie(Long movieId) {
        return null;
    }

    @Override
    public Actor addActorToMovie(Long movieId) {
        return null;
    }

    @Override
    public List<Actor> getAllActors(Long movieId) {
        return List.of();
    }

    @Override
    public ProductionCrew addCrewToMovie(Long movieId) {
        return null;
    }

    @Override
    public List<ProductionCrew> getAllProductionCrews(Long movieId) {
        return List.of();
    }
}
