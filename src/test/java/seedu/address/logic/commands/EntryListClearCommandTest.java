package seedu.address.logic.commands;
//@@author SuxianAlicia

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCalendarEntries.getTypicalCalendarManagerWithEntries;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class EntryListClearCommandTest {

    @Test
    public void execute_emptyCalendarManager_success() {
        Model model = new ModelManager();
        assertCommandSuccess(prepareCommand(model), model, EntryListClearCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_nonEmptyCalendarManager_success() {
        Model model = new ModelManager(getTypicalAddressBook(), getTypicalCalendarManagerWithEntries(),
                new UserPrefs());
        assertCommandSuccess(prepareCommand(model), model, EntryListClearCommand.MESSAGE_SUCCESS, model);
    }

    /**
     * Generates a new {@code EntryListClearCommand} which upon execution,
     * clears the CalendarManager in {@code model}.
     */
    private EntryListClearCommand prepareCommand(Model model) {
        EntryListClearCommand command = new EntryListClearCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
