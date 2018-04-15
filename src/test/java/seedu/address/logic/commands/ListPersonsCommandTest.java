package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.AddressBookBuilder.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListPersonsCommand.
 */
public class ListPersonsCommandTest {

    private Model model;
    private Model expectedModel;
    private ListPersonsCommand listPersonsCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        listPersonsCommand = new ListPersonsCommand();
        listPersonsCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() throws PersonNotFoundException {
        assertCommandSuccess(listPersonsCommand, model, ListPersonsCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() throws PersonNotFoundException {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(listPersonsCommand, model, ListPersonsCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
