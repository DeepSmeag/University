package socialnetwork.domain.Entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Entity class models Community in Network
 */
public class Community extends Entity<UUID>{
    /**
     * members class member
     * type - List of User
     */
    private List<User> members;

    /**
     * Constructor for Community
     */
    public Community() {
        members = new ArrayList<User>();
    }

    /**
     * Method to add user to community
     * @param user - User
     */
    public void addMember(User user) {
        user.setCommunity(this);
        members.add(user);
    }

    /**
     * Method to check if community has a member
     * @param user - User to check
     * @return - boolean
     */
    public boolean hasMember(User user) {
        return members.contains(user);
    }

    /**
     * Method to get members of community
     * @return - List of User
     */
    public List<User> getMembers() {
        return members;
    }

    /**
     * Method to convert community to string
     * @return - String representation of community
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (User user : members) {
            stringBuilder.append("\t").append(user.toString()).append("\n");
        }
        return stringBuilder.toString();
    }
}
