package com.rs.model.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Step implements Comparable<Step>{

	@NotBlank
	@NotNull
	@NotEmpty
	@Min(value = 0)
	@Max(value = 25)
	private Integer order;
	
	@NotBlank
	@NotNull
	@NotEmpty
	private String explianation;

	@Override
	public int compareTo(Step o) {
		if(this.order > o.order)
			return 1;
		else if(this.order < o.order)
			return -1;
		else
			return 0;
	}
	
}
