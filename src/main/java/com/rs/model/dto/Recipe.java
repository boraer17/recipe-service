package com.rs.model.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Recipe {

	
	private String id;

	@NotNull
	private Head head;

	@NotNull
	private Direction direction;
	@NotNull
	private Set<Formula> formulas = new HashSet<Formula>();
}
