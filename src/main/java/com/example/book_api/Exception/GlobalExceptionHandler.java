package com.example.book_api.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

//    @RestControllerAdvice
//    - เป็น annotation ของ Spring (คล้าย `@ControllerAdvice`) แต่เน้นสำหรับ REST API
//    - บอก Spring ว่า class นี้เป็น **global exception handler**
//    - ทุก exception ที่เกิดจาก controller จะถูกส่งเข้ามาที่นี่ หาก type ตรงกับ `@ExceptionHandler` ที่กำหนด
//    - `@RestControllerAdvice` = `@ControllerAdvice + @ResponseBody` → **ส่ง JSON กลับเสมอ**

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errors);
    }

    // จัดการ error จากกรณี throw IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage()); // ดึง "Invalid Buddhist year."
        return ResponseEntity.badRequest().body(error); // HTTP 400
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<String> handleEmployeeNotFound(EmployeeNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}

