package com.rs.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JacksonXmlRootElement(localName = "step")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Step //implements Comparable<Step> 
{

	public static final String TAG = "step";

	//private Integer order;
	@JacksonXmlProperty(localName = "step")
	private String step;
	
	/*
	 * Could be more than 1 step and cooking steps should be ordered therefore there is and order index needed 
	@Override
	public int compareTo(Step o) {
		if (this.order > o.order)
			return 1;
		else if (this.order < o.order)
			return -1;
		else
			return 0;
	}*/
}
