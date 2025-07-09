package com.recepies_service.dto;

import org.springframework.http.HttpStatus;

public class ErrorDto {
    String message;
    HttpStatus status;

    public ErrorDto(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public ErrorDto() {

    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

  public void setStatus(HttpStatus status) {
    this.status = status;
  }
}
