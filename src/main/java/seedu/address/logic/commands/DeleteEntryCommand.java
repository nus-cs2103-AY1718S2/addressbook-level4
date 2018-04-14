package seedu.address.logic.commands;
//@@author SuxianAlicia
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.DisplayCalendarEntryListEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.entry.CalendarEntry;
import seedu.address.model.entry.exceptions.CalendarEntryNotFoundException;

/**
 * Deletes a calendar entry identified using it's last displayed index from the address book.
 */
public class DeleteEntryCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "entrydelete";
    public static final String COMMAND_ALIAS = "ed";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the calendar entry identified by the index number used in the entry listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_ENTRY_SUCCESS = "Deleted Calendar Entry: %1$s";

    private final Index targetIndex;

    private CalendarEntry entryToDelete;

    public DeleteEntryCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(entryToDelete);
        try {
            model.deleteCalendarEntry(entryToDelete);
        } catch (CalendarEntryNotFoundException cenfe) {
            throw new AssertionError("The target calendar entry cannot be missing");
        }

        EventsCenter.getInstance().post(new DisplayCalendarEntryListEvent());
        return new CommandResult(String.format(MESSAGE_DELETE_ENTRY_SUCCESS, entryToDelete));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<CalendarEntry> lastShownList = model.getFilteredCalendarEntryList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);
        }

        entryToDelete = lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteEntryCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteEntryCommand) other).targetIndex) // state check
                && Objects.equals(this.entryToDelete, ((DeleteEntryCommand) other).entryToDelete));
    }
}
