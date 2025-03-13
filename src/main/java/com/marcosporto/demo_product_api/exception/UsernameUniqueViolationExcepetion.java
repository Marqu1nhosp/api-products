package com.marcosporto.demo_product_api.exception;

public class UsernameUniqueViolationExcepetion extends RuntimeException {
    public UsernameUniqueViolationExcepetion(String message) {
        super(message);
    }
}
