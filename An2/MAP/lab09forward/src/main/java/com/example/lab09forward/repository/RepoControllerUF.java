package com.example.lab09forward.repository;

import com.example.lab09forward.config.Config;
import com.example.lab09forward.domain.Entities.Friendship;
import com.example.lab09forward.domain.Entities.Message;
import com.example.lab09forward.domain.Entities.User;
import com.example.lab09forward.domain.exceptions.RepoException;
import com.example.lab09forward.domain.exceptions.ValidationException;
import com.example.lab09forward.domain.validators.ValidatorFriendship;
import com.example.lab09forward.domain.validators.ValidatorMessage;
import com.example.lab09forward.domain.validators.ValidatorUser;
import com.example.lab09forward.repository.database.FriendshipDBRepo;
import com.example.lab09forward.repository.database.MessageDBRepo;
import com.example.lab09forward.repository.database.UserDBRepo;


import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Class models RepoControllerUF type of controller; a wrapper over Repositories of Users and Friendships
 */
public class RepoControllerUF {
    /**
     * repoFriendships - repository of Friendships
     */
    private static FriendshipDBRepo repoFriendships;
    /**
     * repoUsers - repository of Users
     */
    private static UserDBRepo repoUsers;

    private static MessageDBRepo repoMessages;
    /**
     * Instance of RepoControllerUF
     */
    private static RepoControllerUF instance;

    /**
     * Constructor for RepoControllerUF - private because of Singleton Pattern
     */
    private RepoControllerUF() {
    }

    /**
     * Method to get Instance of RepoControllerUF
     *
     * @return - instance of RepoControllerUF
     */
    public static synchronized RepoControllerUF getInstance() {
        if (instance == null) {
            instance = new RepoControllerUF();
            repoFriendships = new FriendshipDBRepo(Config.dbLink, Config.username, Config.password , ValidatorFriendship.getInstance());
            repoUsers = new UserDBRepo(Config.dbLink, Config.username, Config.password, ValidatorUser.getInstance());
            repoMessages = new MessageDBRepo(Config.dbLink, Config.username, Config.password, ValidatorMessage.getInstance());
        }
        return instance;
    }

    /**
     * Method to find User by names
     *
     * @param firstName - String
     * @param lastName  - String
     * @return - User
     * @throws RepoException - if user does not exist
     */
    public User findUserByName(String firstName, String lastName) throws RepoException {
        User user = repoUsers.findByName(firstName, lastName);
        if(user == null)
            throw new RepoException("User does not exist!");
        return user;
    }

    /**
     * Method to save User
     *
     * @param user - User
     * @throws ValidationException - if User is invalid
     */
    public void saveUser(User user) throws ValidationException {
        repoUsers.saveUser(user);
    }

    /**
     * Method to delete User
     *
     * @param id - UUID
     */
    public void deleteUser(UUID id) throws RepoException{
        repoUsers.deleteUser(id);
    }

    /**
     * Method to find all Users
     *
     * @return - Iterable of Users
     */
    public Iterable<User> findAllUsers() {
        return repoUsers.loadAllData();
    }

    /**
     * Method to find all Friendships
     *
     * @param id1 - UUID
     * @param id2 - UUID
     * @return - Iterable of Friendships
     * @throws RepoException - if Friendship doesn't exist
     */
    public Friendship findFriendship(UUID id1, UUID id2) throws RepoException {
        return repoFriendships.findByUUIDs(id1, id2);

    }
    public Friendship findFriendship(UUID id) throws RepoException {
        return repoFriendships.extractEntity(id);

    }

    /**
     * Method to save Friendship
     *
     * @param friendship - Friendship
     * @throws ValidationException - if Friendship is not valid
     */
    public void saveFriendship(Friendship friendship) throws ValidationException {
        repoFriendships.saveFriendship(friendship);
    }
    /**
     * Method to delete Friendship
     *
     * @param id - UUID
     */
    public void deleteFriendship(UUID id) {
        repoFriendships.deleteFriendship(id);
    }

    /**
     * Method to find all Friendships
     *
     * @return - Iterable of Friendships
     */
    public Iterable<Friendship> findAllFriendships() {
        return repoFriendships.loadAllData();
    }

    /**
     * Method to find User based on ID
     *
     * @param id - UUID
     * @return - User
     * @throws RepoException - if User does not exist
     */
    public User findUser(UUID id) throws RepoException {
        return repoUsers.extractEntity(id);
    }

    /**
     * Method to modify a friendship
     * @param id1 - id of first user
     * @param id2 - id of second user
     * @param date - date of friendship
     */
    public void modifyFriendship(UUID id1, UUID id2, String date) {
        Friendship friendship = repoFriendships.findByUUIDs(id1, id2);
        friendship.setDate(LocalDate.parse(date));
        repoFriendships.saveFriendship(friendship);
    }

    /**
     * Method to modify User
     * @param names1 - original names
     * @param names2 - new names
     */
    public void modifyUser(List<String> names1, List<String> names2) {
        User user = findUserByName(names1.get(0), names1.get(1));
        user.setFirstName(names2.get(0));
        user.setLastName(names2.get(1));
        repoUsers.saveUser(user);
    }

    public void removeUserFriendships(User user) {
        repoFriendships.deleteUsersFriendship(user.getId());
    }

    public void confirmFriendship(Friendship friendship) {
        friendship.setConfirmed(true);
        repoFriendships.saveFriendship(friendship);
    }


    public void saveMessage(Message message) {
        repoMessages.saveMessage(message);
    }

    public List<Message> getReceivedMessages(User loggedUser) {
        return repoMessages.findReceivedBy(loggedUser.getId());
    }

    public List<Message> getSentMessages(User loggedUser) {
        return repoMessages.findSentBy(loggedUser.getId());
    }
}
