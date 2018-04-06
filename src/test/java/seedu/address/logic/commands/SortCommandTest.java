package seedu.address.logic.commands;

//@@author lohtianwei
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.SortCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.TypicalPersons.getEmptyAddressBook;
import static seedu.address.testutil.TypicalPersons.getSortedAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class SortCommandTest {

    @Rule
    public ExpectedException error = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model emptyModel = new ModelManager(getEmptyAddressBook(), new UserPrefs());
    private Model sortedByNameDesc = new ModelManager(
            getSortedAddressBook("name", "desc"), new UserPrefs());

    private Model sortedByAdd = new ModelManager(
            getSortedAddressBook("address", "asc"), new UserPrefs());
    private Model sortedByAddDesc = new ModelManager(
            getSortedAddressBook("address", "desc"), new UserPrefs());

    private Model sortedByEmail = new ModelManager(getSortedAddressBook("email", "asc"), new UserPrefs());
    private Model sortedByEmailDesc = new ModelManager(
            getSortedAddressBook("email", "desc"), new UserPrefs());

    @Test
    public void noPlayers() throws CommandException {
        error.expect(CommandException.class);
        prepareCommand("name", "asc", emptyModel).execute();
    }

    @Test
    public void emptySortField_throwsNullPointerEx() {
        error.expect(NullPointerException.class);
        new SortCommand("name", null);
    }

    @Test
    public void emptySortOrder_throwsNullPointerEx() {
        error.expect(NullPointerException.class);
        new SortCommand(null, "asc");
    }

    @Test
    public void sortByName_success() throws Exception {
        SortCommand so = prepareCommand("name", "asc", model);
        String expected = String.format(MESSAGE_SUCCESS, "name", "asc");
        assertCommandSuccess(so, model, expected, model);
    }

    @Test
    public void sortByNameDesc_success() throws Exception {
        SortCommand so = prepareCommand("name", "desc", model);
        String expected = String.format(MESSAGE_SUCCESS, "name", "desc");
        assertCommandSuccess(so, model, expected, sortedByNameDesc);
    }

    @Test
    public void sortByAdd_success() throws Exception {
        SortCommand so = prepareCommand("address", "asc", model);
        String expected = String.format(MESSAGE_SUCCESS, "address", "asc");
        assertCommandSuccess(so, model, expected, sortedByAdd);
    }

    @Test
    public void sortByAddDesc_success() throws Exception {
        SortCommand so = prepareCommand("address", "desc", model);
        String expected = String.format(MESSAGE_SUCCESS, "address", "desc");
        assertCommandSuccess(so, model, expected, sortedByAddDesc);
    }

    @Test
    public void sortByEmail_success() throws Exception {
        SortCommand so = prepareCommand("email", "asc", model);
        String expected = String.format(MESSAGE_SUCCESS, "email", "asc");
        assertCommandSuccess(so, model, expected, sortedByEmail);
    }

    @Test
    public void sortByEmailDesc_success() throws Exception {
        SortCommand so = prepareCommand("email", "desc", model);
        String expected = String.format(MESSAGE_SUCCESS, "email", "desc");
        assertCommandSuccess(so, model, expected, sortedByEmailDesc);
    }

    /**
     * Returns a {@code sortCommand} with the parameters {@code field and @code order}.
     */
    private SortCommand prepareCommand(String field, String order, Model model) {
        SortCommand sortCommand = new SortCommand(field, order);
        sortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return sortCommand;
    }
}
