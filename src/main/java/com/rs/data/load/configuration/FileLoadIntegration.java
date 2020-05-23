package com.rs.data.load.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.file.transformer.FileToStringTransformer;
import org.springframework.stereotype.Service;

import com.rs.data.DtoMapper;
import com.rs.data.deserializer.ICustomDeserializer;
import com.rs.data.repository.RecipeRepository;

@Service
public class FileLoadIntegration {

	@Autowired
	private FileToStringTransformer fileToStringTransformer;

	@Autowired
	@Qualifier(value = "xmlDeserializer")
	private ICustomDeserializer deserializer;

	@Autowired
	private RecipeRepository recipeRepo;

	@Autowired
	private DtoMapper dtoMapper;

	@Bean
	public IntegrationFlow processFileFlow() {
		return IntegrationFlows.from("fileInputChannel").transform(fileToStringTransformer)
				.handle(deserializer, "process").handle(dtoMapper, "mapObject").handle(recipeRepo, "save").get();
	}
}
