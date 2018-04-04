package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for SwitchCommand.
 */
public class SwitchCommandTest {
    private Model model;
    private Model expectedModel;
    private SwitchCommand switchCommand;

    @Test
    public void execute_calendarToTimetable_success() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.switchView();

        switchCommand = new SwitchCommand();
        switchCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        assertCommandSuccess(switchCommand, model, SwitchCommand.MESSAGE_SUCCESS_TIMETABLE, expectedModel);
    }

    @Test
    public void execute_timetableToCalendar_success() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        model.switchView();
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        switchCommand = new SwitchCommand();
        switchCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        assertCommandSuccess(switchCommand, expectedModel, SwitchCommand.MESSAGE_SUCCESS_CALENDAR, model);
    }
}
