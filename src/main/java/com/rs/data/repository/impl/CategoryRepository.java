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
import com.rs.data.repository.ICategoryRepository;
import com.rs.exception.DeserializationException;
import com.rs.exception.DocumentNotFoundException;
import com.rs.exception.DocumentOperationException;
import com.rs.model.dto.Category;
import com.rs.util.Util;

import io.redisearch.Document;
import io.redisearch.Query;
import io.redisearch.Schema;
import io.redisearch.SearchResult;
import io.redisearch.client.AddOptions;
import io.redisearch.client.Client;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class CategoryRepository implements ICategoryRepository {

	private static final String CATEGORY = "category";
	private static final Schema CATEGORY_SCHEMA = new Schema()
			.addField(new Schema.TextField(CATEGORY, 5.0, true, false, false));

	@Autowired
	@Qualifier(value = "categoryClient")
	private Client client;

	private static ObjectMapper objMapper = Util.getMapper();

	@Override
	public Category save(Category category) {
		if (client.addDocument(generateDoc.apply(documentDeserializer.apply(category)), new AddOptions()))
			return category;
		else {
			log.error("Category({}) could not be added to data store", category.toString());
			throw new DocumentOperationException("Category(%s) could not be added to data store", category.toString());
		}
	}

	@Override
	public Page<Category> findAll(Pageable page) {
		Query q = new Query("*")  .limit(page.getPageNumber(), page.getPageSize());
		SearchResult result = client.search(q);
		return new PageImpl<Category>(result.docs.parallelStream().map(documentSerializer).collect(Collectors.toList()),
				page, result.totalResults);
	}
	
	@Override
	public Boolean delete(final String id) {
		if(!client.deleteDocument(id, true)) {
			log.error("Category({}) could not be removed from data store", id);
			throw new DocumentOperationException("Category(%s) could not be removed from data store", id);
		}
		return true;
	}
	
	
	@Override
	public Category findById(String description) {
		final Document doc = client.getDocument(description);
		if (doc != null) {
			return documentSerializer.apply(doc);
		} else {
			throw new DocumentNotFoundException("Category Not Found for Category(%s)", description);
		}
	}

	@PostConstruct
	private boolean createSchema() {

		try {
			client.dropIndex();
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
		return client.createIndex(CATEGORY_SCHEMA, io.redisearch.client.Client.IndexOptions.defaultOptions());
	}

	Function<Category, Map<String, Object>> documentDeserializer = desc -> {
		try {
			Map<String, Object> doc = Collections.unmodifiableMap(new HashMap<String, Object>() {
				{
					put(CATEGORY, desc.getDefinition());
				}
			});
			return doc;
		} catch (Exception ex) {
			log.error("Document Deserialize Error " + ex);
			throw new DeserializationException("Document Deserialize Error " + ex);
		}
	};

	Function<Map<String, Object>, Document> generateDoc = map -> {
		return new Document((String) map.get(CATEGORY), map);
	};
	
	Function<Document, Category> documentSerializer = document -> {
		
			final String raw = document.getString(CATEGORY);
			final Category category = new Category(raw);
			return category;
	};
}
