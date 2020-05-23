package com.rs.model.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JacksonXmlRootElement(localName = "ing")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {

	
	public static final String TAG = "ing";

	@JacksonXmlProperty(localName = "item")
	private String item;
	
	@JacksonXmlProperty(localName = "amt")
	private Amount amount;
	
}
