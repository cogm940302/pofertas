package com.mit.exception;

import com.mit.dto.response.RestResponse;

import java.util.Arrays;

public class ResportException extends RuntimeException {

    private RestResponse<String> response;

    public ResportException(String... errors) {
        super("Empty list");
        this.response = new RestResponse<>();
        this.response.setErrors(Arrays.asList(errors));
    }

    public RestResponse<String> getResponse() {
        return response;
    }
}
