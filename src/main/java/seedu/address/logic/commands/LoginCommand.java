package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;

public class LoginCommand extends Command{
    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Login as student or librarian.\n"
            + "Parameters: USERNAME PASSWORD\n"
            + "Example: " + COMMAND_WORD + " MyUsername MyPassword";

    public static final String MESSAGE_LOGGED_IN_AS_STUTENT = "You are logged in as student";
    public static final String MESSAGE_LOGGED_IN_AS_LIBRARIAN = "You are logged in as librarian";
    public static final String MESSAGE_NOT_LOGGED_IN = "Wrong username/password, try again";

    public static final int PRIVILEGE_LEVEL = Model.PRIVILEGE_LEVEL_GUEST;

    private final String username;
    private final String password;


    public LoginCommand(String username, String password) {
        requireNonNull(username);
        requireNonNull(password);
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoginCommand // instanceof handles nulls
                && username.equals(((LoginCommand) other).username)
                && password.equals(((LoginCommand) other).password));
    }

    @Override
    public CommandResult execute() {
        int newPrivilegeLevel = model.authenticate(username,password);
        switch (newPrivilegeLevel){
            case Model.PRIVILEGE_LEVEL_GUEST:
                return new CommandResult(MESSAGE_NOT_LOGGED_IN);
            case Model.PRIVILEGE_LEVEL_STUDENT:
                return new CommandResult(MESSAGE_LOGGED_IN_AS_STUTENT);
            case Model.PRIVILEGE_LEVEL_LIBRARIAN:
                return new CommandResult(MESSAGE_LOGGED_IN_AS_LIBRARIAN);
            default:
                return new CommandResult(MESSAGE_NOT_LOGGED_IN);
        }
    }

    @Override
    public int getPrivilegeLevel(){
        return PRIVILEGE_LEVEL;
    }
}
