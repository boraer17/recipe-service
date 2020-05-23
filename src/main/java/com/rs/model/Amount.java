package com.rs.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@JacksonXmlRootElement(localName = "amt")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Amount {
   
	public static final String TAG = "amt";

	
	@JacksonXmlProperty(localName = "qty")
	private String qty;
	
	@JacksonXmlProperty(localName = "unit")
	private String unit;
	
}
