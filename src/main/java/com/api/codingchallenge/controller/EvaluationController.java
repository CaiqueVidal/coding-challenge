package com.api.codingchallenge.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.codingchallenge.dto.EvaluationDTO;
import com.api.codingchallenge.dto.EvaluationUpdateDTO;
import com.api.codingchallenge.model.Evaluation;
import com.api.codingchallenge.service.EvaluationService;

@RestController
@RequestMapping("/evaluations")
public class EvaluationController {

	@Autowired
	private EvaluationService evaluationService;

	@PostMapping
	public ResponseEntity<Evaluation> saveEvaluation(@RequestBody @Valid EvaluationDTO evaluationDTO) {
		return ResponseEntity.status(HttpStatus.CREATED).body(evaluationService.save(evaluationDTO));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Evaluation> updateEvaluation(@PathVariable(value = "id") Long evaluationId,
			@RequestBody @Valid EvaluationUpdateDTO evaluationUpdateDTO) {
		return ResponseEntity.status(HttpStatus.OK).body(evaluationService.update(evaluationId, evaluationUpdateDTO));
	}

}
