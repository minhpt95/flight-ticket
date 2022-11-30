package com.catdev.ticket.dto;

import com.catdev.ticket.exception.ErrorResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDto<T> extends ErrorResponse {
    private T content;
    public ResponseDto(String errorCode, String errorType, String message, T content) {
        super(errorCode, errorType, message);
        this.content = content;
    }

    public ResponseDto() {
    }
}
