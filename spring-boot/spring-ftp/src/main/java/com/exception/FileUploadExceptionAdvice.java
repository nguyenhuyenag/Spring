package com.exception;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.sql.SQLException;

@Slf4j
@ControllerAdvice
public class FileUploadExceptionAdvice {

    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFilesize;

    // TODO: https://stackoverflow.com/a/54405341/7068014
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<?> maxUploadSizeExceeded(MaxUploadSizeExceededException ex) {
        log.error("File upload error: {}", ex.getMessage());
        String message = "The maximum upload size " + maxFilesize;
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                .body(message);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<?> sqlExceptionHelper(SQLException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getCause());
    }

}