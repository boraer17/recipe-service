package com.rs.data.load.deserializer;

import java.io.File;
import java.io.InputStream;

import org.springframework.messaging.Message;

import com.rs.data.DeserializerType;
import com.rs.exception.DeserializationException;
import com.rs.model.Recipe;
import com.rs.model.RecipeDivision;

public interface ICustomDeserializer {

    DeserializerType getType();

    RecipeDivision process(Message<String> msg) throws DeserializationException;
    
    RecipeDivision deserialize(String content) throws DeserializationException;
    
}
