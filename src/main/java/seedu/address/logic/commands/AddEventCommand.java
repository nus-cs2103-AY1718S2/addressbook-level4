package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_START_TIME;

import seedu.address.GoogleCalendar.AddEventManager;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.GoogleCalendar.AddCalendarEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.CalendarEvent.CalendarEvent;

//@@author wayneong95

/**
 * Adds an event to the Google Calendar.
 */
public class AddEventCommand extends Command {

    public static final String COMMAND_WORD = "addEvent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to the Google Calendar. "
            + "Parameters: "
            + PREFIX_EVENT_NAME + "EVENT NAME "
            + PREFIX_EVENT_START_DATE + "EVENT START DATE "
            + PREFIX_EVENT_START_TIME + "EVENT START TIME "
            + PREFIX_EVENT_END_DATE + "EVENT END DATE "
            + PREFIX_EVENT_END_TIME + "EVENT END TIME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EVENT_NAME + "Weights Training "
            + PREFIX_EVENT_START_DATE + "2017-11-30 "
            + PREFIX_EVENT_START_TIME + "11:30 "
            + PREFIX_EVENT_END_DATE + "2017-11-30 "
            + PREFIX_EVENT_END_TIME + "13:30 ";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";

    private final CalendarEvent toAdd;

    /**
     * Creates an AddEventCommand to add the specified {@code CalendarEvent}
     */
    public AddEventCommand(CalendarEvent calendarEvent) {
        requireNonNull(calendarEvent);
        toAdd = calendarEvent;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(toAdd);
        AddEventManager.init();
        EventsCenter.getInstance().post(new AddCalendarEvent(toAdd));
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddEventCommand // instanceof handles nulls
                && toAdd.equals(((AddEventCommand) other).toAdd));
    }
}
