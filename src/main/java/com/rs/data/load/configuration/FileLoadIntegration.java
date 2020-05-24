package com.rs.data.load.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.file.transformer.FileToStringTransformer;
import org.springframework.integration.handler.MethodInvokingMessageHandler;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Component;

import com.rs.data.DtoMapper;
import com.rs.data.deserializer.ICustomDeserializer;
import com.rs.service.IRecipeService;

@Component
public class FileLoadIntegration {

	private FileToStringTransformer fileToStringTransformer;

	private ICustomDeserializer deserializer;

	private IRecipeService recipeService;

	private DtoMapper dtoMapper;

	@Autowired
	public FileLoadIntegration(FileToStringTransformer fileToStringTransformer,
			@Qualifier(value = "xmlDeserializer") ICustomDeserializer deserializer, IRecipeService recipeService,
			DtoMapper dtoMapper) {
		super();
		this.fileToStringTransformer = fileToStringTransformer;
		this.deserializer = deserializer;
		this.recipeService = recipeService;
		this.dtoMapper = dtoMapper;
	}

	@Bean
	public IntegrationFlow processFileFlow() {
		return IntegrationFlows.from("fileInputChannel").transform(fileToStringTransformer)
				.handle(deserializer, "process").handle(dtoMapper, "mapObject").handle(recipeService,"saveBatch").get();
	}
}
