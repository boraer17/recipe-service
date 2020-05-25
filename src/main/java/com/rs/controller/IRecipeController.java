package com.rs.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rs.model.dto.Recipe;

@RequestMapping(path = "/recipes")
public interface IRecipeController {

	@PostMapping
	ResponseEntity<Recipe> createRecipe(@Valid @RequestBody final Recipe recipe);

	@PutMapping(path = "/{recipeId}")
	Recipe updateRecipe(@PathVariable("recipeId") String recipeId, @RequestBody final Recipe recipe);

	@GetMapping(path = "/{recipeId}")
	Recipe getRecipe(@PathVariable("recipeId") String recipeId);

	@GetMapping
	Page<Recipe> searchRecipe(@RequestParam(required = false, name = "keyword") final String keyword,@RequestParam(required = false, name = "filter") final String filter,
			@PageableDefault(page = 0, size = 20) @SortDefault.SortDefaults({
					@SortDefault(sort = "name", direction = Direction.DESC) }) Pageable pageable);
	
}
