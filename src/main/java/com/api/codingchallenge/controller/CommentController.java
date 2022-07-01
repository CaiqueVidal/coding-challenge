package com.api.codingchallenge.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.codingchallenge.dto.CommentDTO;
import com.api.codingchallenge.dto.CommentReactionDTO;
import com.api.codingchallenge.dto.CommentUpdateDTO;
import com.api.codingchallenge.model.Comment;
import com.api.codingchallenge.model.CommentReaction;
import com.api.codingchallenge.service.CommentReactionService;
import com.api.codingchallenge.service.CommentService;

@RestController
@RequestMapping("/comments")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@Autowired
	private CommentReactionService commentReactionService;

	@PostMapping
	public ResponseEntity<Comment> saveComment(@RequestBody @Valid CommentDTO commentDTO) {
		return ResponseEntity.status(HttpStatus.CREATED).body(commentService.save(commentDTO));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Comment> updateComment(@PathVariable(value = "id") Long commentId,
			@RequestBody @Valid CommentUpdateDTO commentUpdateDTO) {
		return ResponseEntity.status(HttpStatus.OK).body(commentService.update(commentId, commentUpdateDTO));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteComment(@PathVariable(value = "id") Long commentId) {
		commentService.delete(commentId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@PostMapping("/reply/{id}")
	public ResponseEntity<Comment> replyComment(@PathVariable(value = "id") Long commentId,
			@RequestBody @Valid CommentUpdateDTO commentUpdateDTO) {
		return ResponseEntity.status(HttpStatus.CREATED).body(commentService.reply(commentId, commentUpdateDTO));
	}

	@PutMapping("/mark-as-repeated/{id}")
	public ResponseEntity<Comment> markCommentAsRepeated(@PathVariable(value = "id") Long commentId) {
		return ResponseEntity.status(HttpStatus.OK).body(commentService.markAsRepeated(commentId));
	}

	@PutMapping("/to-react/{id}")
	public ResponseEntity<CommentReaction> reactToComment(@PathVariable(value = "id") Long commentId,
			@RequestBody @Valid CommentReactionDTO commentReactionDTO) {
		return ResponseEntity.status(HttpStatus.OK).body(commentReactionService.save(commentId, commentReactionDTO));
	}

	@DeleteMapping("/remove-reaction/{id}")
	public ResponseEntity<Void> deleteCommentReaction(@PathVariable(value = "id") Long commentReactionId) {
		commentReactionService.delete(commentReactionId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
