package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.parser.FindCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

//@@author tanhengyeow
/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void execute_noKeywords_invalidCommandFormat() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        FindCommand command;

        try {
            command = prepareCommand(" ");
            assertCommandSuccess(command, Collections.emptyList());
        } catch (ParseException pve) {
            if (!pve.getMessage().equals(expectedMessage)) {
                pve.printStackTrace();
            }
        }
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() throws ParseException {
        FindCommand command = prepareCommand("Developer, lydia@example.com");
        assertCommandSuccess(command, Arrays.asList(CARL, FIONA));
    }

    @Test
    public void execute_singlePrefixWithSingleKeyword_onePersonsFound() throws ParseException {
        FindCommand command = prepareCommand(" n/Kurz");
        assertCommandSuccess(command, Arrays.asList(CARL, FIONA)); // due to fuzzy search
    }

    @Test
    public void execute_singlePrefixWithMultipleKeywords_multiplePersonsFound() throws ParseException {
        FindCommand command = prepareCommand(" n/Kurz, Elle, Kunz");
        assertCommandSuccess(command, Arrays.asList(CARL, ELLE, FIONA));
    }

    @Test
    public void execute_multiplePrefixesWithSingleKeyword_onePersonsFound() throws ParseException {
        FindCommand command = prepareCommand(" n/\"arl Kurz\" p/95352563");
        assertCommandSuccess(command, Arrays.asList(CARL));
    }

    @Test
    public void execute_multiplePrefixesWithMultipleKeywords_zeroPersonsFound() throws ParseException {
        FindCommand command = prepareCommand(" n/Kurz, Elle, Kunz p/999, 555, "
                + "000 e/heinz@example.com a/wall street y/2019");
        assertCommandSuccess(command, Arrays.asList());
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private FindCommand prepareCommand(String userInput) throws ParseException {
        FindCommand command = parser.parse(userInput);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the {@code FilteredList<Person>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindCommand command, List<Person> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        command.execute();

        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
