package seedu.address.logic.commands;
//@@author SuxianAlicia
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENTRY_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeCalendarDateRequestEvent;
import seedu.address.commons.events.ui.DisplayCalendarEntryListEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.entry.CalendarEntry;
import seedu.address.model.entry.exceptions.DuplicateCalendarEntryException;

/**
 * Adds a calendar entry to calendar manager.
 */
public class AddEntryCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "entryadd";
    public static final String COMMAND_ALIAS = "ea";
    public static final String COMMAND_SYNTAX = COMMAND_WORD + " "
            + PREFIX_ENTRY_TITLE + "ENTRY_TITLE "
            + PREFIX_START_DATE + "[START_DATE] "
            + PREFIX_END_DATE + "END_DATE "
            + PREFIX_START_TIME + "[START_TIME] "
            + PREFIX_END_TIME + "END_TIME";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a calendar entry to the calendar"
            + " and displays the calendar with the entry's start date.\n"
            + "Parameters: "
            + PREFIX_ENTRY_TITLE + "ENTRY_TITLE "
            + "[" + PREFIX_START_DATE + "START_DATE] "
            + PREFIX_END_DATE + "END_DATE "
            + "[" + PREFIX_START_TIME + "START_TIME] "
            + PREFIX_END_TIME + "END_TIME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ENTRY_TITLE + "Meeting with Boss "
            + PREFIX_START_DATE + "05-05-2018 "
            + PREFIX_END_DATE + "05-05-2018 "
            + PREFIX_START_TIME + "10:00 "
            + PREFIX_END_TIME + "12:30";

    public static final String MESSAGE_ADD_ENTRY_SUCCESS = "Added Entry [%1$s]";
    public static final String MESSAGE_DUPLICATE_ENTRY = "This entry already exists in the calendar.";

    private final CalendarEntry calEntryToAdd;

    /**
     * Creates an AddEntryCommand to add specified {@code CalendarEntry}.
     */
    public AddEntryCommand(CalendarEntry calEntry) {
        requireNonNull(calEntry);
        this.calEntryToAdd = calEntry;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addCalendarEntry(calEntryToAdd);
            EventsCenter.getInstance().post(new ChangeCalendarDateRequestEvent(calEntryToAdd.getDateToDisplay()));
            EventsCenter.getInstance().post(new DisplayCalendarEntryListEvent());
            return new CommandResult(String.format(MESSAGE_ADD_ENTRY_SUCCESS, calEntryToAdd));
        } catch (DuplicateCalendarEntryException dcee) {
            throw new CommandException(MESSAGE_DUPLICATE_ENTRY);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddEntryCommand // instanceof handles nulls
                && calEntryToAdd.equals(((AddEntryCommand) other).calEntryToAdd));
    }
}
