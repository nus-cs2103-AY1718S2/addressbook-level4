package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.JAMES;
import static seedu.address.testutil.TypicalPersons.KEN;
import static seedu.address.testutil.TypicalPersons.LENNY;
import static seedu.address.testutil.TypicalPersons.MISTER;
import static seedu.address.testutil.TypicalPersons.getMissingAttributesAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.predicates.FindMissingPredicate;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

//@@author aussiroth
/**
 * Contains integration tests (interaction with the Model) for {@code FindMissingCommand}.
 */
public class FindMissingCommandTest {
    private Model missingAttributesModel = new ModelManager(getMissingAttributesAddressBook(), new UserPrefs());
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equalsTest() {
        FindMissingPredicate firstPredicate =
                new FindMissingPredicate(Collections.singletonList("first"));
        FindMissingPredicate secondPredicate =
                new FindMissingPredicate(Collections.singletonList("second"));

        FindMissingCommand firstCommand = new FindMissingCommand(firstPredicate);
        FindMissingCommand secondCommand = new FindMissingCommand(secondPredicate);

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));
        // same values -> returns true
        FindMissingCommand firstCommandCopy = new FindMissingCommand(firstPredicate);
        assertTrue(firstCommand.equals(firstCommandCopy));
        // different types -> returns false
        assertFalse(firstCommand.equals("A String"));
        // null -> returns false
        assertFalse(firstCommand.equals(null));
        // different input list -> returns false
        assertFalse(firstCommand.equals(secondCommand));
    }

    @Test
    public void execute_noMissingFields_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        //special prepare command/assert command success using the typical persons model
        FindMissingCommand command = new FindMissingCommand(
                new FindMissingPredicate(Arrays.asList("phone")));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult result = command.execute();
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }

    @Test
    public void execute_oneField_onePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FindMissingCommand command = prepareCommand("phone");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(JAMES));
        command = prepareCommand("email");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(JAMES));
        command = prepareCommand("address");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(KEN));
        command = prepareCommand("subject");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(KEN));
        command = prepareCommand("level");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(LENNY));
        command = prepareCommand("price");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(LENNY));
        command = prepareCommand("status");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(MISTER));
        command = prepareCommand("role");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(MISTER));
    }

    @Test
    public void execute_multipleField_multiplePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        FindMissingCommand command = prepareCommand("email address");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(JAMES, KEN));
    }

    /**
     * Parses {@code userInput} into a {@code FindMissingCommand}.
     */
    private FindMissingCommand prepareCommand(String userInput) {
        FindMissingCommand command = new FindMissingCommand(
                new FindMissingPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(missingAttributesModel, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Person>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindMissingCommand command, String expectedMessage, List<Person> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(missingAttributesModel.getAddressBook());
        CommandResult result = command.execute();

        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedList, missingAttributesModel.getFilteredPersonList());
        assertEquals(expectedAddressBook, missingAttributesModel.getAddressBook());
    }
}
