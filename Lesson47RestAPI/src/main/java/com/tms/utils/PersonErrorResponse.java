package com.tms.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PersonErrorResponse {
    private String message;
    private long timestamp;
}
