package seedu.address.logic.commands;
//@@author crizyli
import seedu.address.logic.Authentication;

public class AuthenCommand extends Command {

    public static final String COMMAND_WORD = "authenET";

    public static final String MESSAGE_SUCCESS = "You have authorized ET!";

    public static final String MESSAGE_FAILURE = "You haven't authorized ET successfully,"
            + " please try it again later";


    @Override
    public CommandResult execute() {
        boolean isSuccessful = Authentication.authen();

        if (isSuccessful) {
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_FAILURE);
        }

    }
}
