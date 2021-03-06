package com.rs.model.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rs.util.Util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Head {

	@NotBlank
	@NotNull
	@NotEmpty
	private String title;
	
	@NotBlank
	@NotNull
	@NotEmpty
	private Integer yield;
	
	
	@NotNull
	@NotEmpty
	private Set<Category> categories = new HashSet();
	
	@Override
	public String toString(){
		try {
			return Util.getMapper().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return this.getTitle();
	}
}
