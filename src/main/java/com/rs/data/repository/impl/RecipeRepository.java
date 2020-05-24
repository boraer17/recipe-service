package com.rs.data.repository.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rs.data.repository.IRecipeRepository;
import com.rs.exception.DeserializationException;
import com.rs.exception.DocumentNotFoundException;
import com.rs.exception.DocumentOperationException;
import com.rs.model.dto.Recipe;
import com.rs.util.Util;

import io.redisearch.Document;
import io.redisearch.Query;
import io.redisearch.Schema;
import io.redisearch.SearchResult;
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
	
	@Autowired
	@Qualifier(value = "recipeClient")
	private Client client;

	private static ObjectMapper objMapper = Util.getMapper();

	@Override
	public Recipe save(final Recipe recipe) {
		final String id = Util.generateUuid();
		recipe.setId(id);
		if (client.addDocument(generateDoc.apply(documentDeserializer.apply(recipe)), new AddOptions()))
			return recipe;
		else {
			log.error("Recipe({}) could not be added to data store", recipe.toString());
			throw new DocumentOperationException("Recipe(%s) could not be added to data store", recipe.toString());
		}
	}

	@Override
	public Recipe update(final Recipe recipe, Double score) {

		if (client.updateDocument(recipe.getId(), score, documentDeserializer.apply(recipe)))
			return recipe;
		else
			throw new DocumentOperationException("Recipe(%s) Update Failed", recipe.getId());

	}

	@Override
	public Boolean deleteById(String id) {
		if (!client.deleteDocument(id, true)) {
			log.error("Recipe({}) could not be removed from data store", id);
			throw new DocumentOperationException("Recipe(%s) could not be removed from data store", id);
		}
		return true;
	}

	@Override
	public Page findAll(Pageable page) {

		Query q = new Query("*")  .limit(page.getPageNumber(), page.getPageSize());
		SearchResult result = client.search(q);
		return new PageImpl<Recipe>(result.docs.parallelStream().map(documentSerializer).collect(Collectors.toList()),
				page, result.totalResults);

	}

	@Override
	public Double findScore(String id) {
		final Document doc = client.getDocument(id);
		if(doc != null)
			return doc.getScore();
		else
			throw new DocumentNotFoundException("Recipe('%s') Not Found",id);
	}
	
	@Override
	public Recipe findById(String id) {
		final Document doc = client.getDocument(id);
		if(doc != null)
			return documentSerializer.apply(doc);
		else
			throw new DocumentNotFoundException("Recipe('%s') Not Found",id);
	}
	
	
	private boolean checkRecipeClientConnection() {
		boolean flag = true;
		try {
			Map<String, Object> info = client.getInfo();
		} catch (JedisConnectionException je) {
			log.error(je.getMessage());
			flag = false;
		}
		return flag;
	}

	@PostConstruct
	private boolean createSchema() {

		try {
			client.dropIndex();
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
		return client.createIndex(RECIPE_SCHEMA, io.redisearch.client.Client.IndexOptions.defaultOptions());
	}

	Function<Recipe, Map<String, Object>> documentDeserializer = recipe -> {
		try {
			Map<String, Object> doc = Collections.unmodifiableMap(new HashMap<String, Object>() {
				{
					put(TITLE, recipe.getHead().getTitle());
					put(ID, recipe.getId());
					put(CATEGORY, recipe.getHead().getCategory());
					put(YIELD, recipe.getHead().getYield());
					put(BODY, objMapper.writeValueAsString(recipe));
				}
			});
			return doc;
		} catch (Exception ex) {
			log.error("Document Deserialize Error " + ex);
			throw new DeserializationException("Document Deserialize Error " + ex);
		}

	};

	Function<Map<String, Object>, Document> generateDoc = map -> {
		return new Document((String) map.get(ID), map);
	};

	Function<Document, Recipe> documentSerializer = document -> {
		try {
			final String raw = document.getString(BODY);
			final Recipe recipe = objMapper.readValue(raw, Recipe.class);
			return recipe;
		} catch (Exception ex) {
			log.error("Document Serialize Error " + ex);
			throw new DeserializationException("Document Serialize Error " + ex);
		}

	};

	Function<Map<String, Object>, Recipe> resultSerializer = map -> {
		try {
			final String raw = (String) map.get(BODY);
			final Recipe recipe = objMapper.readValue(raw, Recipe.class);
			return recipe;
		} catch (Exception ex) {
			log.error("Document Serialize Error " + ex);
			throw new DeserializationException("Document Serialize Error " + ex);
		}
	};


}
