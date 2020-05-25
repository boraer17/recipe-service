package com.rs.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rs.model.dto.Category;

public interface ICategoryService {
	
	Category save(final Category category);
	
	Page<Category> findAll(Pageable page);
	
}
