package ent;

public class User extends Entity<Long> {
    private String lastName;

    public User(long id, String lastName) {
        super((long) id);
        this.lastName = lastName;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
