package kr.ink94.global.dto;

import org.springframework.http.HttpStatus;

public record ApiResponse<T>(
  String status,
  String message,
  T data
) {

  public static <T> ApiResponse<T> success(
    HttpStatus status, String message, T data){
    return new ApiResponse<T>(status.name(), message, data);
  }

  public static <T> ApiResponse<T> fail(
    HttpStatus status, String message){
    return new ApiResponse<T>(status.name(), message, null);
  }

  
}
