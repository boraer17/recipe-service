package com.rs.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
	private String categories;
	
}
