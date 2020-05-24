package com.rs.data.repository.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rs.data.repository.IRecipeRepository;
import com.rs.exception.DeserializationException;
import com.rs.exception.DocumentOperationException;
import com.rs.model.dto.Recipe;
import com.rs.util.Util;

import io.redisearch.Document;
import io.redisearch.Query;
import io.redisearch.Schema;
import io.redisearch.client.AddOptions;
import io.redisearch.client.Client;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.exceptions.JedisConnectionException;

@Slf4j
@Repository
public class RecipeRepository implements IRecipeRepository {

	private static final String ID = "id";
	private static final String TITLE = "title";
	private static final String CATEGORY = "category";
	private static final String YIELD = "yield";
	private static final String BODY = "body";

	private static final Schema RECIPE_SCHEMA = new Schema().addTextField(ID, 1.0)
			.addField(new Schema.TextField(TITLE, 5.0, true, false, false)).addTagField(CATEGORY, ",")
			.addSortableNumericField(YIELD).addTextField("body", 1.0);

	private Client client;

	private static ObjectMapper objMapper = Util.getMapper();

	@Autowired
	public RecipeRepository(@Qualifier(value = "recipeClient") Client client) {
		super();
		this.client = client;
	}

	@Override
	public Recipe save(final Recipe recipe) {
		final String id = Util.generateUuid();
		recipe.setId(id);
		boolean success = client.addDocument(generateDoc(documentDeserializer(recipe)), new AddOptions());
		if (success) {
			return recipe;
		} else {
			throw new DocumentOperationException("Recipe(%s) could not be added to data store", recipe.toString());
		}
	}

	
	@Override
	public Recipe update(final Recipe recipe) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub

	}

	private boolean checkRecipeClientConnection() {
		boolean flag = true;
		try {
			client.getInfo();
		} catch (JedisConnectionException je) {
			log.error(je.getMessage());
			flag = false;
		}
		return flag;
	}

	@PostConstruct
	private boolean createSchema() {
		client.dropIndex();
		return client.createIndex(RECIPE_SCHEMA, io.redisearch.client.Client.IndexOptions.defaultOptions());
	}

	private Map<String, Object> documentDeserializer(Recipe recipe) {
		try {
			Map<String, Object> doc = Collections.unmodifiableMap(new HashMap<String, Object>() {
				{
					put(TITLE, recipe.getHead().getTitle());
					put(ID, recipe.getId());
					put(CATEGORY, recipe.getHead().getCategories());
					put(YIELD, recipe.getHead().getYield());
					put(BODY, objMapper.writeValueAsString(recipe));
				}
			});
			return doc;
		} catch (Exception ex) {
			throw new DeserializationException("Document Deserialize Error " + ex);
		}

	}

	private Document generateDoc(Map<String, Object> map) {
		return new Document((String) map.get(ID), map);
	}

	

}
