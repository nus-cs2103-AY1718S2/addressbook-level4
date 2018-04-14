package seedu.address.logic.commands;
//@@ author SuxianAlicia
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENTRY_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CALENDAR_ENTRIES;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ChangeCalendarDateRequestEvent;
import seedu.address.commons.events.ui.DisplayCalendarEntryListEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.EntryTimeConstraintsUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.entry.CalendarEntry;
import seedu.address.model.entry.EndDate;
import seedu.address.model.entry.EndTime;
import seedu.address.model.entry.EntryTitle;
import seedu.address.model.entry.StartDate;
import seedu.address.model.entry.StartTime;
import seedu.address.model.entry.exceptions.CalendarEntryNotFoundException;
import seedu.address.model.entry.exceptions.DuplicateCalendarEntryException;

/**
 * Edits the details of an existing entry in the calendar manager.
 */
public class EditEntryCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "entryedit";
    public static final String COMMAND_ALIAS = "ee";
    public static final String COMMAND_SYNTAX = COMMAND_WORD + " "
            + "INDEX "
            + "[" + PREFIX_ENTRY_TITLE + "ENTRY_TITLE] "
            + "[" + PREFIX_START_DATE + "START_DATE] "
            + "[" + PREFIX_END_DATE + "END_DATE] "
            + "[" + PREFIX_START_TIME + "START_TIME] "
            + "[" + PREFIX_END_TIME + "ENDTIME]";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the calendar entry identified "
            + "by the index number used in the last entry listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_ENTRY_TITLE + "ENTRY_TITLE] "
            + "[" + PREFIX_START_DATE + "START_DATE] "
            + "[" + PREFIX_END_DATE + "END_DATE] "
            + "[" + PREFIX_START_TIME + "START_TIME] "
            + "[" + PREFIX_END_TIME + "ENDTIME]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ENTRY_TITLE + "Meet client "
            + PREFIX_END_DATE + "01-01-2020";

    public static final String MESSAGE_EDIT_ENTRY_SUCCESS = "Edited Entry: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ENTRY = "This entry already exists in the calendar.";

    private final Index index;
    private final EditEntryDescriptor editEntryDescriptor;

    private CalendarEntry entryToEdit;
    private CalendarEntry editedEntry;

    /**
     * @param index of the entry in the filtered calendar entry list to edit
     * @param editEntryDescriptor details to edit the calendar entry with
     */
    public EditEntryCommand(Index index, EditEntryDescriptor editEntryDescriptor) {
        requireNonNull(index);
        requireNonNull(editEntryDescriptor);

        this.index = index;
        this.editEntryDescriptor = new EditEntryDescriptor(editEntryDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updateCalendarEntry(entryToEdit, editedEntry);
        } catch (DuplicateCalendarEntryException dcee) {
            throw new CommandException(MESSAGE_DUPLICATE_ENTRY);
        } catch (CalendarEntryNotFoundException cenfe) {
            throw new AssertionError("The target calendar entry cannot be missing.");
        }
        model.updateFilteredCalendarEventList(PREDICATE_SHOW_ALL_CALENDAR_ENTRIES);

        EventsCenter.getInstance().post(new ChangeCalendarDateRequestEvent(editedEntry.getDateToDisplay()));
        EventsCenter.getInstance().post(new DisplayCalendarEntryListEvent());

        return new CommandResult(String.format(MESSAGE_EDIT_ENTRY_SUCCESS, editedEntry));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<CalendarEntry> lastShownList = model.getFilteredCalendarEntryList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);
        }

        entryToEdit = lastShownList.get(index.getZeroBased());
        try {
            editedEntry = createEditedEntry(entryToEdit, editEntryDescriptor);
        } catch (IllegalValueException ive) {
            throw new CommandException(ive.getMessage());
        }
    }

    /**
     * Creates and returns a {@code CalendarEntry} with the details of {@code entryToEdit}
     * edited with {@code editEntryDescriptor}.
     */
    private static CalendarEntry createEditedEntry(CalendarEntry entryToEdit, EditEntryDescriptor editEntryDescriptor)
            throws IllegalValueException {
        assert entryToEdit != null;

        EntryTitle updatedEntryTitle = editEntryDescriptor.getEntryTitle().orElse(entryToEdit.getEntryTitle());
        StartDate updatedStartDate = editEntryDescriptor.getStartDate().orElse(entryToEdit.getStartDate());
        EndDate updatedEndDate = editEntryDescriptor.getEndDate().orElse(entryToEdit.getEndDate());
        StartTime updatedStartTime = editEntryDescriptor.getStartTime().orElse(entryToEdit.getStartTime());
        EndTime updatedEndTime = editEntryDescriptor.getEndTime().orElse(entryToEdit.getEndTime());

        EntryTimeConstraintsUtil.checkCalendarEntryTimeConstraints(updatedStartDate, updatedEndDate,
                updatedStartTime, updatedEndTime);

        return new CalendarEntry(updatedEntryTitle, updatedStartDate, updatedEndDate, updatedStartTime, updatedEndTime);
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditEntryCommand)) {
            return false;
        }

        // state check
        EditEntryCommand e = (EditEntryCommand) other;
        return index.equals(e.index)
                && editEntryDescriptor.equals(e.editEntryDescriptor)
                && Objects.equals(entryToEdit, e.entryToEdit);
    }

    /**
     * Stores the details to edit the calendar entry with. Each non-empty field value will replace the
     * corresponding field value of the calendar entry.
     */
    public static class EditEntryDescriptor {
        private EntryTitle entryTitle;
        private StartDate startDate;
        private EndDate endDate;
        private StartTime startTime;
        private EndTime endTime;

        public EditEntryDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditEntryDescriptor(EditEntryDescriptor toCopy) {
            setEntryTitle(toCopy.entryTitle);
            setStartDate(toCopy.startDate);
            setEndDate(toCopy.endDate);
            setStartTime(toCopy.startTime);
            setEndTime(toCopy.endTime);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.entryTitle, this.startDate, this.endDate, this.startTime,
                    this.endTime);
        }

        public void setEntryTitle(EntryTitle entryTitle) {
            this.entryTitle = entryTitle;
        }

        public Optional<EntryTitle> getEntryTitle() {
            return Optional.ofNullable(entryTitle);
        }

        public void setStartDate(StartDate startDate) {
            this.startDate = startDate;
        }

        public Optional<StartDate> getStartDate() {
            return Optional.ofNullable(startDate);
        }

        public void setEndDate(EndDate endDate) {
            this.endDate = endDate;
        }

        public Optional<EndDate> getEndDate() {
            return Optional.ofNullable(endDate);
        }

        public void setStartTime(StartTime startTime) {
            this.startTime = startTime;
        }

        public Optional<StartTime> getStartTime() {
            return Optional.ofNullable(startTime);
        }

        public void setEndTime(EndTime endTime) {
            this.endTime = endTime;
        }

        public Optional<EndTime> getEndTime() {
            return Optional.ofNullable(endTime);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditEntryDescriptor)) {
                return false;
            }

            // state check
            EditEntryDescriptor e = (EditEntryDescriptor) other;

            return getEntryTitle().equals(e.getEntryTitle())
                    && getStartDate().equals(e.getStartDate())
                    && getEndDate().equals(e.getEndDate())
                    && getStartTime().equals(e.getStartTime())
                    && getEndTime().equals(e.getEndTime());
        }
    }
}
