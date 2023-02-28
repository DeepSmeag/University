package socialnetwork.domain.utils;

import socialnetwork.domain.Entities.Community;
import socialnetwork.domain.Entities.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Utility class used to calculate longest path in a community (modeled as a graph)
 */
public class LongestPathCommunity {
    /**
     * Community member of class
     */
    private Community community;
    /**
     * Map of users and whether they were visited, utility to use in the longest path algorithm
     */
    private Map<UUID, Boolean> visitedUser;

    /**
     * Constructor for LongestPathCommunity
     * @param community - the community to be analyzed
     */
    public LongestPathCommunity(Community community) {
        this.community = community;
        visitedUser = new HashMap<UUID, Boolean>();
        for (User user : community.getMembers()) {
            visitedUser.put(user.getId(), false);
        }
    }
    private LongestPathCommunity() {}

    /**
     * Wrapper to calculate the length of the longest path of friendships in a community (graph)
     * @return the length of the longest path
     */
    public int getLongestPathLength() {
        int max = 0;

        for (User user : community.getMembers()) {
            for (User user2 : community.getMembers()) {
                visitedUser.put(user2.getId(), false);
            }
            int current = getLongestPath(user);
            if (current > max) {
                max = current;
            }

        }
        return max-1;
    }

    /**
     *
     * @param user - the user from which we start the search
     * @return the length of the longest path of users from the starting point
     */
    private int getLongestPath(User user) {
        visitedUser.put(user.getId(), true);
        int max = 0;
        for (User friend : user.getFriends()) {
            if (!visitedUser.get(friend.getId())) {
                int current = getLongestPath(friend);
                if (current > max) {
                    max = current;
                }
            }
        }
        return max+1;
    }
}
