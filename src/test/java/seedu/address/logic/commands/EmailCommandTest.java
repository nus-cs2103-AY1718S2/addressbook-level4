package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_SUBJECT;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToPersonListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Account;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.ui.testutil.EventsCollectorRule;

//@@author KevinCJH
public class EmailCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new Account());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());

        assertExecutionSuccess(INDEX_FIRST, VALID_EMAIL_SUBJECT);
        assertExecutionSuccess(INDEX_THIRD, VALID_EMAIL_SUBJECT);
        assertExecutionSuccess(lastPersonIndex, VALID_EMAIL_SUBJECT);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, VALID_EMAIL_SUBJECT, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST);

        assertExecutionSuccess(INDEX_FIRST, VALID_EMAIL_SUBJECT);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST);

        Index outOfBoundsIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        assertExecutionFailure(outOfBoundsIndex, VALID_EMAIL_SUBJECT, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Executes a {@code EmailCommand} with the given {@code index} and check that email address is shown correctly.
     */
    private void assertExecutionSuccess(Index index, String emailSubject) {
        EmailCommand emailCommand = prepareCommand(index, emailSubject);

        List<Person> lastShownList = model.getFilteredPersonList();
        Person personToEmail = lastShownList.get(index.getZeroBased());

        try {
            CommandResult commandResult = emailCommand.execute();
            assertEquals(String.format(EmailCommand.MESSAGE_EMAIL_PERSON_SUCCESS, personToEmail.getEmail()),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        JumpToPersonListRequestEvent lastEvent =
                (JumpToPersonListRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));

    }

    /**
     * Executes a {@code EmailCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String emailSubject, String expectedMessage) {
        EmailCommand emailCommand = prepareCommand(index, emailSubject);

        try {
            emailCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    @Test
    public void equals() {
        EmailCommand emailFirstCommand = new EmailCommand(INDEX_FIRST, VALID_EMAIL_SUBJECT);
        EmailCommand emailSecondCommand = new EmailCommand(INDEX_SECOND, VALID_EMAIL_SUBJECT);

        // same object -> returns true
        assertTrue(emailFirstCommand.equals(emailFirstCommand));

        // same values -> returns true
        EmailCommand emailFirstCommandCopy = new EmailCommand(INDEX_FIRST, VALID_EMAIL_SUBJECT);
        assertTrue(emailFirstCommand.equals(emailFirstCommandCopy));

        // different types -> returns false
        assertFalse(emailFirstCommand.equals(1));

        // null -> returns false
        assertFalse(emailFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(emailFirstCommand.equals(emailSecondCommand));
    }

    /**
     * Returns a {@code EmailCommand} with parameters {@code index}.
     */
    private EmailCommand prepareCommand(Index index, String emailSubject) {
        EmailCommand emailCommand = new EmailCommand(index, emailSubject);
        emailCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return emailCommand;
    }
}
