package com.enigmacamp.api.holasend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ResponseMessage<T> {

    private Integer code;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    ResponseMessage(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    public static <T> ResponseMessage<T> success(T data) {
        return new ResponseMessage<>(HttpStatus.OK.value(), "OK", data);
    }

    public static ResponseMessage<Object> error(int code, String message) {
        return error(code, message,null);
    }

    public static <T> ResponseMessage <T> error(int code, String message, T data) {
        return new ResponseMessage<T>(code, message,data);
    }
}
