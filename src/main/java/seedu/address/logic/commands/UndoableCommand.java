package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CALENDAR_ENTRIES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ORDERS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.CalendarManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyCalendarManager;

/**
 * Represents a command which can be undone and redone.
 */
public abstract class UndoableCommand extends Command {
    private ReadOnlyAddressBook previousAddressBook;
    private ReadOnlyCalendarManager previousCalendarManager;

    protected abstract CommandResult executeUndoableCommand() throws CommandException;

    /**
     * Stores the current state of {@code model#addressBook} and {@code model#calendarManager}.
     */
    private void saveAddressBookSnapshot() {
        requireNonNull(model);
        this.previousAddressBook = new AddressBook(model.getAddressBook());
        this.previousCalendarManager = new CalendarManager(model.getCalendarManager());
    }

    /**
     * This method is called before the execution of {@code UndoableCommand}.
     * {@code UndoableCommand}s that require this preprocessing step should override this method.
     */
    protected void preprocessUndoableCommand() throws CommandException {}

    /**
     * Reverts the AddressBook and CalendarManager to the state before this command
     * was executed and updates the filtered person list to
     * show all persons.
     */
    protected final void undo() {
        requireAllNonNull(model, previousAddressBook, previousCalendarManager);
        model.resetData(previousAddressBook, previousCalendarManager);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);
        model.updateFilteredCalendarEventList(PREDICATE_SHOW_ALL_CALENDAR_ENTRIES);
    }

    /**
     * Executes the command and updates the filtered person
     * list to show all persons.
     */
    protected final void redo() {
        requireNonNull(model);
        try {
            executeUndoableCommand();
        } catch (CommandException ce) {
            throw new AssertionError("The command has been successfully executed previously; "
                    + "it should not fail now");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);
        model.updateFilteredCalendarEventList(PREDICATE_SHOW_ALL_CALENDAR_ENTRIES);
    }

    @Override
    public final CommandResult execute() throws CommandException {
        saveAddressBookSnapshot();
        preprocessUndoableCommand();
        return executeUndoableCommand();
    }
}
