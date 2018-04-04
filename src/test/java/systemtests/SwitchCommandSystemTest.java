package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import org.junit.Assert;
import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SwitchCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class SwitchCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void switchView() {
        final Model calendarModel = getModel();
        final Model timetableModel = getModel();
        timetableModel.switchView();

        /* Case: Application in Calendar view -> switched to Timetable view
         */
        assertCommandSuccess("   " + SwitchCommand.COMMAND_WORD + " view   ", calendarModel, timetableModel);

        assertCommandSuccess("   " + SwitchCommand.COMMAND_WORD + " view   ", timetableModel, calendarModel);
    }

    private void assertCommandSuccess(String command, Model model, Model expectedModel) {
        executeCommand(command);
        assertViewChanged(model, expectedModel);
    }

    private void assertCommandFailure(String command, Model model, Model expectedModel) {
        executeCommand(command);
        assertViewDidNotChange(model, expectedModel);
    }

    private void assertViewChanged(Model model, Model expectedModel) {
        Assert.assertNotEquals(model.calendarIsViewed(), expectedModel.calendarIsViewed());
    }

    private void assertViewDidNotChange(Model model, Model expectedModel) {
        Assert.assertEquals(model.calendarIsViewed(), expectedModel.calendarIsViewed());
    }
}
