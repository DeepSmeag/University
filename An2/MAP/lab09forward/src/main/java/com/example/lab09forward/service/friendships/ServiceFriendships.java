package com.example.lab09forward.service.friendships;

import com.example.lab09forward.domain.Entities.Friendship;
import com.example.lab09forward.domain.Entities.User;
import com.example.lab09forward.domain.exceptions.RepoException;
import com.example.lab09forward.domain.exceptions.ServiceException;
import com.example.lab09forward.repository.RepoControllerUF;


import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Class models ServiceFriendships type of service
 * Singleton Pattern
 */
public class ServiceFriendships {
    /**
     * instance - instance of ServiceFriendships
     */
    private static ServiceFriendships instance;
    /**
     * repoController - instance of RepoControllerUF
     */
    private static RepoControllerUF repoControllerUF;

    /**
     * Constructor for ServiceFriendships - private because of Singleton Pattern
     */
    private ServiceFriendships() {
    }

    /**
     * Method to get Instance of ServiceFriendships
     *
     * @return - instance of ServiceFriendships
     */
    public static synchronized ServiceFriendships getInstance() {
        if (instance == null) {
            instance = new ServiceFriendships();
            repoControllerUF = RepoControllerUF.getInstance();
        }
        return instance;
    }

    /**
     * Method to add Friendship
     *
     * @param user1 - User
     * @param user2 - User
     * @param date  - String
     * @throws ServiceException - if friendship already exists
     */
    public void addFriendship(User user1, User user2, String date) throws ServiceException {
        Friendship friendship = new Friendship(user1.getId(), user2.getId(), LocalDate.parse(date), Boolean.FALSE, LocalDate.now());

        if (repoControllerUF.findFriendship(user1.getId(), user2.getId()) != null) {
            throw new ServiceException("Friendship already exists!");
        }
        friendship.setId(UUID.randomUUID());
        repoControllerUF.saveFriendship(friendship);
    }

    /**
     * Method to delete Friendship
     *
     * @param user1 - User
     * @param user2 - User
     * @throws ServiceException - if friendship doesn't exist
     */
    public void removeFriendship(User user1, User user2) throws ServiceException {
        Friendship friendship = repoControllerUF.findFriendship(user1.getId(), user2.getId());
        if (friendship == null) {
            throw new ServiceException("\n\tFriendship does not exist; cannot delete!\n");
        }
        repoControllerUF.deleteFriendship(friendship.getId());

    }

    /**
     * Method to get all Friendships
     *
     * @return - Iterable of Friendships
     */
    public Iterable<Friendship> findAllFriendships() {
        return repoControllerUF.findAllFriendships();
    }

    public void modifyFriendship(List<String> names1, List<String> names2, String date) throws RepoException {
        User user1 = repoControllerUF.findUserByName(names1.get(0), names1.get(1));
        User user2 = repoControllerUF.findUserByName(names2.get(0), names2.get(1));
        repoControllerUF.modifyFriendship(user1.getId(), user2.getId(), date);
    }

    public Friendship findFriendship(UUID id1, UUID id2) {
        try {
            return repoControllerUF.findFriendship(id1, id2);
        } catch (RepoException e) {
            return null;
        }
    }
    public Friendship findFriendship(UUID id) {
        try {
            return repoControllerUF.findFriendship(id);
        } catch (RepoException e) {
            return null;
        }
    }

    public void confirmFriendship(Friendship friendship) {
        repoControllerUF.confirmFriendship(friendship);
    }
}