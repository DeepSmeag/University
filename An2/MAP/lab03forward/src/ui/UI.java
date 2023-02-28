package socialnetwork.ui;

import socialnetwork.domain.exceptions.ValidationException;
import socialnetwork.domain.utils.TextColor;
import socialnetwork.domain.validators.Validator;
import socialnetwork.domain.validators.ValidatorDate;
import socialnetwork.domain.validators.ValidatorMenu;
import socialnetwork.service.NetworkService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Class models UI type of user interface
 */
public class UI implements TextColor {
    /**
     * networkService - instance of NetworkService
     */
    NetworkService networkService;

    /**
     * Constructor for UI
     * Builds instance of networkService
     */
    public UI() {
        networkService = NetworkService.getInstance();
    }

    /**
     * Main starter for UI
     */
    public void start() {
        mainLoop();
    }

    /**
     * Main loop for UI
     */
    protected void mainLoop() {
        while (true) {
            // Show menu
            showMenu();
            // Get input
            String input = getMenuInput();
            try {
                // Validate input - display error and return to loop if incorrect
                Validator validator = ValidatorMenu.getInstance();
                validator.validate(input);
                // Input is valid, now we check if option is correct
                switch (input) {
                    case "useradd":
                        userAddMenu();
                        break;
                    case "usermod":
                        userModMenu();
                        break;
                    case "userrm":
                        userRmMenu();
                        break;
                    case "friendadd":
                        friendAddMenu();
                        break;
                    case "friendmod":
                        friendModMenu();
                        break;
                    case "friendrm":
                        friendRmMenu();
                        break;
                    case "friendsbuild":
                        friendsBuildMenu();
                        break;
                    case "friendshow":
                        friendShowMenu();
                        break;
                    case "commbuild":
                        commBuildMenu();
                        break;
                    case "commshow":
                        commShowMenu();
                        break;
                    case "mostsocial":
                        mostSocialMenu();
                        break;
                    case "printusers":
                        printAllUsers();
                        break;
                    case "printfriendships":
                        printAllFriendships();
                        break;
                    case "exit":
                    case "0":
                        return;
                    default:
                        throw new ValidationException("Not a valid option");
                }
            } catch (RuntimeException exc) {
                System.out.println(exc.getMessage());
            }
            // Pick corresponding option - move to another menu or something if needed
        }
    }


    /**
     * Method to show menu - utility
     */
    private void showMenu() {
        System.out.print(
                "----------------------------------------\n" +
                        "Choose an option\n" +
                        "\n" +
                        "useradd - Add a user\n" +
                        "usermod - Modify a user\n" +
                        "userrm - Remove a user\n" +
                        "\n" +
                        "friendadd - Add a friend to a user\n" +
                        "friendmod - Modify a friendship (only date)\n" +
                        "friendrm - Remove a friend from a user\n" +
                        "friendsbuild - Build the list of friends for each user\n" +
                        "friendshow - Show the list of friends for a user\n" +
                        "\n" +
                        "commbuild - Build communities based on friendships\n" +
                        "commshow - Show the communities and how many there are\n" +
                        "mostsocial - Community with longest chain of friends\n" +
                        "\n" +
                        "printusers - Print all users\n" +
                        "printfriendships - Print all friendships\n" +
                        "\n" +
                        "exit\n" +
                        "----------------------------------------\n"
        );
    }

    /**
     * Method to handle mostsocial menu option
     */
    private void mostSocialMenu() {
        if (!networkService.communitiesExist()) {
            System.out.println(ANSI_RED + "No communities exist. Building..." + ANSI_RESET);
            commBuildMenu();
        }
        System.out.println(ANSI_GREEN + "Length of biggest path in a community: " + networkService.mostSocial() + ANSI_RESET);
    }

    /**
     * Method to handle commshow menu option
     */
    private void commShowMenu() {
        if (!networkService.communitiesExist()) {
            System.out.println(ANSI_RED + "No communities exist. Building..." + ANSI_RESET);
            commBuildMenu();
        }
        System.out.println(ANSI_YELLOW + networkService.getCommunitiesString() + ANSI_RESET);
    }

    /**
     * Method to handle commbuild menu option
     */
    private void commBuildMenu() {
        networkService.buildFriendsLists();
        networkService.buildCommunities();
        System.out.println(ANSI_GREEN + "Communities built!" + ANSI_RESET);
    }

