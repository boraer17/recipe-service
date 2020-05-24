package com.rs.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rs.model.dto.Recipe;

public interface IRecipeRepository  {
   
	Recipe save(final Recipe recipe);
	
    Recipe update(final Recipe recipe);
    
    void deleteById(final String id);
    
    Page findAll(Pageable page);

    
}
