package com.rs.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.rs.controller.impl.RecipeController;
import com.rs.model.dto.Amount;
import com.rs.model.dto.Direction;
import com.rs.model.dto.Formula;
import com.rs.model.dto.Head;
import com.rs.model.dto.Ingredient;
import com.rs.model.dto.Recipe;
import com.rs.model.dto.Step;
import com.rs.service.impl.RecipeService;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(value = { RecipeController.class })
public class RecipeControllerTest {

	@Autowired
	MockMvc mvc;

	@MockBean
	private RecipeService recipeService;

	@Captor
	ArgumentCaptor<Pageable> pageCaptor;

	@Captor
	ArgumentCaptor<String> filterCaptor;

	@Captor
	ArgumentCaptor<String> keywordCaptor;

	@Test
	public void recipeSaveTest() throws Exception {
		Recipe recipe = createRecipe();
		when(recipeService.save(recipe)).thenReturn(recipe);
		mvc.perform(post("/recipes").content(recipe.toString())
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
	}

	private static Recipe createRecipe() {
		Recipe recipe = new Recipe();
		recipe.setHead(new Head("Bitterbollen", 1, "Dutch"));
		recipe.setDirection(new Direction(new HashSet<Step>() {
			{
				add(new Step(1, "Put prepared bitterbollens to owen for 15 min"));
			}
		}));
		recipe.setFormulas(new HashSet<Formula>() {
			{
				add(new Formula(null, new HashSet<Ingredient>() {
					{
						add(new Ingredient("meat", new Amount("1", "kilo")));
					}
				}));
			}
		});
		return recipe;
	}
}