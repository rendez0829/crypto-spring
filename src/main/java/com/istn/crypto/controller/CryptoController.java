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

import com.istn.crypto.model.ResponseEncryptSuccess;
import com.istn.crypto.model.ResponseEncryptFailuer;
import com.istn.crypto.model.ResponseDecryptSuccess;
import com.istn.crypto.model.ResponseDecryptFailuer;

@RestController
@RequestMapping("/v1")
public class CryptoController {
  public static String alg = "AES/CBC/PKCS5Padding";
  private final String secretKey = "mYq3t6v9y$B&E)H@McQfTjWnZr4u7x!z";
  private final String iv = secretKey.substring(0, 16); // 16byte

  @PostMapping("/encrypt")
  public ResponseEntity<?> AES256Encryption(@RequestBody Map<String, Object> requestBody) {
    try {
      System.out.println(">>> " + requestBody.get("value"));
      Map<String, Object> reqValue = (Map<String, Object>)requestBody.get("value");

      ObjectMapper reqMapper = new ObjectMapper();
      // Map<String, Object> decryptJson = reqMapper.readValue(new String(value, "UTF-8"), Map.class);

      // Map<String, Object> value = (Map<String, Object>) requestBody.get("value");

      Cipher cipher = Cipher.getInstance(alg);
      SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
      IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
      cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

      String stringJson = reqMapper.writeValueAsString(reqValue);

      byte[] encrypted = Base64.getEncoder().encode(cipher.doFinal(stringJson.getBytes("UTF-8")));

      ResponseEncryptSuccess apiResponse = ResponseEncryptSuccess.builder().success(true).message("AES256 암호화 성공!").value(encrypted).build();
      return ResponseEntity.status(HttpStatus.OK).body(apiResponse);

      // return new String(Base64.getEncoder().encode(cipher.doFinal(value.getBytes("UTF-8"))));
    } catch (Exception e) {
      e.printStackTrace();
      ResponseEncryptFailuer apiResponse = ResponseEncryptFailuer.builder().success(false).message(e.getMessage()).build();
      return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(apiResponse);
    }
    return null;
  }

  @PostMapping("/decrypt")
  public ResponseEntity<?> AES256Decryption(@RequestBody Map<String, Object> requestBody) {
    try {
      System.out.println(">>> " + requestBody.get("value"));
      String value = (String) requestBody.get("value");

      Cipher cipher = Cipher.getInstance(alg);

      SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
      IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
      cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

      // byte[] byteValue = Base64.getDecoder().decode(value); //Base64 Decode가 필요한 경우
      byte[] byteValue = value.getBytes("UTF-8");
      byte[] decodedBytes = Base64.getDecoder().decode(byteValue);
      byte[] decrypted = cipher.doFinal(decodedBytes);
      
      ObjectMapper mapper = new ObjectMapper();
      Map<String, Object> decryptJson = mapper.readValue(new String(decrypted, "UTF-8"), Map.class);

      ResponseDecryptSuccess apiResponse = ResponseDecryptSuccess.builder().success(true).message("AES256 복호화 성공!").value(decryptJson).build();
      return ResponseEntity.status(HttpStatus.OK).body(apiResponse);

    } catch (Exception e) {
      e.printStackTrace();
      ResponseDecryptFailuer apiResponse = ResponseDecryptFailuer.builder().success(false).message(e.getMessage()).build();
      return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(apiResponse);
    }
  }
}
