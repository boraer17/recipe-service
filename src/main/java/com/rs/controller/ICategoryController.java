package com.rs.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.rs.model.dto.Category;

@RequestMapping(path = "/categories")
public interface ICategoryController {

	@GetMapping
	Page<Category> getAllCategories(@PageableDefault(page = 0, size = 20) @SortDefault.SortDefaults({
			@SortDefault(sort = "description", direction = Direction.DESC) }) Pageable pageable);
}
