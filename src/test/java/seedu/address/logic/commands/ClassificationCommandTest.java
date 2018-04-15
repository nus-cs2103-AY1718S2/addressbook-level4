package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_KEYWORD;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.ClassificationCommand.MESSAGE_USAGE;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
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
import seedu.address.model.person.NameContainsClassificationPredicate;
import seedu.address.model.person.Person;

//@@author wenhao53
/**
 * Contains integration tests (interaction with the Model) for {@code ClassificationCommand}.
 */
public class ClassificationCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsClassificationPredicate firstPredicate =
                new NameContainsClassificationPredicate(Collections.singletonList("first"));
        NameContainsClassificationPredicate secondPredicate =
                new NameContainsClassificationPredicate(Collections.singletonList("second"));

        ClassificationCommand classificationFirstCommand = new ClassificationCommand(firstPredicate);
        ClassificationCommand classificationSecondCommand = new ClassificationCommand(secondPredicate);

        // same object -> returns true
        assertTrue(classificationFirstCommand.equals(classificationFirstCommand));

        // same values -> returns true
        ClassificationCommand classificationFirstCommandCopy = new ClassificationCommand(firstPredicate);
        assertTrue(classificationFirstCommand.equals(classificationFirstCommandCopy));

        // different types -> returns false
        assertFalse(classificationFirstCommand.equals(1));

        // null -> returns false
        assertFalse(classificationFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(classificationFirstCommand.equals(classificationSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE);
        ClassificationCommand command = prepareCommand(" ");
        assertCommandFailure(command, expectedMessage);
    }

    @Test
    public void execute_incorrectKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_INVALID_KEYWORD, MESSAGE_USAGE);
        ClassificationCommand command = prepareCommand("notValid");
        assertCommandFailure(command, expectedMessage);
    }

    @Test
    public void execute_acceptableClassification_personFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        ClassificationCommand command = prepareCommand("acceptable");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, DANIEL, ELLE));
    }

    @Test
    public void execute_obeseClassification_personFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        ClassificationCommand command = prepareCommand("obese");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON));
    }

    @Test
    public void execute_overweightClassification_personFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        ClassificationCommand command = prepareCommand("overweight");
        assertCommandSuccess(command, expectedMessage, Collections.singletonList(GEORGE));
    }

    @Test
    public void execute_underweightClassification_personFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        ClassificationCommand command = prepareCommand("underweight");
        assertCommandSuccess(command, expectedMessage, Collections.singletonList(FIONA));
    }

    @Test
    public void execute_overweightAndUnderweightClassification_personFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        ClassificationCommand command = prepareCommand("underweight overweight");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(FIONA, GEORGE));
    }

    @Test
    public void execute_underweightAndAcceptableClassification_personFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 4);
        ClassificationCommand command = prepareCommand("underweight acceptable");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, DANIEL, ELLE, FIONA));
    }

    @Test
    public void execute_underweightAndObeseClassification_personFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        ClassificationCommand command = prepareCommand("underweight obese");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON, FIONA));
    }

    @Test
    public void execute_acceptableAndOverweightClassification_personFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 4);
        ClassificationCommand command = prepareCommand("overweight acceptable");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, DANIEL, ELLE, GEORGE));
    }

    @Test
    public void execute_acceptableAndObeseClassification_personFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 5);
        ClassificationCommand command = prepareCommand("obese acceptable");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE));
    }

    @Test
    public void execute_overweightAndObeseClassification_personFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        ClassificationCommand command = prepareCommand("obese overweight");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON, GEORGE));
    }

    @Test
    public void execute_allClassification_personFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 7);
        ClassificationCommand command = prepareCommand("underweight overweight obese acceptable");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    /**
     * Parses {@code userInput} into a {@code ClassificationCommand}.
     */
    private ClassificationCommand prepareCommand(String userInput) {
        ClassificationCommand command = new ClassificationCommand(
                new NameContainsClassificationPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Person>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(ClassificationCommand command,
                                      String expectedMessage, List<Person> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }

    /**
     * Asserts that {@code command} is not successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - there is no {@code FilteredList<Person>} displayed<br>
     *     - the model remains the same after executing the {@code command}
     */
    private void assertCommandFailure(ClassificationCommand command, String expectedMessage) {
        CommandResult commandResult = command.execute();
        assertNotEquals(expectedMessage, commandResult.feedbackToUser);
    }
}
