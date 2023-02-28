package socialnetwork.domain.Entities;

import socialnetwork.domain.Utilizator;

import java.util.*;

/**
 * Entity class models User of the Network
 */
public class User extends Entity<UUID> implements CommunityMember {
    /**
     * firstName class member
     * type - String
     */
    protected String firstName;
    /**
     * community class member of user instance
     * type - Community
     */
    protected Community community;
    /**
     * lastName class member
     * type - String
     */
    protected String lastName;
    /**
     * friendList class member
     * type - List of User
     */
    protected List<User> friendList;

    protected Boolean deleted;

    /**
     * Getter for firstName class member
     * @return firstName - String
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter for firstName class member
     * @param firstName - String
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter for lastName class member
     * @return lastName - String
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter for lastName class member
     * @param lastName - String
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter for friendList
     * @return - friendList instance
     */
    public List<User> getFriends() {
        return friendList;
    }
    /**
     * Constructor for User class
     * @param firstName - String
     * @param lastName - String
     */
    public User(String firstName, String lastName, Boolean deleted) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.deleted = deleted;
        friendList = new ArrayList<User>();
        community = null;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * Constructor for User class - private to prevent instantiation without parameters
     */
    private User() {}

    /**
     * Method to add a friend to the friendList
     * @param user - User
     */
    public void addFriend(User user) {
        if(friendList == null) {
            friendList = new ArrayList<User>();
        }
        friendList.add(user);
    }

    /**
     * Method to convert user instance to string
     * @return - String
     */
    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
//                ", friends=" + friendList +
                '}';
    }

    /**
     * Method to check if two users are equal
     * @param o - Object ; other user
     * @return - boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Utilizator)) return false;
        Utilizator that = (Utilizator) o;
        return getFirstName().equals(that.getFirstName()) &&
                getLastName().equals(that.getLastName()) &&
                getFriends().equals(that.getFriends());
    }

    /**
     * Method to get hashcode of user instance
     * @return - int ; hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getFriends());
    }

    /**
     * Method to get string of friends of user instance
     * @return - String ; the string of friends
     */
    public String getFriendsString() {
        StringBuilder friendsString = new StringBuilder();
        for(User friend : friendList) {
            friendsString.append(friend.getFirstName()).append(" ").append(friend.getLastName()).append(" \n");
        }
        return friendsString.toString();
    }

    /**
     * Getter for community class member
     * @return - community instance
     */
    public Community getCommunity() {
        return community;
    }

    /**
     * Setter for community class member
     * @param community - Community
     */
    @Override
    public void setCommunity(Community community) {
        this.community = community;
    }

    /**
     * Method to verify if user is in a community
     * @return - boolean
     */
    @Override
    public boolean hasCommunity() {
        if(this.community == null) {
            return false;
        }
        return true;
    }

    /**
     * Method to clear friendList of user instance
     */
    public void clearFriendsList() {
        friendList.clear();
    }
}
