package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.event.DuplicateEventException;
import seedu.address.model.event.Event;

//@@author LeonidAgarth
/**
 * Adds an event to the address book.
 */
public class AddEventCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addEvent";
    public static final String COMMAND_ALIAS = "ae";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_VENUE + "VENUE "
            + PREFIX_DATE + "DATE "
            + PREFIX_START_TIME + "STARTTIME "
            + PREFIX_END_TIME + "ENDTIME \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "F1 Race "
            + PREFIX_VENUE + "Marina Bay Street Circuit "
            + PREFIX_DATE + "19/07/2017 "
            + PREFIX_START_TIME + "1000 "
            + PREFIX_END_TIME + "1300\n"
            + "Note: DATE must be in the format of DD/MM/YYYY\n"
            + "      TIME must be in the format of HHmm\n";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the address book";

    private final Event toAdd;

    /**
     * Creates an AddEventCommand to add the specified {@code Event}
     */
    public AddEventCommand(Event event) {
        requireNonNull(event);
        toAdd = event;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addEvent(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateEventException e) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddEventCommand // instanceof handles nulls
                && toAdd.equals(((AddEventCommand) other).toAdd));
    }
}
