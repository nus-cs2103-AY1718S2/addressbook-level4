package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.RATECOUNT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.RATECOUNT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.logic.commands.RateCommand.MESSAGE_RATE_PERSON_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_NINTH_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Rate;
import seedu.address.testutil.PersonBuilder;

//@@author sherlynng
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for RateCommand.
 */
public class RateCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        Rate rate = new Rate(Double.parseDouble(VALID_RATE_BOB), true);
        new RateCommand(null, rate);
    }

    @Test
    public void constructor_nullRate_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new RateCommand(INDEX_FIRST_PERSON, null);
    }

    @Test
    public void execute_filteredListAbsoluteRate_success() throws Exception {
        showPersonAtIndex(model, INDEX_NINTH_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withRate(VALID_RATE_BOB, RATECOUNT_BOB).build();
        Rate rate = new Rate(Double.parseDouble(VALID_RATE_BOB), true);
        rate.setCount(Integer.parseInt(RATECOUNT_BOB));
        RateCommand rateCommand = prepareCommand(INDEX_FIRST_PERSON, rate);
        rateCommand.preprocessUndoableCommand();
        String expectedMessage = String.format(MESSAGE_RATE_PERSON_SUCCESS,
                                 editedPerson.getName(), VALID_RATE_BOB);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(rateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredListAccumulatedRate_success() throws Exception {
        showPersonAtIndex(model, INDEX_NINTH_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Rate rate = new Rate(Double.parseDouble(VALID_RATE_AMY), false);
        Rate accumulatedRate = personInFilteredList.getRate().accumulatedValue(personInFilteredList.getRate(), rate);
        Person editedPerson = new PersonBuilder(personInFilteredList)
                .withRate(Double.toString(accumulatedRate.getDisplayedValue()),
                        Integer.toString(accumulatedRate.getCount())).build();

        RateCommand rateCommand = prepareCommand(INDEX_FIRST_PERSON, rate);
        rateCommand.preprocessUndoableCommand();
        String expectedMessage = String.format(MESSAGE_RATE_PERSON_SUCCESS,
                editedPerson.getName(), VALID_RATE_AMY);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(rateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Rate rate = new Rate(Double.parseDouble(VALID_RATE_BOB), true);
        rate.setCount(Integer.parseInt(RATECOUNT_BOB));
        RateCommand rateCommand = prepareCommand(outOfBoundIndex, rate);

        assertCommandFailure(rateCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Adds rate in filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        Rate rate = new Rate(Double.parseDouble(VALID_RATE_BOB), true);
        rate.setCount(Integer.parseInt(RATECOUNT_BOB));
        RateCommand rateCommand = prepareCommand(outOfBoundIndex, rate);

        assertCommandFailure(rateCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Person editedPerson = new PersonBuilder().withName("Alice Pauline")
                .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com").withPhone("85355255")
                .withPrice("50").withSubject("math").withStatus("Not Matched").withLevel("lower Sec")
                .withRole("Tutor").withRemark("Hardworking but slow learner.")
                .withRate(VALID_RATE_BOB, RATECOUNT_BOB).build();
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Rate rate = new Rate(Double.parseDouble(VALID_RATE_BOB), true);
        rate.setCount(Integer.parseInt(RATECOUNT_BOB));
        RateCommand rateCommand = prepareCommand(INDEX_FIRST_PERSON, rate);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        // add rate -> adds rate to first person
        rateCommand.execute();
        undoRedoStack.push(rateCommand);

        // undo -> reverts STUtor back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person with rate added again
        expectedModel.updatePerson(personToEdit, editedPerson);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Rate rate = new Rate(Double.parseDouble(VALID_RATE_BOB), true);
        rate.setCount(Integer.parseInt(RATECOUNT_BOB));
        RateCommand rateCommand = prepareCommand(outOfBoundIndex, rate);

        // execution failed -> rateCommand not pushed into undoRedoStack
        assertCommandFailure(rateCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Adds a rate to a {@code Person} from a filtered list.
     * 2. Undo the adding of rate.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the adding of rate. This ensures {@code RedoCommand} edits the person object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonEdited() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Person editedPerson = new PersonBuilder().withName("Benson Meier")
                .withAddress("311, Clementi Ave 2, #02-25").withEmail("johnd@example.com").withPhone("98765432")
                .withPrice("50").withSubject("math").withStatus("Matched").withLevel("lower Sec")
                .withRole("Student").withRemark("Not self motivated.")
                .withRate(VALID_RATE_BOB, RATECOUNT_BOB).build();
        Rate rate = new Rate(Double.parseDouble(VALID_RATE_BOB), true);
        rate.setCount(Integer.parseInt(RATECOUNT_BOB));
        RateCommand rateCommand = prepareCommand(INDEX_FIRST_PERSON, rate);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        // add rate -> adds rate to second person in unfiltered person list / first person in filtered person list
        rateCommand.execute();
        undoRedoStack.push(rateCommand);

        // undo -> reverts STUtor back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.updatePerson(personToEdit, editedPerson);
        assertNotEquals(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), personToEdit);
        // redo -> adds rate to same second person in unfiltered person list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        Rate rate = new Rate(Double.parseDouble(VALID_RATE_BOB), true);
        rate.setCount(Integer.parseInt(RATECOUNT_BOB));
        final RateCommand rateCommand = prepareCommand(INDEX_FIRST_PERSON, rate);

        // same values -> returns true
        Person bob = new PersonBuilder().withRate(VALID_RATE_BOB, RATECOUNT_BOB).build();
        RateCommand commandWithSameValues = prepareCommand(INDEX_FIRST_PERSON, bob.getRate());
        assertTrue(rateCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(rateCommand.equals(rateCommand));

        // null -> returns false
        assertFalse(rateCommand.equals(null));

        // different types -> returns false
        assertFalse(rateCommand.equals(new ClearCommand()));

        // different index -> returns false
        bob = new PersonBuilder().withRate(VALID_RATE_BOB, RATECOUNT_BOB).build();
        RateCommand commandWithDifferentIndex = prepareCommand(INDEX_SECOND_PERSON, bob.getRate());
        assertFalse(rateCommand.equals(commandWithDifferentIndex));

        // different rate -> returns false
        Person amy = new PersonBuilder().withRate(VALID_RATE_AMY, RATECOUNT_AMY).build();
        RateCommand commandWithDifferentPerson = prepareCommand(INDEX_FIRST_PERSON, amy.getRate());
        assertFalse(rateCommand.equals(commandWithDifferentPerson));
    }

    /**
     * Returns an {@code RateCommand} with parameters {@code index} and {@code rate}
     */
    private RateCommand prepareCommand(Index index, Rate rate) {
        RateCommand rateCommand = new RateCommand(index, rate);
        rateCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return rateCommand;
    }
}
