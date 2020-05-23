package com.rs.data.deserializer;

import org.springframework.messaging.Message;

import com.rs.data.DeserializerType;
import com.rs.exception.DeserializationException;
import com.rs.model.xml.RecipeDivision;

public interface ICustomDeserializer {

	DeserializerType getType();

	RecipeDivision process(Message<String> msg) throws DeserializationException;

	RecipeDivision deserialize(String content) throws DeserializationException;

	
}
