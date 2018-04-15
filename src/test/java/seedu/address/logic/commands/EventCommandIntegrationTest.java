package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalActivities.getTypicalDeskBoard;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Event;
import seedu.address.testutil.EventBuilder;

//@@author Kyomian
/**
 * Contains integration tests (interaction with the Model) for {@code EventCommand}.
 */
public class EventCommandIntegrationTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalDeskBoard(), new UserPrefs());
    }

    @Test
    public void execute_newEvent_success() throws Exception {
        Event validEvent = new EventBuilder().build();

        Model expectedModel = new ModelManager(model.getDeskBoard(), new UserPrefs());
        expectedModel.addActivity(validEvent);

        assertCommandSuccess(prepareCommand(validEvent, model), model,
                String.format(EventCommand.MESSAGE_SUCCESS, validEvent), expectedModel);
    }

    @Test
    // Questionable - does the app check for duplicate?
    public void execute_duplicateEvent_throwsCommandException() {
        Activity activityInList = model.getDeskBoard().getActivityList().get(0);
        assertCommandFailure(prepareCommand((Event) activityInList, model), model,
                EventCommand.MESSAGE_DUPLICATE_EVENT);
    }

    /**
     * Generates a new {@code EventCommand} which upon execution, adds {@code event} into the {@code model}.
     */
    private EventCommand prepareCommand(Event event, Model model) {
        EventCommand command = new EventCommand(event);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
