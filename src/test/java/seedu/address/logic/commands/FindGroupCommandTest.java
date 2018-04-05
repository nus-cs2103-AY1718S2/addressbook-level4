package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.CalendarManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.GroupsContainKeywordsPredicate;
import seedu.address.model.person.Person;

//@@author SuxianAlicia
public class FindGroupCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new CalendarManager(), new UserPrefs());

    @Test
    public void equals() {
        GroupsContainKeywordsPredicate firstPredicate =
                new GroupsContainKeywordsPredicate(Collections.singletonList("first"));
        GroupsContainKeywordsPredicate secondPredicate =
                new GroupsContainKeywordsPredicate(Collections.singletonList("second"));

        FindGroupCommand findGroupFirstCommand = new FindGroupCommand(firstPredicate);
        FindGroupCommand findGroupSecondCommand = new FindGroupCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findGroupFirstCommand.equals(findGroupFirstCommand));

        // same values -> returns true
        FindGroupCommand findGroupFirstCommandCopy = new FindGroupCommand(firstPredicate);
        assertTrue(findGroupFirstCommand.equals(findGroupFirstCommandCopy));

        // different types -> returns false
        assertFalse(findGroupFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findGroupFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findGroupFirstCommand.equals(findGroupSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FindGroupCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        FindGroupCommand command = prepareCommand("Neighbours twitter");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON, ELLE));
    }

    /**
     * Parses {@code userInput} into a {@code FindGroupCommand}.
     */
    private FindGroupCommand prepareCommand(String userInput) {
        FindGroupCommand command =
                new FindGroupCommand(new GroupsContainKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Person>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindGroupCommand command, String expectedMessage, List<Person> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
