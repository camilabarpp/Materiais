package com.materiais.materiais.configuration.exception;

import java.util.function.Supplier;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String s){
        super(s);
    }
}
