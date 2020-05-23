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
public class Ingredient {

	@NotBlank
	@NotNull
	@NotEmpty
	private String item;
	
	@NotBlank
	@NotNull
	@NotEmpty
	private Amount amount;

}
