package com.rs.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import com.rs.controller.ICategoryController;
import com.rs.model.dto.Category;
import com.rs.service.ICategoryService;

@RestController
public class CategoryController implements ICategoryController {

	private ICategoryService categoryService;
	
    @Autowired
	public CategoryController(ICategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@Override
	public Page<Category> getAllCategories(Pageable pageable) {
		return categoryService.findAll(pageable);
	}

}
