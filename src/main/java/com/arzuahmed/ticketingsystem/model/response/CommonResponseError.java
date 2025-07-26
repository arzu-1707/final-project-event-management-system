package com.arzuahmed.ticketingsystem.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponseError {

    private LocalDateTime timeStamp;
    private boolean success;
    private int errorCode;
    private String message;
    private String path;

    public static CommonResponseError of(int errorCode, String path){
        return CommonResponseError.builder()
                .path(path)
                .errorCode(errorCode)
                .build();
    }

    public static CommonResponseError of(int errorCode, String message, String path){
        return CommonResponseError.builder()
                .errorCode(errorCode)
                .message(message)
                .path(path)
                .build();
    }

}
