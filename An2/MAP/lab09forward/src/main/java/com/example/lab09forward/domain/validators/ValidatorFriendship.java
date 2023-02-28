package com.example.lab09forward.domain.validators;

import com.example.lab09forward.domain.Entities.Friendship;
import com.example.lab09forward.domain.exceptions.ValidationException;

/**
 * Class models ValidatorFriendship type of entity
 * Singleton Pattern
 */
public class ValidatorFriendship implements  Validator<Friendship> {
    /**
     * Instance of validator - singleton
     */
    private static ValidatorFriendship instance;

    /**
     * Method to get Instance of ValidatorFriendship
     * @return instance - ValidatorFriendship
     */
    public static synchronized ValidatorFriendship getInstance() {
        if(instance==null){
            instance=new ValidatorFriendship();
        }
        return instance;
    }
    /**
     * Constructor private because of Singleton Pattern
     */
    private ValidatorFriendship() {
    }
    /**
     * Method to validate Friendship
     * @param friendship - Friendship
     * @throws ValidationException - if Friendship is invalid
     */
    @Override
    public void validate(Friendship friendship) throws ValidationException {

        String problems = "";
        if(friendship.getId1()==null || friendship.getId2()==null) {
            problems+="IDs cannot be null!\n";
        }
        if(friendship.getId1().equals(friendship.getId2())) {
            problems+="IDs cannot be equal!\n";
        }
        if (problems.length() > 0) {
            throw new ValidationException(problems);
        }
    }
}
