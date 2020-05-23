package com.rs.model;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JacksonXmlRootElement(localName = "cat")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

	private String category;
}
