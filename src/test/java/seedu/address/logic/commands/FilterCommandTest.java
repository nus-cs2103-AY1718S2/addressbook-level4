package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Filter;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.DatePredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FilterCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        DatePredicate firstPredicate =
                new DatePredicate(Collections.singletonList("2018-03-23"));
        DatePredicate secondPredicate =
                new DatePredicate(Collections.singletonList("2018-03-24"));

        FilterCommand filterFirstCommand = new FilterCommand(firstPredicate);
        FilterCommand filterSecondCommand = new FilterCommand(secondPredicate);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> returns true
        FilterCommand filterFirstCommandCopy = new FilterCommand(firstPredicate);
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

//    @Test
//    public void execute_zeroKeywords_noPersonFound() {
//        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
//        FilterCommand command = prepareCommand(" ");
//        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
//    }
//
//
//    /**
//     * Parses {@code userInput} into a {@code FindCommand}.
//     */
//    private FilterCommand prepareCommand(String userInput) {
//        FilterCommand command =
//                new FilterCommand(new DatePredicate(Arrays.asList(userInput.split("\\s+"))));
//        command.setData(model, new CommandHistory(), new UndoRedoStack());
//        return command;
//    }
//
//    /**
//     * Asserts that {@code command} is successfully executed, and<br>
//     *     - the command feedback is equal to {@code expectedMessage}<br>
//     *     - the {@code FilteredList<Person>} is equal to {@code expectedList}<br>
//     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
//     */
//    private void assertCommandSuccess(FilterCommand command, String expectedMessage, List<Person> expectedList) {
//        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
//        CommandResult commandResult;
//        try {
//            commandResult = command.execute();
//        } catch (CommandException e) {
//            e.getMessage();
//        }
//        assertEquals(expectedMessage, commandResult.feedbackToUser);
//        assertEquals(expectedList, model.getFilteredPersonList());
//        assertEquals(expectedAddressBook, model.getAddressBook());
//    }
}
