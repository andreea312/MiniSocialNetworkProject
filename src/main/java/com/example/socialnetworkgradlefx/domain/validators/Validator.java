package com.example.socialnetworkgradlefx.domain.validators;

/**
 * Validator interface
 * @param <E>
 */
public interface Validator<E> {
    /**
     * Validates an entity
     * @param entity E - user or friendship
     * @throws ValidationException if entity is not valid
     */
    void validate(E entity) throws ValidationException;
}
