package socialnetwork.repository.file;

import socialnetwork.domain.Entities.Friendship;
import socialnetwork.domain.exceptions.RepoException;
import socialnetwork.domain.utils.observer.Observer;
import socialnetwork.domain.validators.Validator;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Class models FriendshipFile0 type of repository
 */
public class FriendshipFile0 extends AbstractFileRepository0<UUID, Friendship> {
    /**
     * Constructor for FriendshipFile0
     * @param fileName - String
     * @param validator - Validator
     */
    public FriendshipFile0(String fileName, Validator<Friendship> validator) {
        super(fileName, validator);
    }

    /**
     * Method to extract entity from string
     * @param attributes - String
     * @return - Friendship
     */
    @Override
    public Friendship extractEntity(List<String> attributes) {
//        Friendship friendship = new Friendship(UUID.fromString(attributes.get(1)),UUID.fromString(attributes.get(2)), LocalDate.parse(attributes.get(3)));
//        friendship.setId(UUID.fromString(attributes.get(0)));
//        return friendship;
    return null;
    }


    /**
     * Method to find entity by ids
     * @param id1 - id of first user
     * @param id2 - id of second user
     * @return - Friendship
     * @throws RepoException - if friendship does not exist
     */
    public Friendship findByUUIDs(UUID id1, UUID id2) throws RepoException {
        for (Friendship friendship : findAll()) {
            if ((friendship.getId1().equals(id1) && friendship.getId2().equals(id2) || (friendship.getId1().equals(id2) && friendship.getId2().equals(id1)))) {
                return friendship;
            }
        }
        throw new RepoException("Friendship not found!");
    }

    /**
     * Method to convert entity to string
     * @param entity - entity must be not null
     * @return - string
     */
    @Override
    protected String createEntityAsString(Friendship entity) {
        return entity.getId()+","+entity.getId1()+","+entity.getId2()+","+entity.getDate();
    }

    /**
     * Method to update the state of the file repo
     */
    public void update() {
        writeFile();
    }

}
