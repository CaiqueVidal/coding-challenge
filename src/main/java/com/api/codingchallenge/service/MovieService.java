package com.api.codingchallenge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.api.codingchallenge.dto.MovieDTO;
import com.api.codingchallenge.model.Comment;
import com.api.codingchallenge.model.Evaluation;
import com.api.codingchallenge.repository.CommentRepository;
import com.api.codingchallenge.repository.EvaluationRepository;
import com.api.codingchallenge.service.exception.ResourceNotFoundException;

import reactor.core.publisher.Mono;

@Service
public class MovieService {

	@Value("${omdbapi-key}")
	private String odbApiKey;

	@Autowired
	private WebClient webClientMovies;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private EvaluationRepository evaluationRepository;

	public MovieDTO movieDetails(String titleOrImdbId) {
		return findMovieByTitleOrImdbId(titleOrImdbId);
	}

	public Page<Comment> movieReviews(String imdbId, Pageable pageable) {
		return commentRepository.findByImdbId(imdbId, pageable);
	}

	public Page<Evaluation> movieNotes(String imdbId, Pageable pageable) {
		return evaluationRepository.findByImdbId(imdbId, pageable);
	}

	public MovieDTO findMovieByTitleOrImdbId(String titleOrImdbId) {
		Mono<MovieDTO> monoDTOByTitle = this.webClientMovies.get().uri("?apikey=" + odbApiKey + "&t=" + titleOrImdbId)
				.retrieve().bodyToMono(MovieDTO.class);

		Mono<MovieDTO> monoDTOByImdbId = this.webClientMovies.get().uri("?apikey=" + odbApiKey + "&i=" + titleOrImdbId)
				.retrieve().bodyToMono(MovieDTO.class);

		MovieDTO movieDTO = Mono.zip(monoDTOByTitle, monoDTOByImdbId).map(tuple -> {
			if (tuple.getT1().getResponse())
				return tuple.getT1();

			if (tuple.getT2().getResponse())
				return tuple.getT2();

			throw new ResourceNotFoundException("Movie not found " + titleOrImdbId);
		}).block();

		return movieDTO;
	}

	public MovieDTO findMovieByImdbId(String ImdbId) {
		Mono<MovieDTO> monoDTOByImdbId = this.webClientMovies.get().uri("?apikey=" + odbApiKey + "&i=" + ImdbId)
				.retrieve().bodyToMono(MovieDTO.class);

		MovieDTO movieDTO = monoDTOByImdbId.block();

		if (movieDTO.getResponse())
			return movieDTO;

		throw new ResourceNotFoundException("Movie not found " + ImdbId);
	}

}
