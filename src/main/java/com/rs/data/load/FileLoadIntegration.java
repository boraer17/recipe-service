package com.rs.data.load;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.file.transformer.FileToByteArrayTransformer;
import org.springframework.integration.file.transformer.FileToStringTransformer;
import org.springframework.stereotype.Service;

import com.rs.data.load.deserializer.ICustomDeserializer;
import com.rs.data.load.transform.FileToInputStreamTransformer;
import com.rs.data.repository.RecipeRepository;

@Service
public class FileLoadIntegration {

	
	@Autowired
	private FileToInputStreamTransformer fileToStreamTransformer;
	
	@Autowired
	@Qualifier(value = "xmlDeserializer")
	ICustomDeserializer deserializer;
	
	@Autowired
	RecipeRepository recipeRepo;
	
	@Bean
	public IntegrationFlow processFileFlow() {
	    return IntegrationFlows
	        .from("fileInputChannel")
	        .transform(fileToStreamTransformer).handle(deserializer,"process").handle(recipeRepo,"save").get();
	    }
}
