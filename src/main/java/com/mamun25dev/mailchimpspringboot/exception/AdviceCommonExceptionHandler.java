package com.mamun25dev.mailchimpspringboot.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class AdviceCommonExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(AdviceCommonExceptionHandler.class);

    /**
     * This is for RestTemplate response code 500 handling
     * @param ex exception
     * @return response
     */
    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    @ResponseBody
    public ResponseEntity<?> handleInternalServerError(HttpServerErrorException.InternalServerError ex) {
        // response.sendError(ex.getRawStatusCode(), ex.getStatusText());
        logger.error("@AdviceCommonExceptionHandler..............1");
        logger.error(ex.getMessage());
        return ResponseEntity.status(ex.getRawStatusCode()).headers(ex.getResponseHeaders()).body(ex.getResponseBodyAsString());
    }

    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseBody
    public ResponseEntity<?> handleError(HttpClientErrorException ex, HttpServletResponse response) {
        logger.error("@AdviceCommonExceptionHandler..............2");
        logger.error(ex.getMessage());
        return ResponseEntity.status(ex.getRawStatusCode()).headers(ex.getResponseHeaders()).body(ex.getResponseBodyAsString());
    }


}
