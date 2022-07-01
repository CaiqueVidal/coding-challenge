package com.api.codingchallenge.dto;

import javax.validation.constraints.NotBlank;

public class CommentDTO {

	@NotBlank(message = "The imdbId field is required.")
	private String imdbId;

	@NotBlank(message = "The comment field is required.")
	private String comment;

	public String getImdbId() {
		return imdbId;
	}

	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
