package com.rs.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JacksonXmlRootElement(localName = "recipe")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {

	public static final String TAG = "recipe";
    public static final String INGREDIENTS_TAGS = "incredients";
    public static final String DIRECTION_TAGS = "directions";
    
	@JacksonXmlProperty(localName = "head")
	private Head head;
	
	@JacksonXmlProperty(localName = "ingredients")
	private Set<Ingredient> ingredients = new HashSet<Ingredient>();
	
	@JacksonXmlProperty(localName = "directions")
	private Set<Step> directions = new HashSet<Step>();
	
}
