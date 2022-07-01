package com.api.codingchallenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.codingchallenge.dto.MovieDTO;
import com.api.codingchallenge.model.Comment;
import com.api.codingchallenge.model.Evaluation;
import com.api.codingchallenge.service.MovieService;

@RestController
@RequestMapping("/movies")
public class MovieController {

	@Autowired
	private MovieService movieService;

	@GetMapping("/{titleOrImdbId}")
	public ResponseEntity<MovieDTO> getMovieDetails(@PathVariable(value = "titleOrImdbId") String titleOrImdbId) {
		return ResponseEntity.status(HttpStatus.OK).body(movieService.movieDetails(titleOrImdbId));
	}

	@GetMapping("/comments/{imdbId}")
	public ResponseEntity<Page<Comment>> getMovieReviews(
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) @PathVariable(value = "imdbId") String imdbId,
			Pageable pageable) {
		return ResponseEntity.status(HttpStatus.OK).body(movieService.movieReviews(imdbId, pageable));
	}

	@GetMapping("/evaluations/{imdbId}")
	public ResponseEntity<Page<Evaluation>> getMovieNotes(
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) @PathVariable(value = "imdbId") String imdbId,
			Pageable pageable) {
		return ResponseEntity.status(HttpStatus.OK).body(movieService.movieNotes(imdbId, pageable));
	}

}
