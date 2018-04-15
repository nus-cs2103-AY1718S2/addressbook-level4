//@@author deeheenguyen
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INTERVIEWS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalInterviews.SE_INTERVIEW;
import static seedu.address.testutil.TypicalInterviews.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.interview.Interview;
import seedu.address.model.interview.InterviewMatchInterviewee;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindInterviewCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        InterviewMatchInterviewee firstPredicate =
                new InterviewMatchInterviewee ("first");
        InterviewMatchInterviewee secondPredicate =
                new InterviewMatchInterviewee("second");

        FindInterviewCommand findFirstCommand = new FindInterviewCommand(firstPredicate);
        FindInterviewCommand findSecondCommand = new FindInterviewCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindInterviewCommand findFirstCommandCopy = new FindInterviewCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noInterviewFound() {
        String expectedMessage = String.format(MESSAGE_INTERVIEWS_LISTED_OVERVIEW, 0);
        FindInterviewCommand command = prepareCommand(" ");
        thrown.expect(IllegalArgumentException.class);
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_oneKeyword_interviewFound() {
        String expectedMessage = String.format(MESSAGE_INTERVIEWS_LISTED_OVERVIEW, 1);
        FindInterviewCommand command = prepareCommand("Kelvin");
        assertCommandSuccess(command, expectedMessage,
                Arrays.asList(SE_INTERVIEW));
    }

    /**
     * Parses {@code userInput} into a {@code FindInterviewCommand}.
     */
    private FindInterviewCommand prepareCommand(String userInput) {
        FindInterviewCommand command =
                new FindInterviewCommand(new InterviewMatchInterviewee(userInput));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Interview>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindInterviewCommand command, String expectedMessage,
                                      List<Interview> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredInterviewList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
