package com.example.lab09forward.domain.Entities;

/**
 * Interface class models CommunityMember type of entity
 * - primarily used for User class
 */
public interface CommunityMember {
    /**
     * Method to get community of CommunityMember
     * @return community - Community
     */
    Community getCommunity();

    /**
     * Method to set community of CommunityMember
     * @param community - Community
     */
    void setCommunity(Community community);

    /**
     * Method to check if member is in any community
     * @return - true if member is in any community
     */
    boolean hasCommunity();
}