    /**
     * Method to handle friendshow menu option
     */
    private void friendShowMenu() {
        System.out.println("Friends Show Menu");
        System.out.println("Names of user to show friends:");
        List<String> names = getUserNames();
        try {
            String friendsList = networkService.getFriendsOfUser(names.get(0), names.get(1));
            System.out.println(ANSI_YELLOW + friendsList + ANSI_RESET);
        } catch (RuntimeException exc) {
            System.out.println(exc.getMessage());
        }
    }

    /**
     * Method to handle friendsbuild menu option
     */
    private void friendsBuildMenu() {
        networkService.buildFriendsLists();
        System.out.println(ANSI_GREEN + "Built lists of friends!" + ANSI_RESET);
    }

    /**
     * Method to handle printfriendships menu option
     */
    private void printAllFriendships() {
        System.out.println(ANSI_YELLOW + networkService.getFriendshipsString() + ANSI_RESET);
    }

    /**
     * Method to handle friendadd menu option
     */
    protected void friendAddMenu() {
        System.out.println("Friend Add Menu");
        System.out.println("Names of first user:");
        List<String> names1 = getUserNames();
        System.out.println("Names of 2nd user:");
        List<String> names2 = getUserNames();
        System.out.println("Date of friendship (YYYY-MM-DD):");
        String date = getDateInput();
        try {
            networkService.addFriendship(names1, names2, date);
            Validator validator = ValidatorDate.getInstance();
            validator.validate(date);
            System.out.println(ANSI_GREEN + "Friendship added successfully!" + ANSI_RESET);
        } catch (RuntimeException exc) {
            System.out.println(exc.getMessage());
        }
    }

    /**
     * Method to handle friendmod menu option
     */
    private void friendModMenu() {
        try {
            System.out.println("Names of first user:");
            List<String> names1 = getUserNames();
            System.out.println("Names of 2nd user:");
            List<String> names2 = getUserNames();
            System.out.println("New date (yyyy-mm-dd):");
            String date = getDateInput();
            Validator validator = ValidatorDate.getInstance();
            validator.validate(date);
            networkService.modifyFriendship(names1, names2, date);
        } catch (RuntimeException exc) {
            System.out.println(exc.getMessage());
        }
    }

    private String getDateInput() {
        Scanner scanner = new Scanner(System.in);
        String date = scanner.nextLine();

        return date;
    }

    /**
     * Method to handle friendrm menu option
     */
    protected void friendRmMenu() {
        System.out.println("Friend Remove Menu");
        System.out.println("Names of first user:");
        List<String> names1 = getUserNames();
        System.out.println("Names of 2nd user:");
        List<String> names2 = getUserNames();
        try {
            networkService.removeFriendship(names1, names2);
            System.out.println(ANSI_GREEN + "Friendship removed successfully!" + ANSI_RESET);
        } catch (RuntimeException exc) {
            System.out.println(exc.getMessage());
        }
    }

    /**
     * Method to handle printusers menu option
     */
    private void printAllUsers() {
        System.out.println(networkService.getUsersString());
    }


    /**
     * Method to handle useradd menu option
     */
    protected void userAddMenu() {
        try {
            List<String> names = getUserNames();
            networkService.addUser(names.get(0), names.get(1));
            System.out.println("\n==========User successfully added=========\n");
        } catch (RuntimeException exc) {
            System.out.println(exc.getMessage());
        }
    }

    /**
     * Method to handle usermod menu option
     */
    private void userModMenu() {
        System.out.println("Names of user to modify:");
        List<String> names1 = getUserNames();
        System.out.println("New names:");
        List<String> names2 = getUserNames();

        try {
            networkService.modUser(names1, names2);
            System.out.println("\n==========User successfully modified=========\n");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method to handle userrm menu option
     */
    protected void userRmMenu() {
        try {
            List<String> names = getUserNames();
            networkService.removeUser(names.get(0), names.get(1));
            System.out.println("\n=========User successfully removed=========\n");
        } catch (RuntimeException exc) {
            System.out.println(exc.getMessage());
        }
    }

    /**
     * Method to get menu input
     *
     * @return - String with input
     */
    protected String getMenuInput() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        return input;
    }

    /**
     * Method to get usernames from user - utility to get input
     *
     * @return List of names
     */
    protected List<String> getUserNames() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("User first name: ");
        String firstName = scanner.nextLine();
        System.out.print("\n");
        System.out.print("User last name: ");
        String lastName = scanner.nextLine();
        System.out.print("\n");
        return new ArrayList<String>(Arrays.asList(firstName, lastName));
    }
}
