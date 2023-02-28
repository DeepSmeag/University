import domain.User;
import repository.UserDBRepository;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/academic";

        UserDBRepository userDBRepository = new UserDBRepository(url, "postgres", "postgres");
        User user = new User("ccc", "bbb");
        userDBRepository.save(user);
        userDBRepository.findAll().forEach(System.out::println);
    }
}