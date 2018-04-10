package seedu.organizer.logic.commands;

import static seedu.organizer.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.organizer.model.ModelManager.getCurrentlyLoggedInUser;

import seedu.organizer.logic.commands.exceptions.CommandException;
import seedu.organizer.model.user.User;
import seedu.organizer.model.user.UserWithQuestionAnswer;

//@@author dominickenn
/**
 * Adds a question-answer set to the currently logged in user.
 */
public class AddQuestionAnswerCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addqa";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a question answer set to the current user."
            + "The existing question answer set will be overwritten by the input values.\n"
            + "Parameters: "
            + PREFIX_QUESTION + "QUESTION "
            + PREFIX_ANSWER + "ANSWER\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_QUESTION + "What cats do you like? "
            + PREFIX_ANSWER + "All cats ";

    public static final String MESSAGE_ADD_QUESTION_ANSWER_SUCCESS = "Added question answer set to user: %1$s";

    private User userToEdit;
    private UserWithQuestionAnswer editedUser;
    private String question;
    private String answer;

    /**
     * Creates an AddQuestionAnswerCommand
     * to add the specified {@code question} and {@code answer} set
     * to the currently logged in user
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
        requireAllNonNull(userToEdit, editedUser, model);
        model.addQuestionAnswerToUser(userToEdit, editedUser);
        return new CommandResult(String.format(MESSAGE_ADD_QUESTION_ANSWER_SUCCESS, editedUser));
    }

    /**
     * Creates and returns a UserWithQuestionAnswer with the current user, and the question and answer
     */
    private UserWithQuestionAnswer createEditedUser() {
        requireAllNonNull(userToEdit, question, answer);
        return new UserWithQuestionAnswer(
                userToEdit.username,
                userToEdit.password,
                question,
                answer);
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
        AddQuestionAnswerCommand otherCommand = (AddQuestionAnswerCommand) other;
        return userToEdit.equals(otherCommand.userToEdit)
                && question.equals(otherCommand.question)
                && answer.equals(otherCommand.answer);
    }

}

