package com.example.lab09forward.domain.validators;

import com.example.lab09forward.domain.Entities.Message;
import com.example.lab09forward.domain.exceptions.ValidationException;

public class ValidatorMessage implements Validator<Message> {
//    singleton
    private static ValidatorMessage instance = null;
    private ValidatorMessage(){}
    public static ValidatorMessage getInstance(){
        if(instance == null){
            instance = new ValidatorMessage();
        }
        return instance;
    }
    @Override
    public void validate(Message entity) throws ValidationException {
        if (entity.getId() == null) {
            throw new ValidationException("Id is null");
        }

    }
}
