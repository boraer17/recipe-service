package com.rs.data.repository;

import com.rs.model.dto.Recipe;

public interface IRecipeRepository  {
   
	Recipe save(final Recipe recipe);
	
    Recipe update(final Recipe recipe);
    
    void deleteById(final String id);
    
    
}
