package com.sinfolix.Sai_Samarth.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    String resourceName;
    String fieldName;
    long fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, long filedValue) {
        super(String.format("%s not found with %s : %s",resourceName,fieldName, filedValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = filedValue;
    }
    public ResourceNotFoundException(String resourceName, String fieldName) {
        super(String.format("%s not found with %s : %s",resourceName,fieldName));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
    }
}
