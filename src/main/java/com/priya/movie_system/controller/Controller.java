package com.priya.movie_system.controller;

import com.priya.movie_system.model.Actor;
import com.priya.movie_system.model.Director;
import com.priya.movie_system.model.Movie;
import com.priya.movie_system.model.ProductionCrew;
import com.priya.movie_system.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/api")
@RestController
public class Controller {

    @Autowired
    private MovieService movieService;

    @PostMapping("/movies")
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        return new ResponseEntity<>(movieService.createMovie(movie), HttpStatus.CREATED);
    }

    @GetMapping("/movies/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable Long id) {
        return new ResponseEntity<>(movieService.getMovieById(id), HttpStatus.OK);
    }

    @PutMapping("/movies/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie movie) {
        return new ResponseEntity<>(movieService.updateMovieById(id, movie), HttpStatus.OK);
    }

    @DeleteMapping("/movies/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovieById(id);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    @GetMapping("/movies/{movieId}/director")
    public ResponseEntity<Director> getMovieDirector(@PathVariable Long movieId) {
        return new ResponseEntity<>(movieService.getDirectorByMovie(movieId), HttpStatus.OK);
    }

    @GetMapping("/movies/{movieId}/actors")
    public ResponseEntity<List<Actor>> getMovieActors(@PathVariable Long movieId) {
        return new ResponseEntity<>(movieService.getAllActors(movieId), HttpStatus.OK);
    }

    @GetMapping("/movies/{movieId}/crews")
    public ResponseEntity<List<ProductionCrew>> getMovieProductionCrews(@PathVariable Long movieId) {
        return new ResponseEntity<>(movieService.getAllProductionCrews(movieId), HttpStatus.OK);
    }

    @PostMapping("/movies/{movieId}/actors")
    public ResponseEntity<List<Actor>> addActor(@PathVariable Long movieId, @RequestBody Actor actor) {
        return new ResponseEntity<>(movieService.addActorToMovie(movieId, actor), HttpStatus.OK);
    }

    @PostMapping("/movies/{movieId}/crews")
    public ResponseEntity<List<ProductionCrew>> addCrew(@PathVariable Long movieId, @RequestBody ProductionCrew crew) {
        return new ResponseEntity<>(movieService.addCrewToMovie(movieId, crew), HttpStatus.OK);
    }

}
