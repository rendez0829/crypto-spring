package com.istn.crypto.model;

import lombok.*;

import java.util.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseDecryptSuccess {
    private Boolean success;
    private String message;
    private Map<String,Object> value;
    
    @Builder ResponseDecryptSuccess(Boolean success, String message, Map<String,Object> value) {
        this.success = success;
        this.message = message;
        this.value = value;
    }
}