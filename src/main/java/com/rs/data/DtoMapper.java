package com.rs.data;

import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.rs.model.dto.Category;
import com.rs.model.dto.Direction;
import com.rs.model.dto.Formula;
import com.rs.model.dto.Recipe;
import com.rs.model.dto.Step;
import com.rs.model.xml.Amount;
import com.rs.model.xml.IngredientDivision;
import com.rs.model.xml.RecipeDivision;

@Component
public class DtoMapper {

	Function<com.rs.model.xml.Category, com.rs.model.dto.Category> catMapper = xmlCat -> {
		final Category cat = new Category(xmlCat.getCategory());
		return cat;
	};

	Function<com.rs.model.xml.Head, com.rs.model.dto.Head> headMapper = xmlHead -> {

		final com.rs.model.dto.Head head = new com.rs.model.dto.Head(xmlHead.getTitle(), xmlHead.getYield(),
				xmlHead.getCategories().stream().map(catMapper).map(cat -> {
					return cat.getDefinition();
				}).collect(Collectors.joining(",")));
		return head;
	};

	Function<Amount, com.rs.model.dto.Amount> amountMapper = xmlAmount -> {
		final com.rs.model.dto.Amount amount = new com.rs.model.dto.Amount(xmlAmount.getQty(), xmlAmount.getUnit());
		return amount;
	};

	Function<com.rs.model.xml.Ingredient, com.rs.model.dto.Ingredient> ingredientMapper = xmlIngredient -> {
		final com.rs.model.dto.Ingredient ingredient = new com.rs.model.dto.Ingredient(xmlIngredient.getItem(),
				amountMapper.apply(xmlIngredient.getAmount()));
		return ingredient;
	};

	Function<com.rs.model.xml.RecipeDivision, com.rs.model.dto.Direction> directionMapper = xmlDiv -> {
		Iterator<com.rs.model.xml.Step> it = xmlDiv.getDirections().iterator();
		Direction direction = new Direction();
		int order = 0;
		while (it.hasNext()) {
			com.rs.model.xml.Step xmlStep = (com.rs.model.xml.Step) it.next();
			direction.getSteps().add(new Step(order, xmlStep.getStep()));
			order++;

		}
		return direction;
	};

	Function<IngredientDivision, Formula> formulaMapper = ingredientDiv -> {
		final Formula formula = new Formula(ingredientDiv.getTitle(),
				ingredientDiv.getIngredients().stream().map(ingredientMapper).collect(Collectors.toSet()));
		return formula;
	};

	Function<RecipeDivision, Recipe> mapper = recipeDivision -> {

		final Recipe recipe = new Recipe("",headMapper.apply(recipeDivision.getHead()),
				directionMapper.apply(recipeDivision),
				recipeDivision.getIngredientdivs().stream().map(formulaMapper).collect(Collectors.toSet()));
		return recipe;

	};

	public Recipe mapObject(RecipeDivision recipeDiv) {
		return mapper.apply(recipeDiv);
	}
}
