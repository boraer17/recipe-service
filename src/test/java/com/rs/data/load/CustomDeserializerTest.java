package com.rs.data.load;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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
import com.rs.data.deserializer.ICustomDeserializer;
import com.rs.model.xml.Recipe;
import com.rs.model.xml.RecipeDivision;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = RecipeServiceApplication.class)
@ActiveProfiles("test-integration")
@TestPropertySource(properties = { "app.listener.directory=src/test/resources",
		"app.listener.fileName=30_Minute_Chili.xml", })
public class CustomDeserializerTest {

	@Value("${app.listener.fileName}")
	private String fileName;

	@Value("${app.listener.directory}")
	private String directory;

	@Autowired
	@Qualifier(value = "xmlDeserializer")
	private ICustomDeserializer xmlDeserializer;

	@Test
	public void testParseXmlBasedRecipe() throws Exception {
		
		String xml = new String(Files.readAllBytes(Paths.get(directory + "/" + fileName)));
		RecipeDivision recipe = xmlDeserializer.deserialize(xml);
		log.info(recipe.toString());
	    assertThat(0<recipe.getIngredientdivs().size());
	}
}
