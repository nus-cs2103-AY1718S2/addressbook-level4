package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showStudentAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

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
    private ListCommand listCommandAll;
    private ListCommand listCommandFavOnly;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        listCommandAll = new ListCommand(false);
        listCommandAll.setData(model, new CommandHistory(), new UndoRedoStack());
        listCommandFavOnly = new ListCommand(true);
        listCommandFavOnly.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(listCommandAll, model, ListCommand.MESSAGE_SUCCESS_LIST_ALL, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showStudentAtIndex(model, INDEX_FIRST_STUDENT);
        assertCommandSuccess(listCommandAll, model, ListCommand.MESSAGE_SUCCESS_LIST_ALL, expectedModel);
    }

    @Test
    public void execute_listIsFilterd_showsFavouritesOnly() {
        expectedModel.updateFilteredStudentList(Model.PREDICATE_SHOW_FAVOURITE_STUDENTS);
        assertCommandSuccess(listCommandFavOnly, model, ListCommand.MESSAGE_SUCCESS_LIST_FAVOURITES, expectedModel);
    }

}
