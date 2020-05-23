package com.rs.data;


import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum DeserializerType {
    XML("xml"),
    CSV("json");

    private static final Map<String, DeserializerType> MAP = Collections.unmodifiableMap(new HashMap<String, DeserializerType>() {{
        Arrays.stream(DeserializerType.values()).forEach(value -> put(value.type, value));
    }});
    private final String type;

    private DeserializerType(String type) {
        this.type = type;
    }

    public static DeserializerType getByKey(final String key) {
        final DeserializerType result = MAP.get(key);
        if (result == null) {
            throw new IllegalArgumentException(String.format("Can not find DeserializerType by key '%s'", key));
        }
        return result;
    }

    public String getType() {
        return type;
    }
}
