package com.istn.crypto.model;

import lombok.*;

import java.util.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseEncryptSuccess {
    private Boolean success;
    private String message;
    private String value;
    
    @Builder ResponseEncryptSuccess(Boolean success, String message, String value) {
        this.success = success;
        this.message = message;
        this.value = value;
    }
}