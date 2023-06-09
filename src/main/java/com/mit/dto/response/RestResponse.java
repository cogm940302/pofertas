package com.mit.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class RestResponse<T> {

    private T data;
    private String status;
    private List<String> errors;
}
