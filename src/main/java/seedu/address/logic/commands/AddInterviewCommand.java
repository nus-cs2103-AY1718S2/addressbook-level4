//@@author deeheenguyen
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTERVIEW;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.interview.Interview;
import seedu.address.model.interview.exceptions.DuplicateInterviewException;

/***
 * Adds an Interview to AddressBook
 */
public class AddInterviewCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addInterview";
    public static final String COMMAND_ALIAS = "ai";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an interview to the address book.\n"
            + "Parameters: "
            + PREFIX_INTERVIEW + "INTERVIEW "
            + PREFIX_NAME + "INTERVIEWEE "
            + PREFIX_DATE + "INTERVIEW_DATE "
            + PREFIX_LOCATION + "INTERVIEW_LOCATION \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INTERVIEW + "SE INTERVIEW "
            + PREFIX_NAME + "DAVID "
            + PREFIX_DATE + "04.05.2018 "
            + PREFIX_LOCATION + "SUNTEX CITY ";

    public static final String MESSAGE_SUCCESS = "New interview added: ";
    public static final String MESSAGE_DUPLICATE_INTERVIEW = "This interview already exists in the address book";

    private final Interview toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Job}
     */
    public AddInterviewCommand(Interview interview) {
        requireNonNull(interview);
        toAdd = interview;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addInterview(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateInterviewException e) {
            throw new CommandException(MESSAGE_DUPLICATE_INTERVIEW);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddInterviewCommand // instanceof handles nulls
                && toAdd.equals(((AddInterviewCommand) other).toAdd));
    }
}
