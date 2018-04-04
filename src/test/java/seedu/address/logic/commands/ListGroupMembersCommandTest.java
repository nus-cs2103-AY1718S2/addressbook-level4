package seedu.address.logic.commands;

//@@author jas5469

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.TagContainKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code ListGroupMembersCommand}.
 */
public class ListGroupMembersCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        TagContainKeywordsPredicate firstPredicate =
                new TagContainKeywordsPredicate(Collections.singletonList("first"));
        TagContainKeywordsPredicate secondPredicate =
                new TagContainKeywordsPredicate(Collections.singletonList("second"));

        ListGroupMembersCommand findFirstCommand = new ListGroupMembersCommand(firstPredicate);
        ListGroupMembersCommand findSecondCommand = new ListGroupMembersCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        ListGroupMembersCommand findFirstCommandCopy = new ListGroupMembersCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        ListGroupMembersCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        ListGroupMembersCommand command = prepareCommand("CS3230 owesMoney");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON));
    }

    /**
     * Parses {@code userInput} into a {@code ListGroupMembersCommand}.
     */
    private ListGroupMembersCommand prepareCommand(String userInput) {
        ListGroupMembersCommand command =
                new ListGroupMembersCommand(new TagContainKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Person>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(ListGroupMembersCommand command, String expectedMessage,
                                      List<Person> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
