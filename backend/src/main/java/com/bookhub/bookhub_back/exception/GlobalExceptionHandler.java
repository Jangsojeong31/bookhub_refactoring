package com.bookhub.bookhub_back.exception;

import com.bookhub.bookhub_back.common.constants.ResponseCode;
import com.bookhub.bookhub_back.common.constants.ResponseMessage;
import com.bookhub.bookhub_back.common.constants.ResponseMessageKorean;
import com.bookhub.bookhub_back.dto.ResponseDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ IllegalArgumentException.class, IllegalStateException.class })
    public ResponseEntity<ResponseDto<?>> handleBadRequest(RuntimeException e) {
        String errorMessage = e.getMessage() != null ? e.getMessage() : ResponseMessageKorean.INVALID_INPUT;
        return logAndRespond(ResponseCode.INVALID_INPUT, errorMessage, HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto<?>> handleValidationException(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getFieldError() != null
                ? e.getBindingResult().getFieldError().getDefaultMessage()
                : ResponseMessage.VALIDATION_FAIL;
        return logAndRespond(ResponseCode.VALIDATION_FAIL, errorMessage, HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseDto<?>> handleAccessDenied(AccessDeniedException e) {
        return logAndRespond(ResponseCode.NO_PERMISSION, ResponseMessage.NO_PERMISSION, HttpStatus.FORBIDDEN, e);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseDto<?>> handleNotFound(EntityNotFoundException e) {
        return logAndRespond(ResponseCode.RESOURCE_NOT_FOUND, ResponseMessage.RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND, e);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseDto<?>> handleDataIntegrity(DataIntegrityViolationException e) {
        return logAndRespond(ResponseCode.DATA_INTEGRITY_VIOLATION, ResponseMessage.DATA_INTEGRITY_VIOLATION, HttpStatus.CONFLICT, e);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<?>> handleGeneral(Exception e) {
        return logAndRespond(ResponseCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR, e);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ResponseDto<?>> handleBusinessException(BusinessException e) {
        return logAndRespond(e.getErrorCode(), e.getMessage(), HttpStatus.BAD_REQUEST, e);
    }

    private ResponseEntity<ResponseDto<?>> logAndRespond(String code, String message, HttpStatus status, Exception e) {
        log.error("예외 발생: code={}, message={}, HttpStatus={}", code, message, status, e);
        return ResponseDto.failWithStatus(code, message, status);
    }
}
