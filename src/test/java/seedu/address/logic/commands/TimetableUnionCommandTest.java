package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalOddEven.EVEN;
import static seedu.address.testutil.TypicalOddEven.ODD;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.TimeTableEvent;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.timetable.Timetable;
import seedu.address.ui.testutil.EventsCollectorRule;

//@@author AzuraAiR
/**
 * Contains integration tests (interaction with the Model) for {@code TimetableUnionCommand}.
 */
public class TimetableUnionCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);
        assertExecutionSuccess(indexes, ODD);

        indexes.add(lastPersonIndex);
        assertExecutionSuccess(indexes, ODD);
        assertExecutionSuccess(indexes, EVEN);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndexOne = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Index outOfBoundsIndexTwo = Index.fromOneBased(model.getFilteredPersonList().size() + 2);
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(outOfBoundsIndexOne);
        indexes.add(outOfBoundsIndexTwo);

        assertExecutionFailure(indexes, EVEN, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        Index outOfBoundsIndex = INDEX_SECOND_PERSON;
        indexes.add(outOfBoundsIndex);
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        assertExecutionFailure(indexes, EVEN, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);
        TimetableUnionCommand timetableFirstCommand = new TimetableUnionCommand(indexes, EVEN);
        TimetableUnionCommand timetableFirstCommandCopy = new TimetableUnionCommand(indexes, EVEN);
        TimetableUnionCommand timetableSecondCommand = new TimetableUnionCommand(indexes, ODD);
        indexes.add(INDEX_THIRD_PERSON);
        TimetableUnionCommand timetableThirdCommand = new TimetableUnionCommand(indexes, ODD);

        // same object -> returns true
        assertTrue(timetableFirstCommand.equals(timetableFirstCommand));

        // same values -> returns true
        assertTrue(timetableFirstCommand.equals(timetableFirstCommandCopy));

        // different types -> returns false
        assertFalse(timetableFirstCommand.equals(1));

        // null -> returns false
        assertFalse(timetableFirstCommand.equals(null));

        // different OddEven -> returns false
        assertFalse(timetableFirstCommand.equals(timetableSecondCommand));

        // different person -> returns false
        assertFalse(timetableFirstCommand.equals(timetableThirdCommand));

    }

    /**
     * Executes a {@code TimetableUnionCommand} with the given {@code indexes}  and {@code oddEven},
     * and checks that {@code JumpToListRequestEvent}
     * is raised with the correct indexes and oddEven.
     */
    private void assertExecutionSuccess(ArrayList<Index> indexes, String oddEven) {
        TimetableUnionCommand timetableUnionCommand = prepareCommand(indexes, oddEven);
        ArrayList<Person> targets = new ArrayList<Person>();
        ArrayList<Timetable> timetables = new ArrayList<Timetable>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indexes.size(); i++) {
            Person target = model.getFilteredPersonList().get(indexes.get(i).getZeroBased());
            targets.add(target);
            timetables.add(target.getTimetable());
            sb.append(target.getName());
            if (i != indexes.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("\n");

        int oddEvenIndex = StringUtil.getOddEven(oddEven).getZeroBased();
        ArrayList<ArrayList<ArrayList<String>>> targetList = Timetable.unionTimetable(timetables);
        try {
            CommandResult commandResult = timetableUnionCommand.execute();

            assertEquals(String.format(TimetableUnionCommand.MESSAGE_SELECT_PERSON_SUCCESS, oddEven, sb.toString()),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        TimeTableEvent lastEvent = (TimeTableEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(targetList.get(oddEvenIndex), lastEvent.getTimeTable());
    }

    /**
     * Executes a {@code TimetableUnionCommand} with the given {@code indexes} and {@code oddEven},
     * and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(ArrayList<Index> indexes, String oddEven, String expectedMessage) {
        TimetableUnionCommand timetableUnionCommand = prepareCommand(indexes, oddEven);

        try {
            timetableUnionCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code TimetableUnionCommand} with parameters {@code indexes} and {@code oddEven}.
     */
    private TimetableUnionCommand prepareCommand(ArrayList<Index> indexes, String oddEven) {
        TimetableUnionCommand timetableUnionCommand = new TimetableUnionCommand(indexes, oddEven);
        timetableUnionCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return timetableUnionCommand;
    }
}
