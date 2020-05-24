package com.rs.data.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.rs.RecipeServiceApplication;
import com.rs.data.DtoMapper;
import com.rs.data.deserializer.ICustomDeserializer;
import com.rs.model.dto.Recipe;
import com.rs.model.xml.RecipeDivision;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = RecipeServiceApplication.class)
@ActiveProfiles("test-integration")
@TestPropertySource(properties = { "app.listener.directory=src/test/resources",
		"app.listener.fileName=30_Minute_Chili.xml", })
public class RecipeRepositoryTest {

	@Value("${app.listener.fileName}")
	private String fileName;

	@Value("${app.listener.directory}")
	private String directory;

	@Autowired
	private IRecipeRepository recipeRepo;

	@Autowired
	@Qualifier(value = "xmlDeserializer")
	private ICustomDeserializer deserializer;

	@Autowired
	private DtoMapper mapper;

	@Test
	public void testRecipeRepoSave() throws Exception {
		final Recipe recipe = generateRecipe();
		final Recipe recipeCreated = recipeRepo.save(recipe);
		assertNotNull(recipeCreated.getId());
	}

	private Recipe generateRecipe() throws Exception {
		String xml = new String(Files.readAllBytes(Paths.get(directory + "/" + fileName)));
		RecipeDivision recipeDiv = deserializer.deserialize(xml);
		return mapper.mapObject(recipeDiv);
	}

}
