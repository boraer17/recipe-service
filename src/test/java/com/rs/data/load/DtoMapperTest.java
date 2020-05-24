package com.rs.data.load;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import com.rs.data.DtoMapper;
import com.rs.data.deserializer.CustomXmlDeserializer;
import com.rs.data.deserializer.ICustomDeserializer;
import com.rs.model.dto.Recipe;
import com.rs.model.xml.RecipeDivision;

public class DtoMapperTest {

	private static final String FILE_NAME = "30_Minute_Chili.xml";
	private static final String DIRECTORY = "src/test/resources";
	
	@Test
	public void mappperTest() throws Exception {
		ICustomDeserializer xmlDeserializer = new CustomXmlDeserializer();
		String xml = new String(Files.readAllBytes(Paths.get(DIRECTORY + "/" + FILE_NAME)));
		RecipeDivision recipeDiv = xmlDeserializer.deserialize(xml);
		DtoMapper mapper = new DtoMapper();
		final Recipe recipe = mapper.mapObject(recipeDiv);
		assertNotNull(recipe.getHead().getTitle());
	}
}
