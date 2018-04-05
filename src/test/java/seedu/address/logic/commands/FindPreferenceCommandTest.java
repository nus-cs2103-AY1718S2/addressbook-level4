package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.GEORGE;
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
import seedu.address.model.person.Person;
import seedu.address.model.person.PreferencesContainKeywordsPredicate;

//@@author SuxianAlicia
public class FindPreferenceCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new CalendarManager(), new UserPrefs());

    @Test
    public void equals() {
        PreferencesContainKeywordsPredicate firstPredicate =
                new PreferencesContainKeywordsPredicate(Collections.singletonList("first"));
        PreferencesContainKeywordsPredicate secondPredicate =
                new PreferencesContainKeywordsPredicate(Collections.singletonList("second"));

        FindPreferenceCommand findPreferenceFirstCommand = new FindPreferenceCommand(firstPredicate);
        FindPreferenceCommand findPreferenceSecondCommand = new FindPreferenceCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findPreferenceFirstCommand.equals(findPreferenceFirstCommand));

        // same values -> returns true
        FindPreferenceCommand findPreferenceFirstCommandCopy = new FindPreferenceCommand(firstPredicate);
        assertTrue(findPreferenceFirstCommand.equals(findPreferenceFirstCommandCopy));

        // different types -> returns false
        assertFalse(findPreferenceFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findPreferenceFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findPreferenceFirstCommand.equals(findPreferenceSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FindPreferenceCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindPreferenceCommand command = prepareCommand("videoGames shoes");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON, GEORGE));
    }

    /**
     * Parses {@code userInput} into a {@code FindPreferenceCommand}.
     */
    private FindPreferenceCommand prepareCommand(String userInput) {
        FindPreferenceCommand command =
                new FindPreferenceCommand(new PreferencesContainKeywordsPredicate(Arrays.asList(userInput
                        .split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Person>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindPreferenceCommand command, String expectedMessage,
                                      List<Person> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
