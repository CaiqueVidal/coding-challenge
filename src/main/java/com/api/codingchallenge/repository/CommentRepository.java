package com.api.codingchallenge.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.codingchallenge.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	public List<Comment> findByImdbId(String imdbId);

	@Query(value = "SELECT * FROM tb_comments WHERE imdb_id = ?1 AND comment_id is null", countQuery = "SELECT count(*) FROM tb_comments WHERE imdb_id = ?1 AND comment_id is null", nativeQuery = true)
	Page<Comment> findByImdbId(String imdbId, Pageable pageable);
}
