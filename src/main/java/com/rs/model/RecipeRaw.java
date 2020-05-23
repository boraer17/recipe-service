package com.rs.model;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JacksonXmlRootElement(localName = "recipeml")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RecipeRaw {

	
	@JacksonXmlProperty(isAttribute = true, localName = "version")
	private Double version;
	
	@JacksonXmlProperty(localName = "recipe")
	private Recipe recipe;
	
	
}
