package socialnetwork.domain.validators;

import socialnetwork.domain.exceptions.ValidationException;

/**
 * Class models ValidatorFriendship type of entity
 * Singleton Pattern
 */
public class ValidatorMenu implements Validator<String> {
    /**
     * Instance of validator - singleton
     */
    private static ValidatorMenu instance;

    /**
     * Constructor for ValidatorMenu - private to prevent instantiation
     */
    private ValidatorMenu() {
    }
    /**
     * Method to get Instance of ValidatorMenu
     * @return instance - ValidatorMenu
     */
    public static synchronized ValidatorMenu getInstance() {
        if (instance == null) {
            instance = new ValidatorMenu();
        }
        return instance;
    }

    /**
     * Method to validate menu option
     * @param input - entity to validate
     * @throws ValidationException - if entity is invalid
     */
    @Override
    public void validate(String input) throws ValidationException {
        String problems = "";
        if (input.length() > 30) {
            problems += "Length too big\n";
        } else if (input.equals("")) {
            problems += "Is empty\n";
        }
        if(!problems.equals("")){
            throw new ValidationException(problems);
        }
    }
}
