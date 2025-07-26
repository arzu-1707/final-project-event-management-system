package com.arzuahmed.ticketingsystem.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse<T> {

    private String message;
    private boolean success;
    private T data;

    public static <T> CommonResponse<T> success(T data){
       return CommonResponse.<T>builder()
                .success(true)
                .message("Operation successful")
                .data(data)
                .build();
    }

    public static <T> CommonResponse<T> success(String message, T data){
        return CommonResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> CommonResponse<T> fail(String message){
        return CommonResponse.<T>builder()
                .success(false)
                .message(message)
                .build();
    }
}
