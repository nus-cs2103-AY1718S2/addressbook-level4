package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.LoginManager;
import seedu.address.model.user.User;
import seedu.address.model.user.exceptions.DuplicateUserException;

//@@author Pearlissa
/**
 * A utility class containing a list of {@code User} objects to be used in tests.
 */
public class TypicalUsers {

    public static final User JOHNDOE = new UserBuilder().withUsername("JOHNDOE")
            .withPassword("12345678").build();
    public static final User ALICE = new UserBuilder().withUsername("ALICE")
            .withPassword("abcdefgh").build();
    public static final User BENSON = new UserBuilder().withUsername("BENSON")
            .withPassword("1234ABCDE").build();
    public static final User CARL = new UserBuilder().withUsername("CARL")
            .withPassword("1a2b3c4d5e").build();
    public static final User DANIEL = new UserBuilder().withUsername("DANIEL")
            .withPassword("password").build();
    public static final User ELLE = new UserBuilder().withUsername("ELLE")
            .withPassword("elleelle").build();
    public static final User FIONA = new UserBuilder().withUsername("FIONA")
            .withPassword("1223334444").build();
    public static final User GEORGE = new UserBuilder().withUsername("GEORGE")
            .withPassword("george123").build();

    // Manually added
    public static final User HOON = new UserBuilder().withUsername("HOON")
            .withPassword("meehoonkueh").build();
    public static final User IDA = new UserBuilder().withUsername("IDA")
            .withPassword("idanotaho").build();

    private TypicalUsers() {} // prevents instantiation

    /**
     * Returns a {@code LoginManager} with all the typical users.
     */
    public static LoginManager getTypicalLoginManager() {
        LoginManager lm = new LoginManager();
        for (User user : getTypicalUsers()) {
            try {
                lm.addUser(user);
            } catch (DuplicateUserException e) {
                throw new AssertionError("not possible");
            }
        }
        return lm;
    }

    public static List<User> getTypicalUsers() {
        return new ArrayList<>(Arrays.asList(JOHNDOE, ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
