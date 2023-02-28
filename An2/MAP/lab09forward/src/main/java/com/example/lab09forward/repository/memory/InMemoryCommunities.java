package com.example.lab09forward.repository.memory;

import com.example.lab09forward.domain.Entities.Community;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class models InMemoryCommunities type of repository
 */
public class InMemoryCommunities implements Iterable<Community> {
    /**
     * communities class member
     */
    List<Community> communities;

    /**
     * Constructor for InMemoryCommunities
     */
    public InMemoryCommunities() {
        communities = new ArrayList<Community>();
    }

    /**
     * Method to add community to repository
     * @param community - Community
     */
    public void addCommunity(Community community) {
        communities.add(community);
    }

    /**
     * Method to get communities from repository
     * @return - List of communities
     */
    public List<Community> getCommunities() {
        return communities;
    }

    /**
     * Method to generate a new community and add it to repository
     * @return - new community
     */
    public Community generateCommunity() {
        Community community = new Community();
        addCommunity(community);
        return community;
    }

    /**
     * Method to get number of communities
     * @return - number of communities
     */
    public int getSize() {
        return communities.size();
    }

    /**
     * Method to get iterator for communities
     * @return - iterator for communities
     */
    @Override
    public Iterator<Community> iterator() {
        return communities.iterator();
    }

}
