package com.arzuahmed.ticketingsystem.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)   // her hansi bir field Null olanda response-de gorsenmir...
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
