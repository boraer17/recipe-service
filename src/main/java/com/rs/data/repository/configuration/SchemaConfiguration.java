package com.rs.data.repository.configuration;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import io.redisearch.Schema;
import io.redisearch.client.Client;
import io.redisearch.client.Client.IndexOptions;

public class SchemaConfiguration {

	/*
	private static final Schema CATEGORY_SCHEMA = new Schema()
			.addField(new Schema.TextField("category", 5.0, true, false, false));

	private Client client;

	@Autowired
	public SchemaConfiguration(Client client) {
		super();
		this.client = client;
	}

	@PostConstruct
	public void recipeSchemaMapping() {
		client.createIndex(RECIPE_SCHEMA, IndexOptions.defaultOptions());
	}

	@PostConstruct
	public void categorySchemaMapping() {
		client.createIndex(CATEGORY_SCHEMA, IndexOptions.defaultOptions());
	}
	*/
}
