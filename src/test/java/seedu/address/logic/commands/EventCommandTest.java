package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.DeskBoard;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyDeskBoard;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Event;
import seedu.address.model.activity.exceptions.ActivityNotFoundException;
import seedu.address.model.activity.exceptions.DuplicateActivityException;
import seedu.address.testutil.EventBuilder;

//@@author Kyomian
public class EventCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new EventCommand(null);
    }

    @Test
    public void execute_eventAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingEventAdded modelStub = new ModelStubAcceptingEventAdded();
        Event validEvent = new EventBuilder().build();

        CommandResult commandResult = getEventCommandForGivenEvent(validEvent, modelStub).execute();

        assertEquals(String.format(EventCommand.MESSAGE_SUCCESS, validEvent), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validEvent), modelStub.eventsAdded);
    }

    @Test
    public void execute_duplicateEvent_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateActivityException();
        Event validEvent = new EventBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(EventCommand.MESSAGE_DUPLICATE_EVENT);

        getEventCommandForGivenEvent(validEvent, modelStub).execute();
    }

    @Test
    public void equals() {
        Event cip = new EventBuilder().build();
        Event cca = new EventBuilder().withName("CCA").build();
        EventCommand addCipCommand = new EventCommand(cip);
        EventCommand addCcaCommand = new EventCommand(cca);

        // same object -> returns true
        assertTrue(addCipCommand.equals(addCipCommand));

        // same values -> returns true
        EventCommand addAssignmentCommandCopy = new EventCommand(cip);
        assertTrue(addCipCommand.equals(addAssignmentCommandCopy));

        // different types -> returns false
        assertFalse(addCipCommand.equals(1));

        // null -> returns false
        assertFalse(addCipCommand.equals(null));

        // different activity -> returns false
        assertFalse(addCipCommand.equals(addCcaCommand));
    }

    /**
     * Generates a new EventCommand with the details of the given event.
     */
    private EventCommand getEventCommandForGivenEvent(Event event, Model model) {
        EventCommand command = new EventCommand(event);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addActivity(Activity activity) throws DuplicateActivityException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyDeskBoard newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyDeskBoard getDeskBoard() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deleteActivity(Activity target) throws ActivityNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updateActivity(Activity target, Activity editedActivity)
                throws DuplicateActivityException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Activity> getFilteredActivityList() {
            fail("This method should not be called.");
            return null;
        }

        //@@author jasmoon
        @Override
        public ObservableList<Activity> getFilteredTaskList()    {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ObservableList<Activity> getFilteredEventList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredActivityList(Predicate<Activity> predicate) {
            fail("This method should not be called.");
        }
    }

    /**
     * A Model stub that always throw a DuplicateActivityException when trying to add a activity.
     */
    private class ModelStubThrowingDuplicateActivityException extends ModelStub {
        @Override
        public void addActivity(Activity activity) throws DuplicateActivityException {
            throw new DuplicateActivityException();
        }

        @Override
        public ReadOnlyDeskBoard getDeskBoard() {
            return new DeskBoard();
        }
    }

    /**
     * A Model stub that always accept the activity being added.
     */
    private class ModelStubAcceptingEventAdded extends ModelStub {
        final ArrayList<Activity> eventsAdded = new ArrayList<>();

        @Override
        public void addActivity(Activity activity) throws DuplicateActivityException {
            requireNonNull(activity);
            eventsAdded.add(activity);
        }

        @Override
        public ReadOnlyDeskBoard getDeskBoard() {
            return new DeskBoard();
        }
    }
}
