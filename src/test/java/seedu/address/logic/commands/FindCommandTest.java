package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalEvents.getTypicalEventBook;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.HARRY;
import static seedu.address.testutil.TypicalPersons.IAN;
import static seedu.address.testutil.TypicalPersons.KEITH;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskBook;

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
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.tag.TagContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model =
            new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), getTypicalTaskBook(), new UserPrefs());
    //@@author jill858
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

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }
    //@@author jill858
    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FindCommand command = prepareNameCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }
    //@@author jill858
    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        // multiple name keywords
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 4);
        FindCommand command = prepareNameCommand("Kurz Elle Kunz");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA, IAN));

        // multiple partial name keywords
        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 5);
        command = prepareNameCommand("Ku El");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, DANIEL, ELLE, FIONA, IAN));

        // multiple mixed-case name keywords
        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 4);
        command = prepareNameCommand("KuRz ElLe KuNz");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA, IAN));

        // multiple mixed-case partial name keywords
        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        command = prepareNameCommand("KuN ElL");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ELLE, FIONA));

        // multiple tag keywords
        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        command = prepareTagCommand("colleagues classmates");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(HARRY, IAN, KEITH));

        // multiple partial tag keywords
        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 4);
        command = prepareTagCommand("c owesM");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON, HARRY, IAN, KEITH));

        // multiple mixed-case tag keywords
        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        command = prepareTagCommand("colLeagUes clasSMates");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(HARRY, IAN, KEITH));

        // multiple mixed-case partial tag keywords
        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        command = prepareTagCommand("colL owESM");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON, HARRY, IAN));
    }
    //@@author jill858
    @Test
    public void execute_singleKeywords_multiplePersonsFound() {
        // name keywords
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        FindCommand command = prepareNameCommand("Kurz");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, IAN));

        //  partial name keywords
        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        command = prepareNameCommand("Ku");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, FIONA, IAN));

        // mixed-case name keywords
        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        command = prepareNameCommand("KuRz");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, IAN));

        //  mixed-case partial name keywords
        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        command = prepareNameCommand("kU");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, FIONA, IAN));

        // tag keywords
        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 7);
        command = prepareTagCommand("friends");
        assertCommandSuccess(command, expectedMessage,
                Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));

        //  partial tag keywords
        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        command = prepareTagCommand("class");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(IAN, KEITH));

        //  mixed-case tag keywords
        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        command = prepareTagCommand("oweSMoney");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON, IAN));

        //  mixed-case partial tag keywords
        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        command = prepareTagCommand("owesM");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON, IAN));
    }
    //@@author jill858
    /**
     * Parses {@code userInput} into a {@code FindCommand} for a name search.
     */
    private FindCommand prepareNameCommand(String userInput) {
        FindCommand command =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
    //@@author jill858
    /**
     * Parses {@code userInput} into a {@code FindCommand} for a tag search.
     */
    private FindCommand prepareTagCommand(String userInput) {
        FindCommand command =
                new FindCommand(new TagContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Person>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindCommand command, String expectedMessage, List<Person> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
