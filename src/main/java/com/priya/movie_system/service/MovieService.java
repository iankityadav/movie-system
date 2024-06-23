package com.priya.movie_system.service;

import com.priya.movie_system.model.Actor;
import com.priya.movie_system.model.Director;
import com.priya.movie_system.model.Movie;
import com.priya.movie_system.model.ProductionCrew;

import java.util.List;

public interface MovieService {
    public Movie createMovie(Movie movie);

    public Movie getMovieById(Long id);

    public Movie updateMovieById(Long id, Movie movie);

    public void deleteMovieById(Long id);

    public Director getDirectorByMovie(Long movieId);

    public List<Actor> addActorToMovie(Long movieId, Actor actor);

    public List<Actor> getAllActors(Long movieId);

    public List<ProductionCrew> addCrewToMovie(Long movieId, ProductionCrew crew);

    public List<ProductionCrew> getAllProductionCrews(Long movieId);
}
