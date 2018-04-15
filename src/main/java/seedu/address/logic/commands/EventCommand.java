package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.activity.Event;
import seedu.address.model.activity.exceptions.DuplicateActivityException;

//@@author Kyomian
/**
 * Adds an event to the desk board
 */
public class EventCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "event";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to desk board. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_START_DATETIME + " START DATETIME "
            + PREFIX_END_DATETIME + "END DATETIME "
            + PREFIX_LOCATION + "LOCATION "
            + "[" + PREFIX_REMARK + "REMARK]"
            + "[" + PREFIX_TAG + "TAG]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Software Project "
            + PREFIX_START_DATETIME + "01/05/2018 08:00 "
            + PREFIX_END_DATETIME + "01/08/2018 08:00 "
            + PREFIX_LOCATION + "School of Computing "
            + PREFIX_REMARK + "Bring laptop charger "
            + PREFIX_TAG + "Important";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the desk board";

    private final Event toAdd;

    /**
     * Creates a EventCommand to add the specified {@code Event}
     */
    public EventCommand(Event event) {
        requireNonNull(event);
        toAdd = event;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addActivity(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateActivityException e) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventCommand // instanceof handles nulls
                && toAdd.equals(((EventCommand) other).toAdd));
    }
}
