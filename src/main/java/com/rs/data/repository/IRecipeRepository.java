package com.rs.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rs.model.dto.Recipe;

public interface IRecipeRepository  {
   
	Recipe save(final Recipe recipe);
	
	Recipe update(Recipe recipe, Double score);
    
    Boolean deleteById(final String id);
    
    Page findAll(Pageable page);

    Recipe findById(String id);

	Double findScore(String id);

}
