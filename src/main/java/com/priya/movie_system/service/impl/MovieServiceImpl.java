package com.priya.movie_system.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.priya.movie_system.model.Actor;
import com.priya.movie_system.model.Director;
import com.priya.movie_system.model.Movie;
import com.priya.movie_system.model.ProductionCrew;
import com.priya.movie_system.repository.ActorRepository;
import com.priya.movie_system.repository.DirectorRepository;
import com.priya.movie_system.repository.MovieRepository;
import com.priya.movie_system.repository.ProductionCrewRepository;
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
        return movieRepository.findById(id).orElseThrow();
    }

    /*
     * To update the movie, check if the movie with Id present then set the same ID to use same record to update
     * instead of  creating new record
     * */
    @Override
    public Movie updateMovieById(Long id, Movie movie) {
        Movie existingMovie = getMovieById(id);
        existingMovie.setGenre(movie.getGenre());
        existingMovie.setTitle(movie.getTitle());
        existingMovie.setReleaseYear(movie.getReleaseYear());

        // Update Director
        Director newDirector = movie.getDirector();
        if (newDirector != null) {
            // If there is an existing director, update it, else set the new director
            if (existingMovie.getDirector() != null) {
                Director existingDirector = existingMovie.getDirector();
                existingDirector.setName(newDirector.getName());
                existingDirector.setBiography(newDirector.getBiography());
                existingMovie.setDirector(existingDirector);
            } else {
                newDirector.setMovie(existingMovie);
                existingMovie.setDirector(newDirector);
            }
        } else {
            existingMovie.setDirector(null);
        }

        //Set Actor
        Set<Actor> actors = new HashSet<>();
        for (Actor actor : movie.getActors()) {
            actor.setMovie(existingMovie);
            actors.add(actor);
        }
        // Remove actors that are not in the updated list
        existingMovie.getActors().removeIf(actor -> !actors.contains(actor));
        existingMovie.getActors().addAll(actors);

        // Set Crew
        Set<ProductionCrew> productionCrews = new HashSet<>();
        for (ProductionCrew productionCrew : movie.getProductionCrews()) {
            productionCrew.getMovies().add(existingMovie);
            productionCrews.add(productionCrew);
        }
        // Remove production crews that are not in the updated list
        existingMovie.getProductionCrews().removeIf(productionCrew -> !productionCrews.contains(productionCrew));
        existingMovie.getProductionCrews().addAll(productionCrews);

        System.out.println(existingMovie);
        return movieRepository.save(existingMovie);
    }

    @Override
    public void deleteMovieById(Long id) {
        movieRepository.delete(getMovieById(id));
    }

    @Override
    public Director getDirectorByMovie(Long movieId) {
        return getMovieById(movieId).getDirector();
    }

    @Override
    public List<Actor> addActorToMovie(Long movieId, Actor actor) {
        Movie exitingMovie = getMovieById(movieId);
        exitingMovie.getActors().add(actor);
        return updateMovieById(movieId, exitingMovie).getActors().stream().toList();
    }

    @Override
    public List<Actor> getAllActors(Long movieId) {
        return getMovieById(movieId).getActors().stream().toList();
    }

    @Override
    public List<ProductionCrew> addCrewToMovie(Long movieId, ProductionCrew crew) {
        Movie exitingMovie = getMovieById(movieId);
        exitingMovie.getProductionCrews().add(crew);
        return updateMovieById(movieId, exitingMovie).getProductionCrews().stream().toList();
    }

    @Override
    public List<ProductionCrew> getAllProductionCrews(Long movieId) {
        return getMovieById(movieId).getProductionCrews().stream().toList();
    }

}
