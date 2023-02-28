package socialnetwork.domain.Entities;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

/**
 * Entity class models Friendship between two Users
 */
public class Friendship extends Entity<UUID> {
    /**
     * id1 class member; id of first user
     * type - UUID
     */
    protected UUID id1;
    /**
     * id2 class member; id of second user
     * type - UUID
     */
    protected UUID id2;

    /**
     * date class member; date of friendship
     * type - LocalDate
     */
    protected LocalDate date;

    /**
     * confirmed class member; if friendship is confirmed
     */
    protected Boolean confirmed;

    /**
     * Constructor for Friendship
     *
     * @param id1  - UUID of first user
     * @param id2  - UUID of second user
     * @param date
     */
    public Friendship(UUID id1, UUID id2, LocalDate date, Boolean confirmed) {
        this.id1 = id1;
        this.id2 = id2;
        this.date = date;
        this.confirmed = confirmed;
    }
    /**
     * Getter for id1 class member
     * @return id1 - UUID of first user
     */
    public UUID getId1() {
        return id1;
    }
    /**
     * Getter for id2 class member
     * @return id2 - UUID of second user
     */
    public UUID getId2() {
        return id2;
    }

    /**
     * Getter for date class member
     * @return date - date of friendship
     */
    public LocalDate getDate() {
        return date;
    }
    /**
     * Setter for date class member
     * @param date - date of friendship
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    /**
     * Method to convert friendship to string
     * @return String representation of friendship
     */
    @Override
    public String toString() {
        return "------------------\n" + id1.toString() + "\n" + id2.toString() + " " + date.toString() + "\n------------------\n";
    }


}
