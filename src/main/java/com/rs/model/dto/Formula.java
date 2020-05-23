package com.rs.model.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Formula {

	@Nullable
	private String title;
	
	@NotBlank
	@NotEmpty
	private Set<Ingredient> ingredients = new HashSet<Ingredient>();
	
}
