package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.StreamCommand.MESSAGE_L1B4A_SUCCESS;
import static seedu.address.logic.commands.StreamCommand.MESSAGE_L1B4B_SUCCESS;
import static seedu.address.logic.commands.StreamCommand.MESSAGE_L1B4C_SUCCESS;
import static seedu.address.logic.commands.StreamCommand.MESSAGE_L1B4D_SUCCESS;
import static seedu.address.logic.commands.StreamCommand.MESSAGE_L1R5_SUCCESS;
import static seedu.address.logic.commands.StreamCommand.MESSAGE_SELECT_STUDENT_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.ui.testutil.EventsCollectorRule;

//@@author TeyXinHui
/**
 * Contains integration tests (interaction with the Model) for {@code StreamCommand}.
 */
public class StreamCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_indexOutOfBounds_throwCommandException() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        int type = 1;
        assertExecutionFailure(outOfBoundsIndex, type, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validInput_success() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());
        assertExecutionSuccess(INDEX_FIRST_PERSON, 1);
        assertExecutionSuccess(INDEX_THIRD_PERSON, 2);
        assertExecutionSuccess(INDEX_FIRST_PERSON, 3);
        assertExecutionSuccess(INDEX_FIRST_PERSON, 4);
        assertExecutionSuccess(lastPersonIndex, 5);
    }

    @Test
    public void equals() {
        StreamCommand streamFirstCommand = new StreamCommand(INDEX_FIRST_PERSON, 1);
        StreamCommand streamSecondCommand = new StreamCommand(INDEX_SECOND_PERSON, 1);


        // same object -> returns true
        assertTrue(streamFirstCommand.equals(streamFirstCommand));

        // same values -> returns true
        StreamCommand streamFirstCommandCopy = new StreamCommand(INDEX_FIRST_PERSON, 1);
        assertTrue(streamFirstCommand.equals(streamFirstCommandCopy));

        // different types -> returns false
        assertFalse(streamFirstCommand.equals(1));

        // null -> returns false
        assertFalse(streamFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(streamFirstCommand.equals(streamSecondCommand));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess (Index index, int type) {
        StreamCommand streamCommand = prepareCommand(index, type);
        List<Person> lastShownList = model.getFilteredPersonList();
        Person selectedPerson = lastShownList.get(index.getZeroBased());
        StringBuilder result = new StringBuilder();
        String message = "";
        int score = 5;
        switch(type) {
        case(1):
            message = MESSAGE_L1R5_SUCCESS;
            score = 6;
            break;
        case(2):
            message = MESSAGE_L1B4A_SUCCESS;
            break;
        case(3):
            message = MESSAGE_L1B4B_SUCCESS;
            break;
        case(4):
            message = MESSAGE_L1B4C_SUCCESS;
            break;
        case(5):
            message = MESSAGE_L1B4D_SUCCESS;
            break;
        default:
            break;
        }

        try {
            CommandResult commandResult = streamCommand.execute();
            assertEquals(result.append(String.format(MESSAGE_SELECT_STUDENT_SUCCESS, selectedPerson.getName()))
                    .append(String.format(message, score)).toString(),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        JumpToListRequestEvent lastEvent = (JumpToListRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
    }
    /**
     *
     * Executes a {@code StreamCommand} with the given {@code index} and {@code type},
     * and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, int type, String expectedMessage) {
        StreamCommand streamCommand = prepareCommand(index, type);
        try {
            streamCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code SelectCommand} with parameters {@code index}.
     */
    private StreamCommand prepareCommand(Index index, int type) {
        StreamCommand streamCommand = new StreamCommand(index, type);
        streamCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return streamCommand;
    }
}
//@@author
