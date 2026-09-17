package ru.romzheln.search_service.util;

import com.fasterxml.jackson.databind.JsonNode;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MapperUtil {

    private MapperUtil(){}

    public static String text(JsonNode payload, String field) {
        JsonNode node = payload.get(field);
        return node == null || node.isNull() ? null : node.asText();
    }

    public static Integer integer(JsonNode payload, String field) {
        JsonNode node = payload.get(field);
        return node == null || node.isNull() ? null : node.asInt();
    }

    public static Boolean bool(JsonNode payload, String field) {
        JsonNode node = payload.get(field);
        return node == null || node.isNull() ? null : node.asBoolean();
    }

    public static BigDecimal decimal(JsonNode payload, String field) {
        JsonNode node = payload.get(field);
        return node == null || node.isNull()
                ? null
                : node.decimalValue();
    }

    public static LocalDate date(JsonNode payload, String field) {
        JsonNode node = payload.get(field);
        return node == null || node.isNull()
                ? null
                : LocalDate.parse(node.asText());
    }

    public static Long toLong(JsonNode payload, String field){
        JsonNode node = payload.get(field);
        return node == null || node.isNull()
                ? null
                : node.asLong();
    }
}
