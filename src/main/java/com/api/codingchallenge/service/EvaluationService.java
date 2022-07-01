package com.api.codingchallenge.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.codingchallenge.dto.EvaluationDTO;
import com.api.codingchallenge.dto.EvaluationUpdateDTO;
import com.api.codingchallenge.dto.MovieDTO;
import com.api.codingchallenge.model.Evaluation;
import com.api.codingchallenge.model.User;
import com.api.codingchallenge.repository.EvaluationRepository;
import com.api.codingchallenge.service.exception.PSQLException;
import com.api.codingchallenge.service.exception.ResourceNotFoundException;

@Service
public class EvaluationService {

	@Autowired
	private EvaluationRepository evaluationRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private MovieService movieService;

	@Transactional
	public Evaluation save(EvaluationDTO evaluationDTO) {
		User user = userService.getCurrentUser();

		Optional<Evaluation> evaluationOptional = evaluationRepository.findByUserAndImdbId(user,
				evaluationDTO.getImdbId());
		if (evaluationOptional.isPresent())
			throw new PSQLException(
					"It is not possible to give 2 ratings for the same movie " + evaluationDTO.getImdbId());

		MovieDTO movieDTO = movieService.findMovieByImdbId(evaluationDTO.getImdbId());

		var evaluation = new Evaluation();
		evaluation.setNote(evaluationDTO.getNote());
		evaluation.setImdbId(movieDTO.getImdbId());
		evaluation.setUser(user);

		userService.addScore(user.getId());

		return evaluationRepository.save(evaluation);
	}

	@Transactional
	public Evaluation update(Long evaluationId, EvaluationUpdateDTO evaluationUpdateDTO) {
		try {
			User user = userService.getCurrentUser();
			Evaluation evaluation = evaluationRepository.findById(evaluationId).get();
			if (evaluation.getUser() != user)
				throw new PSQLException("Can't update other user's note.");

			evaluation.setNote(evaluationUpdateDTO.getNote());
			return evaluationRepository.save(evaluation);
		} catch (EntityNotFoundException | NoSuchElementException e) {
			throw new ResourceNotFoundException("Evaluation id not found " + evaluationId);
		}
	}

}
