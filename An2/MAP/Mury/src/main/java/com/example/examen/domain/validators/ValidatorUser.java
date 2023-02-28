package com.example.examen.domain.validators;

import com.example.examen.domain.entities.User;
import com.example.examen.domain.exceptions.ValidationException;

/**
 * Class models ValidatorUser type of entity
 * Singleton Pattern
 */
public class ValidatorUser implements Validator<User> {
    /**
     * Instance of validator - singleton
     */
    private static ValidatorUser instance;

    /**
     * Constructor for ValidatorUser - private to prevent instantiation
     */
    private ValidatorUser() {
    }
    /**
     * Method to get Instance of ValidatorUser
     * @return instance - ValidatorUser
     */
    public static synchronized ValidatorUser getInstance() {
        if (instance == null) {
            instance = new ValidatorUser();
        }
        return instance;
    }
    /**
     * Method to validate User
     * @param user - User
     * @throws ValidationException - if User is invalid
     */
    @Override
    public void validate(User user) throws ValidationException {
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String problems = "";
        if (firstName.length() == 0 || lastName.length() == 0) {

            problems += "Names cannot be empty.\n";
        }
        else if (firstName.charAt(0) < 'A' || firstName.charAt(0) > 'Z' || lastName.charAt(0) < 'A' || lastName.charAt(0) > 'Z') {
            problems += "Names usually start with a big letter.\n";
        }
        if (problems.length() > 0) {
            throw new ValidationException(problems);
        }
    }
}
