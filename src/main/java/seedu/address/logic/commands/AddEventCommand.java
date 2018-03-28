package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.DisplayCalendarRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.event.CalendarEvent;
import seedu.address.model.event.UniqueCalendarEventList;

/**
 * Adds a calendar event to the address book.
 */
public class AddEventCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "eventadd";
    public static final String COMMAND_ALIAS = "ea";
    public static final String COMMAND_SYNTAX = COMMAND_WORD + " "
            + PREFIX_EVENT_TITLE + "EVENT_TITLE "
            + PREFIX_START_DATE + "[START_DATE] "
            + PREFIX_END_DATE + "END_DATE "
            + PREFIX_START_TIME + "[START_TIME] "
            + PREFIX_END_TIME + "END_TIME";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to the address book.\n"
            + "Parameters: "
            + PREFIX_EVENT_TITLE + "EVENT_TITLE "
            + "[" + PREFIX_START_DATE + "START_DATE] "
            + PREFIX_END_DATE + "END_DATE "
            + "[" + PREFIX_START_TIME + "START_TIME] "
            + PREFIX_END_TIME + "END_TIME\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_EVENT_TITLE + "Meeting with Boss "
            + PREFIX_START_DATE + "05-05-2018 "
            + PREFIX_END_DATE + "05-05-2018 "
            + PREFIX_START_TIME + "10:00 "
            + PREFIX_END_TIME + "12:30";

    public static final String MESSAGE_ADD_EVENT_SUCCESS = "Added Event [%1$s]";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in address book.";

    private final CalendarEvent calEventToAdd;

    /**
     * Creates an AddEventCommand to add specified {@code CalendarEvent}.
     */
    public AddEventCommand(CalendarEvent calEvent) {
        requireNonNull(calEvent);
        this.calEventToAdd = calEvent;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addCalendarEvent(calEventToAdd);
            EventsCenter.getInstance().post(new DisplayCalendarRequestEvent(model.getFilteredCalendarEventList()));
            return new CommandResult(String.format(MESSAGE_ADD_EVENT_SUCCESS, calEventToAdd));
        } catch (UniqueCalendarEventList.DuplicateCalendarEventException dcee) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddEventCommand // instanceof handles nulls
                && calEventToAdd.equals(((AddEventCommand) other).calEventToAdd));
    }
}
