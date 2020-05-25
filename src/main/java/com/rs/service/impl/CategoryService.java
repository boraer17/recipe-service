package com.rs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rs.data.repository.ICategoryRepository;
import com.rs.model.dto.Category;
import com.rs.service.ICategoryService;

@Service
public class CategoryService implements ICategoryService {

	private ICategoryRepository categoryRepository;

	@Autowired
	public CategoryService(ICategoryRepository categoryRepository) {
		super();
		this.categoryRepository = categoryRepository;
	}

	@Override
	public Category save(Category category) {
		return categoryRepository.save(category);
	}

	@Override
	public Page<Category> findAll(Pageable page) {
		return categoryRepository.findAll(page);
	}

}
