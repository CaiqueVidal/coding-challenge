package com.api.codingchallenge.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.codingchallenge.model.Comment;
import com.api.codingchallenge.model.CommentReaction;
import com.api.codingchallenge.model.User;

public interface CommentReactionRepository extends JpaRepository<CommentReaction, Long> {

	public Optional<CommentReaction> findByUserAndComment(User user, Comment comment);
}
