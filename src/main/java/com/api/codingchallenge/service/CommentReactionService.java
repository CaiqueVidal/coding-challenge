package com.api.codingchallenge.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.api.codingchallenge.dto.CommentReactionDTO;
import com.api.codingchallenge.model.Comment;
import com.api.codingchallenge.model.CommentReaction;
import com.api.codingchallenge.model.User;
import com.api.codingchallenge.repository.CommentReactionRepository;
import com.api.codingchallenge.repository.CommentRepository;
import com.api.codingchallenge.service.exception.PSQLException;
import com.api.codingchallenge.service.exception.ResourceNotFoundException;

@Service
public class CommentReactionService {

	@Autowired
	private CommentReactionRepository commentReactionRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private CommentRepository commentRepository;

	@Transactional
	public CommentReaction save(Long commentId, CommentReactionDTO commentReactionDTO) {
		User user = userService.getCurrentUser();
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment id not found " + commentId));

		Optional<CommentReaction> commentReactionOptional = commentReactionRepository.findByUserAndComment(user,
				comment);
		CommentReaction commentReaction = commentReactionOptional.isPresent() ? commentReactionOptional.get()
				: new CommentReaction();

		commentReaction.setUser(user);
		commentReaction.setComment(comment);
		commentReaction.setReaction(commentReactionDTO.getReaction());

		return commentReactionRepository.save(commentReaction);
	}

	@Transactional
	public void delete(Long commentReactionId) {
		try {
			User user = userService.getCurrentUser();
			CommentReaction commentReaction = commentReactionRepository.findById(commentReactionId).get();
			if (commentReaction.getUser() != user)
				throw new PSQLException("Cannot change another user's reaction.");

			commentReactionRepository.deleteById(commentReactionId);
		} catch (EmptyResultDataAccessException | NoSuchElementException e) {
			throw new ResourceNotFoundException("Comment reaction id not found " + commentReactionId);
		}
	}
}
