package com.rs.data.repository;

import org.springframework.stereotype.Repository;

import com.rs.model.dto.Recipe;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class RecipeRepository {

	public void save(final Recipe recipe) {
		log.info("Save is called");
		log.info(recipe.toString());
	}

}
