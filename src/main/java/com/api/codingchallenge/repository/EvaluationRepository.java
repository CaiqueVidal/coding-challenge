package com.api.codingchallenge.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.codingchallenge.model.Evaluation;
import com.api.codingchallenge.model.User;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
	public Optional<Evaluation> findByUserAndImdbId(User user, String imdbId);

	public List<Evaluation> findByImdbId(String imdbId);

	@Query(value = "SELECT * FROM tb_evaluations WHERE imdb_id = ?1", countQuery = "SELECT count(*) FROM tb_evaluations WHERE imdb_id = ?1", nativeQuery = true)
	Page<Evaluation> findByImdbId(String imdbId, Pageable pageable);
}
