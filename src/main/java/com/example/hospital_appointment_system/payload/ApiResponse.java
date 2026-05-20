package com.example.hospital_appointment_system.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    private int status;

    private String message;

    private T data;

    // Success response with data
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(
                LocalDateTime.now(),
                200,
                message,
                data
        );
    }

    // Success response without data (for delete operations)
    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>(
                LocalDateTime.now(),
                200,
                message,
                null
        );
    }

    // Error response
    public static <T> ApiResponse<T> error(int status, String message) {
        return new ApiResponse<>(
                LocalDateTime.now(),
                status,
                message,
                null
        );
    }

    // Created response (201)
    public static <T> ApiResponse<T> created(String message, T data) {
        return new ApiResponse<>(
                LocalDateTime.now(),
                201,
                message,
                data
        );
    }
}
