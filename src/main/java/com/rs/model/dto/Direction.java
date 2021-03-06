package com.rs.model.dto;

import java.util.Set;
import java.util.TreeSet;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Direction {


	@NotNull
	@NotEmpty
	private Set<Step> steps = new TreeSet<Step>();
	
}
