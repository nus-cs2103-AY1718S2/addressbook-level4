package seedu.address.logic.commands;

import static junit.framework.TestCase.assertEquals;
import static seedu.address.logic.commands.OverdueCommand.SHOWN_OVERDUE_MESSAGE;
import static seedu.address.testutil.TypicalActivities.getTypicalDeskBoard;

import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.DeskBoard;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.activity.Activity;

//@@author Kyomian
public class OverdueCommandTest {

    private Model model = new ModelManager(getTypicalDeskBoard(), new UserPrefs());

    @Test
    public void execute_noOverdueTask() {
        String expectedMessage = String.format(SHOWN_OVERDUE_MESSAGE, 0);
        OverdueCommand command = prepareCommand();
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    /**
     * Parses {@code userInput} into a {@code OverdueCommand}.
     */
    private OverdueCommand prepareCommand() {
        OverdueCommand command = new OverdueCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Activity>} is equal to {@code expectedList}<br>
     *     - the {@code DeskBoard} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(OverdueCommand command, String expectedMessage, List<Activity> expectedList) {
        DeskBoard expectedDeskBoard = new DeskBoard(model.getDeskBoard());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredActivityList());
        assertEquals(expectedDeskBoard, model.getDeskBoard());
    }
}
