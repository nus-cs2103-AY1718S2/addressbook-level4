package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.TypicalPersons;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class SortCommandTest {

    private Model model;
    private Model customModel;
    private Model expectedModel;
    private SortCommand sortCommand;

    @Before
    public void setUp() throws Exception {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        customModel = new ModelManager(generateModelWithPersons(generatePersonList(
                TypicalPersons.JOHN3, TypicalPersons.JOHN2, TypicalPersons.JANE, TypicalPersons.BLAKE,
                TypicalPersons.HOB2, TypicalPersons.JOHN1, TypicalPersons.LEONARD, TypicalPersons.HOB1
        )).getAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(generateModelWithPersons(generatePersonList(
                TypicalPersons.BLAKE, TypicalPersons.HOB1, TypicalPersons.HOB2, TypicalPersons.JANE,
                TypicalPersons.JOHN1, TypicalPersons.JOHN2, TypicalPersons.JOHN3, TypicalPersons.LEONARD
        )).getAddressBook(), new UserPrefs());
        sortCommand = new SortCommand();
        sortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_unfilteredListAlreadySorted_failure() {
        assertCommandFailure(sortCommand, model, SortCommand.MESSAGE_FAILURE);
    }

    @Test
    public void execute_filteredListAlreadySorted_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandFailure(sortCommand, model, SortCommand.MESSAGE_FAILURE);
    }

    @Test
    public void execute_emptyList_failure() {
        model.resetData(new AddressBook());
        assertCommandFailure(sortCommand, model, Messages.MESSAGE_ADDRESS_BOOK_EMPTY);
    }

    @Test
    public void execute_unfilteredListUnsorted_success() {
        model.resetData(customModel.getAddressBook());
        assertCommandSuccess(sortCommand, model, SortCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_unfilteredList_success() throws Exception {
        model.resetData(customModel.getAddressBook());
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);

        // sort -> sorts all persons in address book
        sortCommand.execute();
        undoRedoStack.push(sortCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, customModel);

        // redo -> sorts all persons in address book again
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    /**
     * 1. Sorts all persons from a filtered list, such that the list is kept.
     * 2. Undo the sorting.
     * 3. The unfiltered list should be shown now. Verify that the list is reverted to before it is sorted.
     * 4. Redo the sorting. The list shown should still be unfiltered.
     */
    @Test
    public void executeUndoRedo_filteredList_success() {
        model.resetData(customModel.getAddressBook());
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);

        showPersonAtIndex(model, INDEX_THIRD_PERSON);
        showPersonAtIndex(expectedModel, Index.fromZeroBased(
                expectedModel.getFilteredPersonList().indexOf(model.getFilteredPersonList().get(0))));

        // sort -> sorts all persons in address book
        assertCommandSuccess(sortCommand, model, SortCommand.MESSAGE_SUCCESS, expectedModel);
        undoRedoStack.push(sortCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, customModel);

        // redo -> sorts all persons in address book again
        expectedModel.updateFilteredPersonList(unused -> true);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    //@@author Nethergale-reused
    //Reused from https://github.com/nus-cs2103-AY1718S2/addressbook-level3/
    //blob/master/test/java/seedu/addressbook/logic/LogicTest.java with minor modifications
    /**
     * Creates a list of Persons based on the given Person objects.
     */
    private List<Person> generatePersonList(Person... persons) {
        List<Person> personList = new ArrayList<>();
        for (Person p : persons) {
            personList.add(p);
        }
        return personList;
    }
    //@@author

    /**
     * Creates a model with all persons found in the list added.
     */
    private Model generateModelWithPersons(List<Person> personList) throws Exception {
        Model m = new ModelManager();
        for (Person p : personList) {
            m.addPerson(p);
        }
        return m;
    }
}
