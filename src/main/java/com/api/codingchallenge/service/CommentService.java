package com.api.codingchallenge.service;

import java.util.NoSuchElementException;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.api.codingchallenge.dto.CommentDTO;
import com.api.codingchallenge.dto.CommentUpdateDTO;
import com.api.codingchallenge.dto.MovieDTO;
import com.api.codingchallenge.model.Comment;
import com.api.codingchallenge.model.User;
import com.api.codingchallenge.repository.CommentRepository;
import com.api.codingchallenge.service.exception.PSQLException;
import com.api.codingchallenge.service.exception.ResourceNotFoundException;

@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private MovieService movieService;

	@Transactional
	public Comment save(CommentDTO commentDTO) {
		User user = userService.getCurrentUser();
		MovieDTO movieDTO = movieService.findMovieByImdbId(commentDTO.getImdbId());

		var comment = new Comment();
		comment.setComment(commentDTO.getComment());
		comment.setImdbId(movieDTO.getImdbId());
		comment.setUser(user);

		return commentRepository.save(comment);
	}

	@Transactional
	public Comment update(Long commentId, CommentUpdateDTO commentUpdateDTO) {
		try {
			User user = userService.getCurrentUser();
			Comment comment = commentRepository.findById(commentId).get();
			if (comment.getUser() != user)
				throw new PSQLException("Can't update other user's comment.");
			
			comment.setComment(commentUpdateDTO.getComment());
			return commentRepository.save(comment);
		} catch (EntityNotFoundException | NoSuchElementException e) {
			throw new ResourceNotFoundException("Comment id not found " + commentId);
		}
	}

	@Transactional
	public void delete(Long commentId) {
		try {
			commentRepository.deleteById(commentId);
		} catch (EmptyResultDataAccessException | NoSuchElementException e) {
			throw new ResourceNotFoundException("Comment id not found " + commentId);
		}
	}

	@Transactional
	public Comment reply(Long commentAnsweredId, CommentUpdateDTO commentUpdateDTO) {
		User user = userService.getCurrentUser();
		Comment commentAnswered = commentRepository.findById(commentAnsweredId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment answered id not found " + commentAnsweredId));
		
		if (commentAnswered.getRepeated())
			throw new PSQLException("Unable to reply to comments marked as repeated.");
		
		MovieDTO movieDTO = movieService.findMovieByImdbId(commentAnswered.getImdbId());

		var comment = new Comment();
		comment.setComment(commentUpdateDTO.getComment());
		comment.setImdbId(movieDTO.getImdbId());
		comment.setCommentAnswered(commentAnswered);
		comment.setUser(user);

		userService.addScore(user.getId());

		return commentRepository.save(comment);
	}

	@Transactional
	public Comment markAsRepeated(Long commentId) {
		try {
			Comment comment = commentRepository.findById(commentId).get();
			comment.setRepeated(true);
			return commentRepository.save(comment);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Comment id not found " + commentId);
		}
	}
}
