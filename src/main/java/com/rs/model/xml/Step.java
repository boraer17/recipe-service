package com.rs.model.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JacksonXmlRootElement(localName = "step")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Step
{

	public static final String TAG = "step";


	@JacksonXmlProperty(localName = "step")
	private String step;
	
	
}
