package com.tharushi.tomatogame.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessageResponse {

    private boolean success;
    private String message;
    private int code;
}
