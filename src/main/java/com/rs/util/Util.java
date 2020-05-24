package com.rs.util;

import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Util {
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	public static String generateUuid() {
        return UUID.randomUUID().toString();
    }
	
	public static ObjectMapper getMapper() {
		return mapper;
	}

}
