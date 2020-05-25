package com.rs.data.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.rs.RecipeServiceApplication;
import com.rs.exception.DocumentNotFoundException;
import com.rs.model.dto.Category;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = RecipeServiceApplication.class)
@ActiveProfiles("test-integration")
@TestMethodOrder(OrderAnnotation.class)
public class CategoryRepositoryTest {

	@Autowired
	private ICategoryRepository categoryRepository;

	@Test
	@Order(1)
	public void testCategoryAdd() {

		final Category cat1 = new Category("Chili");
		final Category cat2 = new Category("Mexico");
		final Category cat3 = new Category("Main Dish");
		final Category cat4 = new Category("Italiano");
		final Category cat5 = new Category("Turkish");
		final Category cat6 = new Category("Pizza");
		final Category cat7 = new Category("Hamburger");
		categoryRepository.save(cat1);
		categoryRepository.save(cat2);
		categoryRepository.save(cat3);
		categoryRepository.save(cat4);
		categoryRepository.save(cat5);
		categoryRepository.save(cat6);
		categoryRepository.save(cat7);

	}

	@Test
	@Order(2)
	public void testGetAllCategories() {

		Pageable page = PageRequest.of(0, 20);
		Page pageResult = categoryRepository.findAll(page);
		pageResult.getContent().forEach(System.out::println);
		assertEquals(7, pageResult.getContent().size());

	}

	@Test
	@Order(3)
	public void testFindByIdSucess() {
		ArrayList<String> cats = new ArrayList<String>();
		cats.add("Pizza");
		cats.add("Turkish");
		Set<Category> catResult =  categoryRepository.findByIds(cats.toArray(new String[cats.size()]));
		assertEquals(2,catResult.size());
	}
	
	@Test
	@Order(4)
	public void testFindByIdFail() {
		Set<Category> cats =  categoryRepository.findByIds("XXX");
		assertEquals(0,cats.size());
	
	}
	
}
