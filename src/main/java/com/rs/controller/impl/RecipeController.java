package com.rs.controller.impl;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.rs.controller.IRecipeController;
import com.rs.model.dto.Recipe;
import com.rs.service.IRecipeService;

@RestController
public class RecipeController implements IRecipeController {

	private IRecipeService recipeService;

	@Autowired
	public RecipeController(IRecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@Override
	public ResponseEntity<Recipe> createRecipe(@Valid Recipe recipe) {
		return new ResponseEntity<Recipe>(recipeService.update(recipe),HttpStatus.CREATED);
	}

	@Override
	public Recipe updateRecipe(String recipeId, Recipe recipe) {
		recipe.setId(recipeId);
		return recipeService.update(recipe);
	}

	@Override
	public Recipe getRecipe(String recipeId) {
		return recipeService.findById(recipeId);
	}

	@Override
	public Page<Recipe> searchRecipe(String keyword, String filter, Pageable pageable) {
		
		return null;
	}

}
