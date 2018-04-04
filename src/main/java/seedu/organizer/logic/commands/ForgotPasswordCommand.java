package seedu.organizer.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.organizer.model.user.User;
import seedu.organizer.model.user.UserWithQuestionAnswer;
import seedu.organizer.model.user.exceptions.UserNotFoundException;

//@@author dominickenn
/**
 * Finds a user in PrioriTask with the given username.
 */
public class ForgotPasswordCommand extends Command {

    public static final String COMMAND_WORD = "forgotpassword";
    public static final String COMMAND_ALIAS = "fp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Retrieves question from user"
            + " with the username, if the user exists. \n"
            + "Parameters: " + PREFIX_USERNAME + " USERNAME\n"
            + "Example: " + COMMAND_WORD + PREFIX_USERNAME + "david";

    public static final String MESSAGE_SUCCESS = "Question: %1$s";
    public static final String MESSAGE_NO_QUESTION = "User %1$s does not have a question";

    private String username;

    public ForgotPasswordCommand(String username) {
        requireNonNull(username);
        this.username = username;
    }

    @Override
    public CommandResult execute() {
        User user;
        try {
            user = model.getUserByUsername(username);
        } catch (UserNotFoundException e) {
            throw new AssertionError("User does not exist");
        }
        if (user instanceof UserWithQuestionAnswer) {
            String question = ((UserWithQuestionAnswer) user).question;
            return new CommandResult(String.format(MESSAGE_SUCCESS, question));
        } else {
            return new CommandResult(String.format(MESSAGE_NO_QUESTION, username));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ForgotPasswordCommand // instanceof handles nulls
                && this.username.equals(((ForgotPasswordCommand) other).username)); // state check
    }
}

