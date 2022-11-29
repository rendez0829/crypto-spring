package com.istn.crypto.model;

import java.util.Map;

import org.springframework.web.servlet.function.EntityResponse;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class APIResponse {
    private Boolean success;
    private String message;
    private EntityResponse<?> value;
    
    @Builder APIResponse(Boolean success, String message, EntityResponse<?> value) {
        this.success = success;
        this.message = message;
        this.value = value;
    }
}