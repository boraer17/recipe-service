package com.rs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rs.data.repository.IRecipeRepository;
import com.rs.model.dto.Recipe;
import com.rs.service.IRecipeService;
import com.rs.util.Util;

@Service
public class RecipeService implements IRecipeService{

	
	private IRecipeRepository recipeRepository;
	
	@Autowired
	public RecipeService(IRecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}

	@Override
	public Recipe save(Recipe recipe) {
		return recipeRepository.save(recipe);
	}

	@Override
	public void saveBatch(Recipe recipe) {
		save(recipe);
	}
	
	@Override
	public Recipe update(Recipe recipe) {
		 final Double score = recipeRepository.findScore(recipe.getId());
		 return recipeRepository.update(recipe, score);
	}

	@Override
	public Page<Recipe> searchRecipe(Pageable page, String keyword) {
		// TODO Auto-generated method stub
		return null;
	}


}
