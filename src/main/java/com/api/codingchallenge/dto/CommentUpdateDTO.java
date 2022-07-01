package com.api.codingchallenge.dto;

import javax.validation.constraints.NotBlank;

public class CommentUpdateDTO {

	@NotBlank(message = "The comment field is required.")
	private String comment;

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
