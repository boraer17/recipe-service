package com.rs.service;

import com.rs.model.dto.Recipe;

public interface IRecipeService {

	
	Recipe save(Recipe recipe);
	
	void saveBatch(Recipe recipe);
	
	Recipe update(final Recipe recipe);
	
	
}
