package com.example.lld;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;

public class CustomJsonParser {
    ObjectMapper mapper = new ObjectMapper();

    Object parser(String jsonString) throws IOException {
        JsonNode jsonNode = mapper.readTree(jsonString);
        System.out.println(jsonNode.get("f1").get("f2"));
        return jsonNode;
    }

    Object parse(String str, int st, int ed) {
        JsonNode jsonNode = mapper.createObjectNode();
        ((ObjectNode) jsonNode).put("k1", "v1");
        return null;
    }

    public static void main(String[] args) throws IOException {
        String jsonString = "{ \"f1\" : { \"f2\" : \"v1\" } } ";
        CustomJsonParser customJsonParser = new CustomJsonParser();
        System.out.println(customJsonParser.parser(jsonString));
    }
}
