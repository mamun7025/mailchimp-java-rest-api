package com.mamun25dev.mailchimpspringboot.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mamun25dev.mailchimpspringboot.model.generic.ResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CommonHttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(CommonHttpUtil.class);


    public static HttpHeaders getHeaders(String token ) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        if (token != null) {
            headers.set("Authorization", token);
        }
        return headers;

    }

    public static String getJwtTokenFromRequest( HttpServletRequest request ) {

        String token = null;
        String bearer = "Bearer ";
        final String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith(bearer)) {
            token = bearer + authorizationHeader.substring(7);
        }
        return token;

    }

    public static Map<String, Object> convertToSimpleMapObject(String jsonStr) {
        Map<String, Object> finalObject = null;
        ObjectMapper objectMapper = new ObjectMapper();
        if (jsonStr != null) {
            try {
                finalObject = objectMapper.readValue(jsonStr, HashMap.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return finalObject;
    }

    public static String getTcsResponseStatusValue(String jsonStr){
        Map<String, Object> simpleMap = convertToSimpleMapObject(jsonStr);
        if(simpleMap != null){
            return simpleMap.get("status").toString();
        }
        return null;
    }

    public static <T> T convertToOriginalObject(Object object, Class<T> type) {
        T finalObject = null;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.registerModule(new JavaTimeModule()); // JDK 8 - Local Date Time Handling
        if (object != null) {
            finalObject = objectMapper.convertValue(object, type);
        }
        return finalObject;
    }

    public static String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    public static void printPrettyJson(Object object){
        if (object != null) {
            try {
                String prettyJson = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(object);
                System.out.println(prettyJson);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }
    public static void logPrettyJson(Object object){
        if (object != null) {
            try {
                String prettyJson = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(object);
                System.out.println("@Console Print");
                System.out.println(prettyJson);
                System.out.println("@Log File Print");
                logger.info(prettyJson);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }


    public static ResponseEntity<ResponseModel> postDataFromOtherService(RestTemplate restTemplate, String url, String token, String bodyJson) {
        return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<String>(bodyJson, getHeaders(token)),
                ResponseModel.class);
    }

    public static ResponseEntity<ResponseModel> getDataFromOtherService(RestTemplate restTemplate, String url, String token) {
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<String>(getHeaders(token)),
                ResponseModel.class);
    }

    public static ResponseEntity<ResponseModel> putDataFromOtherService(RestTemplate restTemplate, String url, String token, String bodyJson) {
        return restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<String>(bodyJson, getHeaders(token)),
                ResponseModel.class);
    }

    public static ResponseEntity<ResponseModel> deleteDataFromOtherService(RestTemplate restTemplate, String url, String token) {
        return restTemplate.exchange(url, HttpMethod.DELETE, new HttpEntity<String>(getHeaders(token)),
                ResponseModel.class);
    }
    
    public String getUniqueId(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }


}
