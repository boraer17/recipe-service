package com.rs.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.test.web.servlet.MockMvc;

import com.rs.controller.impl.CategoryController;
import com.rs.service.impl.CategoryService;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(value = { CategoryController.class })
public class CategoryControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private CategoryService categoryService;
	
	@Captor
	ArgumentCaptor<Pageable> pageCaptor;
	
	@Test
	public void testGetAllCategories() throws Exception {
		
		mvc.perform(get("/categories").param("page","0").param("size","5").param("sort","description,asc")).andExpect(status().isOk());
		verify(categoryService,times(1)).findAll(pageCaptor.capture());
		PageRequest pageable = (PageRequest) pageCaptor.getValue();
		assertThat(pageable.getPageNumber()).isEqualTo(0);
	    assertThat(pageable.getPageSize()).isEqualTo(5);
	    assertThat(pageable.getSort()).isEqualTo(Sort.by(Order.asc("description")));
	 
	}
	
}
