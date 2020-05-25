package com.rs.service.impl;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.rs.data.repository.IRecipeRepository;
import com.rs.model.dto.Category;
import com.rs.model.dto.Recipe;
import com.rs.service.IRecipeService;
import com.rs.util.Util;

@Service
public class RecipeService implements IRecipeService{

	private static final String CATEGORY_FILTER_FIELD_NAME = "category";
	private static final String TITLE_KEYWORD_FIELD_NAME = "title";
	
	
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
	public Page<Recipe> searchRecipe(Pageable page, String keyword, String filter) {
		if (StringUtils.isEmpty(keyword) && StringUtils.isEmpty(filter))
			return recipeRepository.findAll(page);
		else if (StringUtils.isEmpty(keyword) && !StringUtils.isEmpty(filter)) {
			final String query = generateFilterQuery(CATEGORY_FILTER_FIELD_NAME, Util.parseString(",", filter));
			return recipeRepository.search(page, query);
		} else if (!StringUtils.isEmpty(keyword) && StringUtils.isEmpty(filter)) {
			final String query = generateSearchQuery(TITLE_KEYWORD_FIELD_NAME,keyword);
			return recipeRepository.search(page, query);
		} else {
			final String query = generateSearchQuery(TITLE_KEYWORD_FIELD_NAME, keyword) + " "
					+ generateFilterQuery(CATEGORY_FILTER_FIELD_NAME, Util.parseString(",", filter));
			return recipeRepository.search(page, query);
		}
	}

	@Override
	public Recipe findById(String id) {
		return recipeRepository.findById(id);
	}
	
	private static String generateFilterQuery(final String filterField, final String... filters) {

		StringBuilder bd = new StringBuilder();
		bd.append("@" + filterField + ":{");
		int length = filters.length;
		int count = 1;
		for (final String filter : filters) {
			bd.append(filter);
			if (count < length)
				bd.append("|");
			count++;
		}
		bd.append("}");
		return bd.toString();
	}

	private static String generateSearchQuery(final String searchField,final String keyword) {
		return "@" + searchField + ":" + keyword;
	}
}
