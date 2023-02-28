package socialnetwork.service;

import socialnetwork.domain.Entities.Community;
import socialnetwork.domain.Entities.Friendship;
import socialnetwork.domain.Entities.User;
import socialnetwork.domain.exceptions.RepoException;
import socialnetwork.domain.exceptions.ServiceException;
import socialnetwork.domain.utils.LongestPathCommunity;
import socialnetwork.repository.memory.InMemoryCommunities;
import socialnetwork.service.friendships.ServiceFriendships;
import socialnetwork.service.users.ServiceUsers;

import java.util.List;

/**
 * Class models NetworkService type of service
 * Singleton Pattern
 */
public class NetworkService {
    /**
     * instance - instance of NetworkService
     */
    private static NetworkService instance;
    /**
     * serviceUsers - instance of ServiceUsers
     */
    private static ServiceUsers serviceUsers = ServiceUsers.getInstance();
    /**
     * serviceFriendships - instance of ServiceFriendships
     */
    private static ServiceFriendships serviceFriendships = ServiceFriendships.getInstance();
    /**
     * Instance of InMemoryCommunities - repository for communities
     */
    private InMemoryCommunities communities = new InMemoryCommunities();

    /**
     * Constructor for NetworkService - private because of Singleton Pattern
     */
    private NetworkService() {
    }

    /**
     * Method to get Instance of NetworkService
     *
     * @return - instance of NetworkService
     */
    public static synchronized NetworkService getInstance() {
        if (instance == null) {
            instance = new NetworkService();
        }
        return instance;
    }

    /**
     * Method to add Friendship between 2 users given names
     *
     * @param names1 - String
     * @param names2 - String
     * @throws ServiceException - if friendship already exists
     */
    public void addFriendship(List<String> names1, List<String> names2, String date) throws ServiceException {
        try {
            User user1 = serviceUsers.findUser(names1.get(0), names1.get(1));
            User user2 = serviceUsers.findUser(names2.get(0), names2.get(1));
            serviceFriendships.addFriendship(user1, user2, date);
        } catch (RepoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * Method to remove Friendship between 2 users given names
     *
     * @param names1 - List of String
     * @param names2 - List of String
     * @throws ServiceException - if friendship doesn't exist
     */
    public void removeFriendship(List<String> names1, List<String> names2) throws ServiceException {
        try {
            User user1 = serviceUsers.findUser(names1.get(0), names1.get(1));
            User user2 = serviceUsers.findUser(names2.get(0), names2.get(1));
            serviceFriendships.removeFriendship(user1, user2);
        } catch (RepoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * Method to add user
     *
     * @param firstName - String
     * @param lastName  - String
     */
    public void addUser(String firstName, String lastName) {
        serviceUsers.addUser(firstName, lastName);
    }

    /**
     * Method to remove user
     *
     * @param firstName - String
     * @param lastName  - String
     */
    public void removeUser(String firstName, String lastName) {
        User user = serviceUsers.findUser(firstName, lastName);
//        buildFriendsLists();
//        List<User> friends = user.getFriends();
//        if (friends.size() > 0) {
//            for (User friend : friends) {
//                System.out.println("Friend of " + user.getFirstName() + " " + user.getLastName() + " is " + friend.getFirstName() + " " + friend.getLastName());
//                serviceFriendships.removeFriendship(friend, user);
//            }
//        }
        serviceUsers.removeUserFriendships(user);
        serviceUsers.removeUser(firstName, lastName);
    }

    /**
     * Method to get string representing all users
     *
     * @return - String
     */
    public String getUsersString() {
        return serviceUsers.getUsersString();
    }

    /**
     * Method to get string representing all friendships
     *
     * @return - String
     */
    public String getFriendshipsString() {
        Iterable<Friendship> friendships = serviceFriendships.findAllFriendships();
        StringBuilder friendshipsString = new StringBuilder("--------------------------------\n");
        for (Friendship friendship : friendships) {
            friendshipsString.append(serviceUsers.findUser(friendship.getId1()).toString()).append(" - friends with - ").append(serviceUsers.findUser(friendship.getId2()).toString()).append("\n").append(" since ").append(friendship.getDate()).append("\n");
        }
        friendshipsString.append("--------------------------------\n");
        return friendshipsString.toString();
    }

    /**
     * Method to build friendLists of all users
     */
    public void buildFriendsLists() {
        serviceUsers.clearFriendsLists();
        Iterable<Friendship> friendships = serviceFriendships.findAllFriendships();
        for (Friendship friendship : friendships) {
            serviceUsers.findUser(friendship.getId1()).addFriend(serviceUsers.findUser(friendship.getId2()));
            serviceUsers.findUser(friendship.getId2()).addFriend(serviceUsers.findUser(friendship.getId1()));
        }
    }

    /**
     * Method to get string representing friends of a user given by names
     *
     * @param firstName - first name of user
     * @param lastName  - last name of user
     * @return - string representing friends of a user given by names
     * @throws ServiceException - if user not found
     */
    public String getFriendsOfUser(String firstName, String lastName) throws ServiceException {
        buildFriendsLists();
        User user = serviceUsers.findUser(firstName, lastName);
        String friendsString = user.getFriendsString();
        if (friendsString.equals("")) {
            throw new ServiceException("User has no friends!");
        }
        return friendsString;
    }

    /**
     * Method to build communities based on friendships
     * Rebuilds friendships if needed
     */
    public void buildCommunities() {
        buildFriendsLists();
        Iterable<User> users = serviceUsers.findAllUsers();
        for (User user : users) {
            if (!user.hasCommunity()) {
                Community community = communities.generateCommunity();
                serviceUsers.buildCommunity(user, community);
            }
        }
    }

    /**
     * Method to check if communities have been built
     *
     * @return - true if communities have been built, false otherwise
     */
    public boolean communitiesExist() {
        if (communities.getCommunities().size() != 0) {
            return true;
        }
        return false;
    }

    /**
     * Method to get string representing all communities
     *
     * @return - string representing all communities
     */
    public String getCommunitiesString() {
        StringBuilder communitiesString = new StringBuilder("--------------------------------\n");
        for (Community community : communities.getCommunities()) {
            communitiesString.append(community.toString()).append("\n").append("================================\n");
        }
        communitiesString.append(String.format("Number of communities: %d\n", communities.getSize()));
        communitiesString.append("--------------------------------\n");
        return communitiesString.toString();
    }

    /**
     * Utility function to get community with the longest path
     *
     * @return - community with the longest path
     */
    public int mostSocial() {
        buildCommunities();
        int maxLength = 0;
        for (Community community : communities) {
            LongestPathCommunity longestPathCommunity = new LongestPathCommunity(community);
            int currentLength = longestPathCommunity.getLongestPathLength();
            if (currentLength > maxLength) {
                maxLength = currentLength;
            }
        }
        return maxLength;
    }

    public void modUser(List<String> names1, List<String> names2) {
        serviceUsers.modUser(names1, names2);
    }

    public void modifyFriendship(List<String> names1, List<String> names2, String date) throws RepoException {
        serviceFriendships.modifyFriendship(names1, names2, date);
    }
}
