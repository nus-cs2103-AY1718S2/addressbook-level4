package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalStudents.ELLE;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.Schedule;
import seedu.address.model.UserPrefs;
import seedu.address.model.student.NameContainsKeywordsPredicate;
import seedu.address.model.student.Student;

//@@author chweeee
/**
 * Contains integration tests (interaction with the Model) for {@code FindAndSelectCommand}.
 */

public class FindAndDeleteCommandTest {
    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new Schedule());
    }

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

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

        // different student -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noStudentFound() {
        String expectedMessage = "Student to be deleted cannot be found.";
        FindAndDeleteCommand command = prepareFadCommand(" ");
        try {
            assertCommandSuccess(command, expectedMessage, Collections.emptyList());
        } catch (CommandException e) {
            assertTrue(e.getMessage().equals(expectedMessage));
        }
    }

    @Test
    public void execute_studentDeleted() {
        String expectedMessage = "Deleted Student: " + ELLE.getName() + " Phone: " + ELLE.getPhone()
                + " Email: " + ELLE.getEmail() + " Address: "
                + ELLE.getAddress() + " Programming Language: " + ELLE.getProgrammingLanguage() + " Tags: [friends]"
                + " Favourite: " + ELLE.getFavourite() + " Profile Picture Path: "
                + ELLE.getProfilePicturePath() + " Miscellaneous Info: "
                + ELLE.getMiscellaneousInfo() + " Dashboard: " + ELLE.getDashboard();
        FindAndDeleteCommand command = prepareFadCommand("ELLE");
        try {
            assertCommandSuccess(command, expectedMessage, Collections.emptyList());
        } catch (CommandException e) {
            System.out.println(e.getMessage());
            assertTrue(e.getMessage().equals(expectedMessage));
        }
    }

    /**
     * Parses {@code userInput} into a {@code FindAndSelectCommand}.
     */
    private FindAndDeleteCommand prepareFadCommand(String userInput) {
        FindAndDeleteCommand command =
                new FindAndDeleteCommand(new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Student>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindAndDeleteCommand command, String expectedMessage,
                                      List<Student> expectedList) throws CommandException {
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredStudentList());
    }
}
