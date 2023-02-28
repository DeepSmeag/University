package socialnetwork.service.users;


import socialnetwork.domain.Entities.Community;
import socialnetwork.domain.Entities.User;
import socialnetwork.domain.exceptions.RepoException;
import socialnetwork.domain.exceptions.ServiceException;
import socialnetwork.repository.RepoControllerUF;

import java.util.List;
import java.util.UUID;


/**
 * Class models ServiceUsers type of service
 * Singleton Pattern
 */
public class ServiceUsers {
    /**
     * instance - instance of ServiceUsers
     */
    private static ServiceUsers instance;
    /**
     * repoController - instance of RepoControllerUF
     */
    private static RepoControllerUF repoControllerUF;
    /**
     * Constructor for ServiceUsers - private because of Singleton Pattern
     */
    private ServiceUsers() {
    }
    /**
     * Method to get Instance of ServiceUsers
     * @return - instance of ServiceUsers
     */
    public static synchronized ServiceUsers getInstance() {
        if (instance == null) {
            instance = new ServiceUsers();
            repoControllerUF = RepoControllerUF.getInstance();
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
            User user = repoControllerUF.findUserByName(firstName, lastName);
            if(user.getDeleted() == Boolean.TRUE){
                user.setDeleted(Boolean.FALSE);
                repoControllerUF.saveUser(user);
                return;
            }
        } catch (RepoException e) {
            User user = new User(firstName, lastName, Boolean.FALSE);
            user.setId(UUID.randomUUID());
            repoControllerUF.saveUser(user);
            return;
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
        repoControllerUF.modifyUser(names1, names2);
    }

    /**
     * Method to delete User
     * @param firstName - first name of user
     * @param lastName - last name of user
     * @throws ServiceException - if user doesn't exist
     */
    public void removeUser(String firstName, String lastName) throws RepoException {
        User user = repoControllerUF.findUserByName(firstName, lastName);
        repoControllerUF.deleteUser(user.getId());
    }

    /**
     * Method to get string of users
     * @return - string of users
     */
    public String getUsersString() {
        Iterable<User> users = repoControllerUF.findAllUsers();
        StringBuilder usersString = new StringBuilder();
        for (User user : users) {
            usersString.append(user.toString()).append("\n");
        }
        return usersString.toString();
    }

    /**
     * Method to find all users
     * @return - Iterable of Users
     */
    public Iterable<User> findAllUsers() {
        return repoControllerUF.findAllUsers();
    }

    /**
     * Method to find user by names
     * @param firstName - first name of user
     * @param lastName  - last name of user
     * @return - User
     * @throws RepoException - if user doesn't exist
     */
    public User findUser(String firstName, String lastName) throws RepoException {
        return repoControllerUF.findUserByName(firstName, lastName);
    }

    /**
     * Method to find user by id
     * @param id - id of user
     * @return - User
     * @throws RepoException - if user doesn't exist
     */
    public User findUser(UUID id) throws RepoException {
        return repoControllerUF.findUser(id);
    }

    /**
     * Method to build community based on user's friendships
     * @param user - user
     * @param community - community
     */
    public void buildCommunity(User user, Community community) {
        user.setCommunity(community);
        community.addMember(user);
        for (User friend : user.getFriends()) {
            if (friend.hasCommunity() == false) {
                buildCommunity(friend, community);
            }
        }
    }

    /**
     * Method to clear friendLists of all users
     */
    public void clearFriendsLists() {
        Iterable<User> users = repoControllerUF.findAllUsers();
        for (User user : users) {
            user.clearFriendsList();
        }
    }


    public void removeUserFriendships(User user) {
        repoControllerUF.removeUserFriendships(user);
    }
}
