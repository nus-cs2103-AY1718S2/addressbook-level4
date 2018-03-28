package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TITLE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.event.CalendarEvent;
import seedu.address.model.event.Event;

/**
 * Adds an event to event book.
 */
public class AddEventCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addevent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a event to the event book. "
            + "Parameters: "
            + PREFIX_EVENT_TITLE + "NAME "
            + PREFIX_EVENT_DESCRIPTION + "DESCRIPTION "
            + PREFIX_EVENT_LOCATION + "LOCATION "
            + PREFIX_EVENT_DATETIME + "DATETIME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EVENT_TITLE + "Halloween Horror Night "
            + PREFIX_EVENT_DESCRIPTION + "Horrifying night "
            + PREFIX_EVENT_LOCATION + "Universal Studio "
            + PREFIX_EVENT_DATETIME + "13-10-2017 2359";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the event book";

    private final CalendarEvent toAdd;

    /**
     * Creates an AddEventCommand to add the specified {@code ReadOnlyEvent}
     */
    public AddEventCommand(CalendarEvent event) {
        toAdd = new Event(event);
    }

    public static String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addEvent(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (Error e) {
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
