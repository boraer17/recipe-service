package com.rs.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rs.exception.DeserializationException;
import com.rs.model.dto.Recipe;

import io.redisearch.Document;

public class Util {
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	public static String generateUuid() {
        return UUID.randomUUID().toString();
    }
	
	public static ObjectMapper getMapper() {
		return mapper;
	}

	public static String[] parseString(final String splitRegex, String s) {
		return Arrays.stream(s.split(splitRegex)).map(String::trim).toArray(String[]::new);
	}
}
