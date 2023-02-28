package com.example.examen.service;



import com.example.examen.domain.entities.TestEntity;
import com.example.examen.domain.entities.User;
import com.example.examen.domain.exceptions.RepoException;
import com.example.examen.domain.exceptions.ServiceException;
import com.example.examen.repository.RepoController;


import java.util.List;
import java.util.UUID;


/**
 * Class models ServiceUsers type of service
 * Singleton Pattern
 */
public class ServiceAll {
    /**
     * instance - instance of ServiceUsers
     */
    private static ServiceAll instance;

    /**
     * Constructor for ServiceUsers - private because of Singleton Pattern
     */
    private ServiceAll() {
    }
    /**
     * Method to get Instance of ServiceUsers
     * @return - instance of ServiceUsers
     */
    public static synchronized ServiceAll getInstance() {
        if (instance == null) {
            instance = new ServiceAll();

        }
        return instance;
    }

    /**
     * Method to add User
     * @param firstName - String
     * @param lastName - String
     * @throws ServiceException - if user already exists
     */
    public void addUser(String firstName, String lastName) throws ServiceException {
        try {
//            User user = repoControllerUF.findUserByName(firstName, lastName);
//            if(user.getDeleted() == Boolean.TRUE){
//                user.setDeleted(Boolean.FALSE);
//                repoControllerUF.saveUser(user);
//                return;
//            }
        } catch (RepoException e) {
//            User user = new User(firstName, lastName, Boolean.FALSE);
//            user.setId(UUID.randomUUID());
//            repoControllerUF.saveUser(user);
//            return;
        }
        throw new ServiceException("User already exists!");
    }

    /**
     * Method to modify a user
     * @param names1 - original names
     * @param names2 - new names
     * @throws RepoException - error in case repo does not find original user
     */
    public void modUser(List<String> names1, List<String> names2) throws RepoException{
//        repoControllerUF.modifyUser(names1, names2);
    }

    /**
     * Method to delete User
     * @param firstName - first name of user
     * @param lastName - last name of user
     * @throws ServiceException - if user doesn't exist
     */
    public void removeUser(String firstName, String lastName) throws RepoException {
//        User user = repoControllerUF.findUserByName(firstName, lastName);
//        repoControllerUF.deleteUser(user.getId());
    }

    /**
     * Method to get string of users
     * @return - string of users
     */
    public String getUsersString() {
//        Iterable<User> users = repoControllerUF.findAllUsers();
//        StringBuilder usersString = new StringBuilder();
//        for (User user : users) {
//            usersString.append(user.toString()).append("\n");
//        }
//        return usersString.toString();
        return "";
    }

    /**
     * Method to find all users
     * @return - Iterable of Users
     */
    public Iterable<User> findAllUsers() {
//        return repoControllerUF.findAllUsers();
        return null;
    }

    /**
     * Method to find user by names
     * @param firstName - first name of user
     * @param lastName  - last name of user
     * @return - User
     * @throws RepoException - if user doesn't exist
     */
    public User findUser(String firstName, String lastName) throws RepoException {
//        return repoControllerUF.findUserByName(firstName, lastName);
        return null;
    }

    /**
     * Method to find user by id
     * @param id - id of user
     * @return - User
     * @throws RepoException - if user doesn't exist
     */
    public User findUser(UUID id) throws RepoException {
//        return repoControllerUF.findUser(id);
        return null;
    }


    public Iterable<TestEntity> findAlltestUsers() {
        return RepoController.getInstance().findAllTests();
    }
    public TestEntity findTestEntity(Integer id) {
        return RepoController.getInstance().findTestEntity(id);
    }
}
