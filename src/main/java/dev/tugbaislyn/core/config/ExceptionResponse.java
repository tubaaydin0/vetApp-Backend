package dev.tugbaislyn.core.config;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExceptionResponse {
    private LocalDateTime timeStamp;
    private String message;
    private String status;
}
