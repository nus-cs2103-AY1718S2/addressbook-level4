package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_JOBS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalJobs.DEVOPS_ENGINEER;
import static seedu.address.testutil.TypicalJobs.MARKETING_INTERN;
import static seedu.address.testutil.TypicalJobs.PRODUCT_MANAGER;
import static seedu.address.testutil.TypicalJobs.SOFTWARE_ENGINEER;
import static seedu.address.testutil.TypicalJobs.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.job.JobFindCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.job.Job;
import seedu.address.model.job.PositionContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code JobFindCommand}.
 */
public class JobFindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        PositionContainsKeywordsPredicate firstPredicate =
                new PositionContainsKeywordsPredicate(Collections.singletonList("first"));
        PositionContainsKeywordsPredicate secondPredicate =
                new PositionContainsKeywordsPredicate(Collections.singletonList("second"));

        JobFindCommand findFirstCommand = new JobFindCommand(firstPredicate);
        JobFindCommand findSecondCommand = new JobFindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        JobFindCommand findFirstCommandCopy = new JobFindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different job -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noJobFound() {
        String expectedMessage = String.format(MESSAGE_JOBS_LISTED_OVERVIEW, 0);
        JobFindCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multipleJobsFound() {
        String expectedMessage = String.format(MESSAGE_JOBS_LISTED_OVERVIEW, 4);
        JobFindCommand command = prepareCommand("Engineer Intern Manager");
        assertCommandSuccess(command, expectedMessage,
                Arrays.asList(SOFTWARE_ENGINEER, MARKETING_INTERN, DEVOPS_ENGINEER, PRODUCT_MANAGER));
    }

    /**
     * Parses {@code userInput} into a {@code JobFindCommand}.
     */
    private JobFindCommand prepareCommand(String userInput) {
        JobFindCommand command =
                new JobFindCommand(new PositionContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Job>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(JobFindCommand command, String expectedMessage, List<Job> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredJobList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
