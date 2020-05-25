package com.rs.data.repository;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import com.rs.model.dto.Category;

public interface ICategoryRepository {
	
	Category save(final Category category);
	
	Boolean delete(final String id);

	Page<Category> findAll(Pageable page);
    
	 Set<Category> findByIds(String... category);
	
}
