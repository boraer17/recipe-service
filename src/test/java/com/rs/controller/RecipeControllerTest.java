package com.rs.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.rs.controller.impl.RecipeController;
import com.rs.model.dto.Amount;
import com.rs.model.dto.Category;
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
		System.out.println(recipe.toString());
		when(recipeService.save(recipe)).thenReturn(recipe);
		mvc.perform(post("/recipes").content(recipe.toString()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}

	@Test
	public void testSearchRecipe() throws Exception {
     
		mvc.perform(get("/recipes").param("page", "0").param("size", "15").param("sort", "title,asc")
				.param("keyword", "Chili").param("filter", "Main Dish")).andExpect(status().isOk());
		verify(recipeService, times(1)).searchRecipe(pageCaptor.capture(), keywordCaptor.capture(),
				filterCaptor.capture());
		final PageRequest pageable = (PageRequest) pageCaptor.getValue();
		assertThat(pageable.getPageNumber()).isEqualTo(0);
		assertThat(pageable.getPageSize()).isEqualTo(15);
		assertThat(pageable.getSort()).isEqualTo(Sort.by(Order.asc("title")));
        final String filter = filterCaptor.getValue();
		assertThat(filter).isEqualTo("Main Dish");
		final String keyword = keywordCaptor.getValue(); 
		assertThat(keyword).isEqualTo("Chili");
	}

	
	
	private static Recipe createRecipe() {
		Recipe recipe = new Recipe();
		recipe.setHead(new Head("Bitterbollen", 1, new HashSet<Category>() {
			{
				add(new Category("Dutch"));
			}
		}));
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