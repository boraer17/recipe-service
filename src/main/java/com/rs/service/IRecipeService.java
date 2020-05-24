package com.rs.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rs.model.dto.Recipe;

public interface IRecipeService {

	
	Recipe save(Recipe recipe);
	
	void saveBatch(Recipe recipe);
	
	Recipe update(final Recipe recipe);
	
	Page<Recipe> searchRecipe(Pageable page,String keyword);
}
