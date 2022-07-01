package com.api.codingchallenge.dto;

import com.api.codingchallenge.enums.annotations.ReactionAnnotation;

public class CommentReactionDTO {

	@ReactionAnnotation
	private Integer reaction;

	public Integer getReaction() {
		return reaction;
	}

	public void setReaction(Integer reaction) {
		this.reaction = reaction;
	}

}
