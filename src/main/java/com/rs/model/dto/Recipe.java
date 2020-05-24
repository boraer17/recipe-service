package com.rs.model.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rs.util.Util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Recipe {

	
	private String id;

	@NotNull
	private Head head;

	@NotNull
	private Direction direction;
	@NotNull
	private Set<Formula> formulas = new HashSet<Formula>();
	
	@Override
	public String toString(){
		try {
			return Util.getMapper().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return this.head.getTitle();
	}
}
