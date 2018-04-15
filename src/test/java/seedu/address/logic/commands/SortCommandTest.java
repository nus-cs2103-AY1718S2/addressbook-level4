package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalInternshipsForSorting.IN1;
import static seedu.address.testutil.TypicalInternshipsForSorting.IN2;
import static seedu.address.testutil.TypicalInternshipsForSorting.IN3;
import static seedu.address.testutil.TypicalInternshipsForSorting.IN4;
import static seedu.address.testutil.TypicalInternshipsForSorting.IN5;
import static seedu.address.testutil.TypicalInternshipsForSorting.getTypicalInternshipForSorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.JobbiBot;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.internship.Internship;

//@@author niloc94
/**
 * Contains integration tests (interaction with the Model) for {@code SortCommand}.
 */
public class SortCommandTest {

    private Model model = new ModelManager(getTypicalInternshipForSorting(), new UserPrefs());

    @Test
    public void equals() {
        List<String> keywordsSet1 = new ArrayList<>(Arrays.asList("Name", "Role", "Industry"));
        List<String> keywordsSet2 = new ArrayList<>(Arrays.asList("Role", "Industry", "Region"));

        SortCommand firstSortCommand = new SortCommand(keywordsSet1);
        SortCommand secondSortCommand = new SortCommand(keywordsSet2);

        // same object -> returns true
        assertTrue(firstSortCommand.equals(firstSortCommand));

        // same values -> return true
        SortCommand firstSortCommandCopy = new SortCommand(keywordsSet1);
        assertTrue(firstSortCommand.equals(firstSortCommandCopy));

        // different types -> returns false
        assertFalse(firstSortCommand.equals(1));

        // null -> returns false
        assertFalse(firstSortCommand.equals(null));

        // different keywords -> returns false
        assertFalse(firstSortCommand.equals(secondSortCommand));
    }

    @Test
    public void sort() {
        // Salary keyword
        String expectedMessage = String.format(SortCommand.SORT_SUCCESSS_MESSAGE);
        SortCommand command = prepareCommand("salary");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(IN1, IN2, IN3, IN4, IN5));

        // One keyword, non-salary attribute
        expectedMessage = String.format(SortCommand.SORT_SUCCESSS_MESSAGE);
        command = prepareCommand("role");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(IN1, IN2, IN3, IN4, IN5));

        // One keyword with negative attribute
        expectedMessage = String.format(SortCommand.SORT_SUCCESSS_MESSAGE);
        command = prepareCommand("-salary");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(IN5, IN4, IN3, IN2, IN1));

        // Two keyword
        expectedMessage = String.format(SortCommand.SORT_SUCCESSS_MESSAGE);
        command = prepareCommand("role industry");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(IN1, IN2, IN5, IN4, IN3));

        // Two repeated keyword
        expectedMessage = String.format(SortCommand.SORT_SUCCESSS_MESSAGE);
        command = prepareCommand("role role");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(IN1, IN2, IN5, IN4, IN3));

        // Two keyword, negative of each other
        expectedMessage = String.format(SortCommand.SORT_SUCCESSS_MESSAGE);
        command = prepareCommand("role -role");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(IN1, IN2, IN5, IN4, IN3));

        // Two keywords, one negative
        expectedMessage = String.format(SortCommand.SORT_SUCCESSS_MESSAGE);
        command = prepareCommand("region -salary");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(IN1, IN2, IN3, IN5, IN4));

        // Two Negative
        expectedMessage = String.format(SortCommand.SORT_SUCCESSS_MESSAGE);
        command = prepareCommand("-region -salary");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(IN5, IN4, IN3, IN2, IN1));

        // Three keyword
        expectedMessage = String.format(SortCommand.SORT_SUCCESSS_MESSAGE);
        command = prepareCommand("role industry name");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(IN1, IN2, IN5, IN4, IN3));

        // Three keyword, one negative
        expectedMessage = String.format(SortCommand.SORT_SUCCESSS_MESSAGE);
        command = prepareCommand("role -industry name");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(IN1, IN2, IN3, IN5, IN4));

        // Three keyword, two negative
        expectedMessage = String.format(SortCommand.SORT_SUCCESSS_MESSAGE);
        command = prepareCommand("role -industry -name");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(IN1, IN2, IN3, IN4, IN5));

        // Three keyword, three negative
        expectedMessage = String.format(SortCommand.SORT_SUCCESSS_MESSAGE);
        command = prepareCommand("-role -industry -name");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(IN3, IN4, IN5, IN2, IN1));

        // More than three keywords
        expectedMessage = String.format(SortCommand.SORT_SUCCESSS_MESSAGE);
        command = prepareCommand("role industry name salary");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(IN1, IN2, IN5, IN4, IN3));

        // More than three keywords, all negative
        expectedMessage = String.format(SortCommand.SORT_SUCCESSS_MESSAGE);
        command = prepareCommand("-role -industry -name -salary");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(IN3, IN4, IN5, IN2, IN1));

        // More than three keywords, 1 negative
        expectedMessage = String.format(SortCommand.SORT_SUCCESSS_MESSAGE);
        command = prepareCommand("role industry name -salary");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(IN1, IN2, IN5, IN4, IN3));

        // More than three keywords, 2 negative
        expectedMessage = String.format(SortCommand.SORT_SUCCESSS_MESSAGE);
        command = prepareCommand("role -industry name salary");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(IN1, IN2, IN3, IN5, IN4));
    }

    /**
     * Parses {@code userInput} into a {@code SortCommand}.
     */
    private SortCommand prepareCommand(String arguments) {
        List<String> keywords = new ArrayList<>(Arrays.asList(arguments.split("\\s+")));
        SortCommand command = new SortCommand(keywords);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Person>} is equal to {@code expectedList}<br>
     *     - the {@code JobbiBot} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(SortCommand command, String expectedMessage, List<Internship> expectedList) {
        JobbiBot expectedJobbiBot = new JobbiBot(model.getJobbiBot());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredInternshipList());
        assertEquals(expectedJobbiBot, model.getJobbiBot());
    }
}
