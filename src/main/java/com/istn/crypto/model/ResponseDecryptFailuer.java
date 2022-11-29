package com.istn.crypto.model;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseDecryptFailuer {
    private Boolean success;
    private String message;
    private String value;
    
    @Builder ResponseDecryptFailuer(Boolean success, String message, String value) {
        this.success = success;
        this.message = message;
        this.value = value;
    }
}