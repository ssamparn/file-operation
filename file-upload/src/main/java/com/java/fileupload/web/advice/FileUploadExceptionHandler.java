package com.java.fileupload.web.advice;

import jakarta.servlet.http.HttpServletRequest;

import com.java.fileupload.web.exception.ErrorResponse;
import com.java.fileupload.web.exception.FileUploadException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class FileUploadExceptionHandler {

  @ExceptionHandler(FileUploadException.class)
  public ResponseEntity<ErrorResponse> appException(FileUploadException e, HttpServletRequest request) {

    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setMessage(e.getMessage());

    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
