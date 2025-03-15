package com.marcosporto.demo_product_api.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.marcosporto.demo_product_api.exception.EntityNotFoundException;
import com.marcosporto.demo_product_api.exception.UsernameUniqueViolationExcepetion;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

        @ExceptionHandler(AccessDeniedException.class)
        public ResponseEntity<ErrorMessage> accessDeniedExcepetion(AccessDeniedException ex,
                        HttpServletRequest request) {

                log.error("Api Error - ", ex);

                return ResponseEntity
                                .status(HttpStatus.FORBIDDEN)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(new ErrorMessage(request, HttpStatus.FORBIDDEN, ex.getMessage()));
        }

        @ExceptionHandler(UsernameUniqueViolationExcepetion.class)
        public ResponseEntity<ErrorMessage> usernameUniqueViolationExcepetion(RuntimeException ex,
                        HttpServletRequest request) {

                log.error("Api Error - ", ex);

                return ResponseEntity
                                .status(HttpStatus.CONFLICT)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(new ErrorMessage(request, HttpStatus.CONFLICT, ex.getMessage()));
        }

        @ExceptionHandler(EntityNotFoundException.class)
        public ResponseEntity<ErrorMessage> entityNotFoundException(RuntimeException ex,
                        HttpServletRequest request) {

                log.error("Api Error - ", ex);

                return ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(new ErrorMessage(request, HttpStatus.NOT_FOUND, ex.getMessage()));
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorMessage> methodArgumentNotValidException(MethodArgumentNotValidException ex,
                        HttpServletRequest request, BindingResult result) {

                log.error("Api Error - ", ex);

                return ResponseEntity
                                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(new ErrorMessage(request, HttpStatus.UNPROCESSABLE_ENTITY,
                                                "Campo(s) invalidos(s)", result));
        }
}
