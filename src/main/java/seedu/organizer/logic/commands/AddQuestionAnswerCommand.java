package seedu.organizer.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.organizer.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.organizer.model.ModelManager.getCurrentlyLoggedInUser;

import seedu.organizer.logic.commands.exceptions.CommandException;
import seedu.organizer.model.user.User;
import seedu.organizer.model.user.UserWithQuestionAnswer;

//@@author dominickenn
/**
 * Adds a question-answer to the currently logged in user.
 */
public class AddQuestionAnswerCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addqa";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a question and answer to the current user."
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: "
            + PREFIX_QUESTION + "QUESTION "
            + PREFIX_ANSWER + "ANSWER\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_QUESTION + "What cats do you like? "
            + PREFIX_ANSWER + "All cats ";

    public static final String MESSAGE_ADD_QUESTION_ANSWER_SUCCESS = "Added question and answer to user: %1$s";

    private User userToEdit;
    private UserWithQuestionAnswer editedUser;
    private String question;
    private String answer;

    /**
     * Updates current user with a user with the given question and answer
     */
    public AddQuestionAnswerCommand(String question, String answer) {
        requireAllNonNull(question, answer, getCurrentlyLoggedInUser());
        this.userToEdit = getCurrentlyLoggedInUser();
        this.question = question;
        this.answer = answer;
        editedUser = createEditedUser();
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        model.addQuestionAnswerToUser(userToEdit, editedUser);
        return new CommandResult(String.format(MESSAGE_ADD_QUESTION_ANSWER_SUCCESS, editedUser));
    }

    /**
     * Creates and returns a UserWithQuestionAnswer with the current user, and the question and answer
     */
    private UserWithQuestionAnswer createEditedUser() {
        requireNonNull(userToEdit);
        UserWithQuestionAnswer user = new UserWithQuestionAnswer(
                userToEdit.username,
                userToEdit.password,
                question,
                answer);
        return user;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddQuestionAnswerCommand)) {
            return false;
        }

        // state check
        AddQuestionAnswerCommand a = (AddQuestionAnswerCommand) other;
        return userToEdit.equals(a.userToEdit)
                && question.equals(a.question)
                && answer.equals(a.answer);
    }

}

