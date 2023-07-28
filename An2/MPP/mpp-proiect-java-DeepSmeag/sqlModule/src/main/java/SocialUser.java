public class SocialUser {
    String firstName;
    String lastName;
    boolean deleted;

    SocialUser(String firstName, String lastName, boolean deleted) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.deleted = deleted;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean isDeleted() {
        return deleted;
    }
}
