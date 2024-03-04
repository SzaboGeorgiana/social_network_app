package com.example.lab6_fx_15_11.Validator;

public interface Validator<T> {
    void validate(T entity) throws com.example.lab6_fx_15_11.Validator.ValidationException;
}
