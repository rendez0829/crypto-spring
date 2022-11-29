package com.istn.crypto.controller;

import java.util.*;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.istn.crypto.model.APIResponse;

@RestController
@RequestMapping("/v1")
public class CryptoController {
  public static String alg = "AES/CBC/PKCS5Padding";
  private final String secretKey = "mYq3t6v9y$B&E)H@McQfTjWnZr4u7x!z";
  private final String iv = secretKey.substring(0, 16); // 16byte

  @PostMapping("/encrypt")
  public String AES256Encryption(@RequestBody Map<String, Object> requestBody) {
    try {
      System.out.println(">>> " + requestBody.get("value"));
      String value = (String) requestBody.get("value");

      ObjectMapper mapper = new ObjectMapper();

      Cipher cipher = Cipher.getInstance(alg);
      SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
      IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
      cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

      return new String(Base64.getEncoder().encode(cipher.doFinal(value.getBytes("UTF-8"))));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  @PostMapping("/decrypt")
  public String AES256Decryption(@RequestBody Map<String, Object> requestBody) {
    try {
      System.out.println(">>> " + requestBody.get("value"));
      String value = (String) requestBody.get("value");

      ObjectMapper mapper = new ObjectMapper();

      Cipher cipher = Cipher.getInstance(alg);

      SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
      IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
      cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

      // byte[] byteValue = Base64.getDecoder().decode(value); //Base64 Decode가 필요한 경우
      byte[] byteValue = value.getBytes("UTF-8");
      byte[] decodedBytes = Base64.getDecoder().decode(byteValue);
      byte[] decrypted = cipher.doFinal(decodedBytes);

      return new String(decrypted, "UTF-8");
      // return ResponseEntity.status(HttpStatus.OK).body(mapper);

    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
