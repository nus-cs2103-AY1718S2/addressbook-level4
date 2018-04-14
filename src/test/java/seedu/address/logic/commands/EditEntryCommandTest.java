package seedu.address.logic.commands;
//@@author SuxianAlicia
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_GET_STOCKS;
import static seedu.address.logic.commands.CommandTestUtil.DESC_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENTRY_TITLE_GET_STOCKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_GET_STOCKS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.testutil.TypicalCalendarEntries.getTypicalCalendarManagerWithEntries;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ENTRY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ENTRY;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.EditEntryCommand.EditEntryDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.entry.CalendarEntry;
import seedu.address.testutil.CalendarEntryBuilder;
import seedu.address.testutil.EditEntryDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for EditEntryCommand.
 */
public class EditEntryCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalCalendarManagerWithEntries() ,
            new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        CalendarEntry editedEntry = new CalendarEntryBuilder().withEntryTitle("Meet Client").build();
        EditEntryDescriptor descriptor = new EditEntryDescriptorBuilder(editedEntry).build();
        EditEntryCommand editEntryCommand = prepareCommand(INDEX_FIRST_ENTRY, descriptor);

        String expectedMessage = String.format(EditEntryCommand.MESSAGE_EDIT_ENTRY_SUCCESS, editedEntry);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), model.getCalendarManager(),
                new UserPrefs());
        expectedModel.updateCalendarEntry(model.getFilteredCalendarEntryList().get(0), editedEntry);

        assertCommandSuccess(editEntryCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() throws Exception {
        Index indexLastEntry = Index.fromOneBased(model.getFilteredCalendarEntryList().size());
        CalendarEntry lastEntry = model.getFilteredCalendarEntryList().get(indexLastEntry.getZeroBased());

        CalendarEntryBuilder entryInList = new CalendarEntryBuilder(lastEntry);
        CalendarEntry editedEntry = entryInList.withEntryTitle(VALID_ENTRY_TITLE_GET_STOCKS)
                .withStartTime(VALID_START_TIME_GET_STOCKS).build();

        EditEntryDescriptor descriptor = new EditEntryDescriptorBuilder()
                .withEntryTitle(VALID_ENTRY_TITLE_GET_STOCKS)
                .withStartTime(VALID_START_TIME_GET_STOCKS).build();
        EditEntryCommand editEntryCommand = prepareCommand(indexLastEntry, descriptor);

        String expectedMessage = String.format(EditEntryCommand.MESSAGE_EDIT_ENTRY_SUCCESS, editedEntry);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), model.getCalendarManager(),
                new UserPrefs());
        expectedModel.updateCalendarEntry(lastEntry, editedEntry);

        assertCommandSuccess(editEntryCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() throws Exception {
        EditEntryCommand editEntryCommand = prepareCommand(INDEX_FIRST_ENTRY, new EditEntryDescriptor());
        CalendarEntry editedEntry = model.getFilteredCalendarEntryList().get(INDEX_FIRST_ENTRY.getZeroBased());

        String expectedMessage = String.format(EditEntryCommand.MESSAGE_EDIT_ENTRY_SUCCESS, editedEntry);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), model.getCalendarManager(),
                new UserPrefs());
        expectedModel.updateCalendarEntry(model.getFilteredCalendarEntryList().get(0), editedEntry);

        assertCommandSuccess(editEntryCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateCalendarEntryUnfilteredList_failure() {
        CalendarEntry firstEntry = model.getFilteredCalendarEntryList().get(INDEX_FIRST_ENTRY.getZeroBased());
        EditEntryDescriptor descriptor = new EditEntryDescriptorBuilder(firstEntry).build();
        EditEntryCommand editEntryCommand = prepareCommand(INDEX_SECOND_ENTRY, descriptor);

        assertCommandFailure(editEntryCommand, model, EditEntryCommand.MESSAGE_DUPLICATE_ENTRY);
    }

    @Test
    public void execute_invalidCalendarEntryIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCalendarEntryList().size() + 1);
        EditEntryDescriptor descriptor = new EditEntryDescriptorBuilder()
                .withEntryTitle(VALID_ENTRY_TITLE_GET_STOCKS).build();
        EditEntryCommand editEntryCommand = prepareCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editEntryCommand, model, Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        CalendarEntry editedEntry = new CalendarEntryBuilder().build();
        CalendarEntry entryToEdit = model.getFilteredCalendarEntryList().get(INDEX_FIRST_ENTRY.getZeroBased());
        EditEntryDescriptor descriptor = new EditEntryDescriptorBuilder(editedEntry).build();
        EditEntryCommand editEntryCommand = prepareCommand(INDEX_FIRST_ENTRY, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), model.getCalendarManager(),
                new UserPrefs());

        // edit -> first calendar entry edited
        editEntryCommand.execute();
        undoRedoStack.push(editEntryCommand);

        // undo -> reverts address book back to previous state and filtered calendar entry list to show all entries
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first calendar entry edited again
        expectedModel.updateCalendarEntry(entryToEdit, editedEntry);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCalendarEntryList().size() + 1);
        EditEntryDescriptor descriptor = new EditEntryDescriptorBuilder()
                .withEntryTitle(VALID_ENTRY_TITLE_GET_STOCKS).build();
        EditEntryCommand editEntryCommand = prepareCommand(outOfBoundIndex, descriptor);

        // execution failed -> editEntryCommand not pushed into undoRedoStack
        assertCommandFailure(editEntryCommand, model, Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }


    @Test
    public void equals() throws Exception {
        final EditEntryCommand firstCommand = prepareCommand(INDEX_FIRST_ENTRY, DESC_MEET_BOSS);

        // same values -> returns true
        EditEntryDescriptor copyDescriptor = new EditEntryDescriptor(DESC_MEET_BOSS);
        EditEntryCommand firstCommandCopy = prepareCommand(INDEX_FIRST_ENTRY, copyDescriptor);
        assertTrue(firstCommand.equals(firstCommandCopy));

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // one command preprocessed when previously equal -> returns false
        firstCommandCopy.preprocessUndoableCommand();
        assertFalse(firstCommand.equals(firstCommandCopy));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different types -> returns false
        assertFalse(firstCommand.equals(1));

        // different index -> returns false
        assertFalse(firstCommand.equals(new EditEntryCommand(INDEX_SECOND_ENTRY, DESC_MEET_BOSS)));

        // different descriptor -> returns false
        assertFalse(firstCommand.equals(new EditEntryCommand(INDEX_FIRST_ENTRY, DESC_GET_STOCKS)));
    }

    /**
     * Returns an {@code EditEntryCommand} with parameters {@code index} and {@code descriptor}
     */
    private EditEntryCommand prepareCommand(Index index, EditEntryDescriptor descriptor) {
        EditEntryCommand editEntryCommand = new EditEntryCommand(index, descriptor);
        editEntryCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editEntryCommand;
    }
}
