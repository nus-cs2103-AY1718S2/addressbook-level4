package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalInternships.BUSINESS3;
import static seedu.address.testutil.TypicalInternships.DATASCIENCE;
import static seedu.address.testutil.TypicalInternships.getTypicalInternshipBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.JobbiBot;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.internship.Internship;
import seedu.address.model.internship.InternshipContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {

    private Model model = new ModelManager(getTypicalInternshipBook(), new UserPrefs());

    @Test
    public void equals() {
        InternshipContainsKeywordsPredicate firstPredicate =
                new InternshipContainsKeywordsPredicate(Collections.singletonList("first"));
        InternshipContainsKeywordsPredicate secondPredicate =
                new InternshipContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different internship -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noInternshipFound() {
        String expectedMessage = String.format(FindCommand.MESSAGE_SEARCH_RESPONSE_NO_INTERNSHIPS, 0);
        FindCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_singleKeywords_singleInternshipFound() {
        String expectedMessage = String.format(FindCommand.MESSAGE_SEARCH_RESPONSE, 1);
        FindCommand command = prepareCommand("Data");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(DATASCIENCE));
    }

    @Test
    public void execute_multipleKeywords_multipleInternshipsFound() {
        String expectedMessage = String.format(FindCommand.MESSAGE_SEARCH_RESPONSE, 2);
        FindCommand command = prepareCommand("Data Consulting");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(DATASCIENCE, BUSINESS3));
    }

    @Test
    public void execute_multipleKeywordsWithDuplicate_singleInternshipFound() {
        String expectedMessage = String.format(FindCommand.MESSAGE_SEARCH_RESPONSE, 1);
        FindCommand command = prepareCommand("Data Data");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(DATASCIENCE));
    }

    @Test
    public void execute_multipleKeywordsWithDuplicate_multipleInternshipsFound() {
        String expectedMessage = String.format(FindCommand.MESSAGE_SEARCH_RESPONSE, 2);
        FindCommand command = prepareCommand("Data Data Consulting Consulting");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(DATASCIENCE, BUSINESS3));
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private FindCommand prepareCommand(String userInput) {
        FindCommand command =
                new FindCommand(new InternshipContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Internship>} is equal to {@code expectedList}<br>
     *     - the {@code JobbiBot} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindCommand command, String expectedMessage, List<Internship> expectedList) {
        JobbiBot expectedJobbiBot = new JobbiBot(model.getJobbiBot());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredInternshipList());
        assertEquals(expectedJobbiBot, model.getJobbiBot());
    }
}
