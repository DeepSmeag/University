package com.example.examen.domain.validators;

import com.example.examen.domain.entities.Client;
import com.example.examen.domain.exceptions.ValidationException;

/**
 * Class models ValidatorUser type of entity
 * Singleton Pattern
 */
public class ValidatorUser implements Validator<Client> {

    private static ValidatorUser instance;


    private ValidatorUser() {
    }

    public static synchronized ValidatorUser getInstance() {
        if (instance == null) {
            instance = new ValidatorUser();
        }
        return instance;
    }

    @Override
    public void validate(Client client) throws ValidationException {
        String name = client.getName();
        if(name == null) {
            throw new ValidationException("Invalid name");
        }
    }
}
