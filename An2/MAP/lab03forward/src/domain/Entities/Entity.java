package socialnetwork.domain.Entities;

import java.io.Serializable;

/**
 * Entity class models a generic Entity
 * @param <ID> - type of id
 *            - UUID for User
 *            - UUID for Friendship
 * - primarily to be used as a base class for other entities give them an ID
 */
public class Entity<ID> implements Serializable {
    /**
     * public constructor
     */
    public Entity () {
    }
    /**
     * A constant representing the default value of the id ?????????
     */
    private static final long serialVersionUID = 7331115341259248461L;
    /**
     * id class member
     * type - ID
     */
    private ID id;

    /**
     * Getter for id class member
     * @return id - ID
     */
    public ID getId() {
        return id;
    }

    /**
     * Setter for id class member
     * @param id - ID
     */
    public void setId(ID id) {
        this.id = id;
    }
}