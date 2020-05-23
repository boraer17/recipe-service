package com.rs.model.xml;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JacksonXmlRootElement(localName = "head")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Head {

	public static final String TAG = "head";

	@JacksonXmlProperty(localName = "title")
	private String title;
	
	@JacksonXmlProperty(localName = "yield")
	private Integer yield;
	
	@JacksonXmlProperty(localName = "categories")
	private Set<Category> categories = new HashSet<Category>();

}
