package com.rs.model.xml;

import java.util.HashSet;
import java.util.Set;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JacksonXmlRootElement(localName = "ing-div")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientDivision {

	public static final String TAG = "ing-div";
	
	@JacksonXmlProperty(localName = "title")
	@Nullable
	private String title;
	
	@JacksonXmlProperty(localName = "ing")
	private Set<Ingredient> ingredients = new HashSet<Ingredient>();
	
}
