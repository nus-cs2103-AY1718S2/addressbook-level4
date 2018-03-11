package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;
    private ListCommand listCommand_All;
    private ListCommand listCommand_FavOnly;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        listCommand_All = new ListCommand(false);
        listCommand_All.setData(model, new CommandHistory(), new UndoRedoStack());
        listCommand_FavOnly = new ListCommand(true);
        listCommand_FavOnly.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(listCommand_All, model, ListCommand.MESSAGE_SUCCESS_LIST_ALL, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(listCommand_All, model, ListCommand.MESSAGE_SUCCESS_LIST_ALL, expectedModel);
    }

    @Test
    public void execute_listIsFilterd_showsFavouritesOnly() {
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_FAVOURITE_PERSONS);
        assertCommandSuccess(listCommand_FavOnly, model, ListCommand.MESSAGE_SUCCESS_LIST_FAVOURITES, expectedModel);
    }

}
