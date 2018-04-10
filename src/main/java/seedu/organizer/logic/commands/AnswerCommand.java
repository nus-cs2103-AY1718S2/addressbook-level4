package seedu.organizer.logic.commands;

import static seedu.organizer.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.organizer.logic.commands.exceptions.CommandException;
import seedu.organizer.model.user.User;
import seedu.organizer.model.user.UserWithQuestionAnswer;
import seedu.organizer.model.user.exceptions.UserNotFoundException;

//@@author dominickenn
/**
 * Answer a question correctly to retrieve a user's password.
 */
public class AnswerCommand extends Command {

    public static final String COMMAND_WORD = "answer";
    public static final String COMMAND_ALIAS = "ans";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": If the user exists, "
            + "answer a user's question\n"
            + "Parameters: " + PREFIX_USERNAME + "USERNAME"
            + PREFIX_ANSWER + "ANSWER\n"
            + "Example: " + COMMAND_WORD
            + PREFIX_USERNAME + "david"
            + PREFIX_ANSWER + "yes";

    public static final String MESSAGE_SUCCESS = "Answer correct!\nPassword: %1$s";
    public static final String MESSAGE_NO_QUESTION = "User %1$s does not have a question";
    public static final String MESSAGE_WRONG_ANSWER = "Answer is incorrect";
    public static final String MESSAGE_USER_DOES_NOT_EXIST = "User does not exist";

    private String username;
    private String answer;

    /**
     * Creates an AnswerCommand to answer
     * the specified User with the {@code username}
     * with the {@code answer}
     * to attempt to retrieve the User's password
     */
    public AnswerCommand(String username, String answer) {
        requireAllNonNull(username, answer);
        this.username = username;
        this.answer = answer;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireAllNonNull(username, answer, model);
        User user;
        try {
            user = model.getUserByUsername(username);
        } catch (UserNotFoundException unf) {
            throw new CommandException(MESSAGE_USER_DOES_NOT_EXIST);
        }
        if (user instanceof UserWithQuestionAnswer) {
            String answer = ((UserWithQuestionAnswer) user).answer;
            String password = user.password;
            return answerQuestionAndReturnResult(answer, password);
        } else {
            return new CommandResult(String.format(MESSAGE_NO_QUESTION, username));
        }
    }

    /**
     * Returns the password if the answer is correct
     */
    private CommandResult answerQuestionAndReturnResult(String answer, String password) {
        requireAllNonNull(answer, password);
        if (answer.equals(this.answer)) {
            return new CommandResult(String.format(MESSAGE_SUCCESS, password));
        } else {
            return new CommandResult(MESSAGE_WRONG_ANSWER);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AnswerCommand // instanceof handles nulls
                && this.username.equals(((AnswerCommand) other).username) // state check
                && this.answer.equals(((AnswerCommand) other).answer));
    }
}
