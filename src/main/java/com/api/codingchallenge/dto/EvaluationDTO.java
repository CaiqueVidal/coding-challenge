package com.api.codingchallenge.dto;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class EvaluationDTO {

	@DecimalMin(value = "0.0", message = "The grade must be greater than or equal to 0")
	@DecimalMax(value = "10.0", message = "The grade must be less than or equal to 10")
	@NotNull(message = "The note field is required.")
	private Float note;

	@NotBlank(message = "The imdbId field is required.")
	private String imdbId;

	public Float getNote() {
		return note;
	}

	public void setNote(Float note) {
		this.note = note;
	}

	public String getImdbId() {
		return imdbId;
	}

	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}

}
