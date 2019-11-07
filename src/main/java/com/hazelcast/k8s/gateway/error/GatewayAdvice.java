package com.hazelcast.k8s.gateway.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Register ExceptionHandler for catching exceptions and mapping them into respectful REST error response
 */
@ControllerAdvice
public class GatewayAdvice {

    @ResponseBody
    @ExceptionHandler(GatewayException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    String gatewayExceptionHandler(GatewayException ex) {
        return ex.getMessage();
    }
}
