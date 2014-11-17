package com.innometrics.integration.app.ml.recommender.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author andrew, Innometrics
 */
public class JacksonUtil {
    private static ObjectMapper objectMapper = initObjectMapper();

    public JacksonUtil() {
    }

    private static ObjectMapper initObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper;
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
