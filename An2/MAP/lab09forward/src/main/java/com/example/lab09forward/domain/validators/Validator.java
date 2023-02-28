package com.example.lab09forward.domain.validators;

import com.example.lab09forward.domain.exceptions.ValidationException;

/**
 * Interface class models Validator type of entity
 * @param <T> - type of entity to validate
 */
public interface Validator<T> {
    /**
     * Method to validate entity
     * @param entity - entity to validate
     * @throws ValidationException - if entity is invalid
     */
    void validate(T entity) throws ValidationException;
}