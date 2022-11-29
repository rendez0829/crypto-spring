package com.istn.crypto.model;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseEncryptFailuer {
    private Boolean success;
    private String message;
    private String value;
    
    @Builder ResponseEncryptFailuer(Boolean success, String message, String value) {
        this.success = success;
        this.message = message;
        this.value = value;
    }
}