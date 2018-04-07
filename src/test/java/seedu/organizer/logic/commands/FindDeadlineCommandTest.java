package seedu.organizer.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.organizer.commons.core.Messages.MESSAGE_TASKS_LISTED_OVERVIEW;
import static seedu.organizer.testutil.TypicalTasks.ADMIN_USER;
import static seedu.organizer.testutil.TypicalTasks.PREPAREBREAKFAST;
import static seedu.organizer.testutil.TypicalTasks.PROJECT;
import static seedu.organizer.testutil.TypicalTasks.REVISION;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import seedu.organizer.logic.CommandHistory;
import seedu.organizer.logic.UndoRedoStack;
import seedu.organizer.logic.commands.exceptions.CommandException;
import seedu.organizer.model.task.predicates.DeadlineContainsKeywordsPredicate;
import seedu.organizer.model.user.exceptions.CurrentlyLoggedInException;
import seedu.organizer.model.user.exceptions.UserNotFoundException;
import seedu.organizer.model.user.exceptions.UserPasswordWrongException;

//@@author guekling
/**
 * Contains integration tests (interaction with the Model) for {@code FindDeadlineCommand}.
 */
public class FindDeadlineCommandTest extends FindCommandTest<FindDeadlineCommand> {

    @Before
    public void setUp() {
        try {
            model.loginUser(ADMIN_USER);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        } catch (CurrentlyLoggedInException e) {
            e.printStackTrace();
        } catch (UserPasswordWrongException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void equals() {
        DeadlineContainsKeywordsPredicate firstPredicate =
                new DeadlineContainsKeywordsPredicate(Collections.singletonList("2018-08-09"));
        DeadlineContainsKeywordsPredicate secondPredicate =
                new DeadlineContainsKeywordsPredicate(Collections.singletonList("2018-03-02"));

        FindDeadlineCommand findFirstCommand = new FindDeadlineCommand(firstPredicate);
        FindDeadlineCommand findSecondCommand = new FindDeadlineCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindDeadlineCommand findFirstCommandCopy = new FindDeadlineCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noTaskFound() throws CommandException {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 0);
        FindDeadlineCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multipleTasksFound() throws CommandException {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 3);
        FindDeadlineCommand command = prepareCommand("2019-04-05 2019-09-14 2019-11-12");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(REVISION, PROJECT, PREPAREBREAKFAST));
    }

    /**
     * Parses {@code userInput} into a {@code FindDeadlineCommand}.
     */
    private FindDeadlineCommand prepareCommand(String userInput) {
        FindDeadlineCommand command =
                new FindDeadlineCommand(new DeadlineContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
