package com.api.codingchallenge.enums.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.api.codingchallenge.enums.Reaction;

public class ReactionValidator implements ConstraintValidator<ReactionAnnotation, Integer> {

	@Override
	public boolean isValid(Integer code, ConstraintValidatorContext context) {
		for (Reaction value : Reaction.values()) {
			if (code == value.getCode())
				return true;
		}

		return false;
	}

}