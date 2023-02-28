package com.example.lab09forward.repository.file;

import com.example.lab09forward.domain.Entities.User;
import com.example.lab09forward.domain.exceptions.RepoException;
import com.example.lab09forward.domain.validators.Validator;

import java.util.List;
import java.util.UUID;

/**
 * Class models UserFile0 type of repository
 */
public class UserFile0 extends AbstractFileRepository0<UUID, User>  {
    /**
     * Constructor for UserFile0
     * @param fileName - String
     * @param validator - Validator
     */
    public UserFile0(String fileName, Validator<User> validator) {
        super(fileName, validator);
    }

    /**
     * Method to extract entity from string
     * @param attributes - String
     * @return - User
     */
    @Override
    public User extractEntity(List<String> attributes) {
        //TODO: implement method
//        User user = new User(attributes.get(1),attributes.get(2));
//        user.setId(UUID.fromString(attributes.get(0)));
//        return user;
        return null;
    }

    /**
     * Method to find entity by names
     * @param firstName - String
     * @param lastName - String
     * @return - User
     * @throws RepoException - if user doesn't exist
     */
    public User findByName(String firstName, String lastName) throws RepoException {
        for (User user : findAll()) {
            if (user.getFirstName().equals(firstName) && user.getLastName().equals(lastName)) {
                return user;
            }
        }
        throw new RepoException("User not found!");
    }

    /**
     * Method to convert entity to string
     * @param entity - User
     * @return - String
     */
    @Override
    protected String createEntityAsString(User entity) {
        return entity.getId()+","+entity.getFirstName()+","+entity.getLastName();
    }

    /**
     * Method to update the state of the file repo
     */
    public void update() {
        writeFile();
    }
}
