package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;

import org.junit.Assert;
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
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

//@@author TeyXinHui
public class CcaCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullIndexAndPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new CcaCommand(null, null);

        thrown.expect(NullPointerException.class);
        new CcaCommand(INDEX_FIRST_PERSON, null);

        thrown.expect(NullPointerException.class);
        new CcaCommand(null, new EditPersonDescriptor());
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        Person editedPerson = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        CcaCommand ccaCommand = prepareCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(CcaCommand.MESSAGE_REMARK_PERSON_SUCCESS, editedPerson.getCca(),
                editedPerson.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(ccaCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() throws Exception {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastPerson);
        Person editedPerson = personInList.build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withCca("Basketball", "Member").build();
        CcaCommand ccaCommand = prepareCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(CcaCommand.MESSAGE_REMARK_PERSON_SUCCESS, editedPerson.getCca(),
                editedPerson.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(lastPerson, editedPerson);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).build();
        CcaCommand ccaCommand = prepareCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder().withCca("Basketball", "Member").build());

        String expectedMessage = String.format(CcaCommand.MESSAGE_REMARK_PERSON_SUCCESS, editedPerson.getCca(),
                editedPerson.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() throws IOException {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(firstPerson).build();
        CcaCommand ccaCommand = prepareCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(ccaCommand, model, CcaCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() throws IOException {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // edit person in filtered list into a duplicate in address book
        Person personInList = model.getAddressBook().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        CcaCommand ccaCommand = prepareCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder(personInList).build());

        assertCommandFailure(ccaCommand, model, CcaCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws IOException {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withCca("Basketball", "Member").build();
        CcaCommand ccaCommand = prepareCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(ccaCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws IOException {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        Assert.assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        CcaCommand ccaCommand = prepareCommand(outOfBoundIndex,
                new EditPersonDescriptorBuilder().withCca("Basketball", "Member").build());

        assertCommandFailure(ccaCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withCca("Basketball", "Member").build();
        CcaCommand ccaCommand = prepareCommand(outOfBoundIndex, descriptor);

        // execution failed -> ccaCommand not pushed into undoRedoStack

        try {
            assertCommandFailure(ccaCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } catch (IOException e) {
            Assert.fail("The expected CommandException was not thrown.");
        }

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        try {
            assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        } catch (IOException e) {
            Assert.fail("The expected CommandException was not thrown.");
        }
        try {
            assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
        } catch (IOException e) {
            Assert.fail("The expected CommandException was not thrown.");
        }

    }

    @Test
    public void equals() throws Exception {
        final CcaCommand standardCommand = prepareCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        CcaCommand commandWithSameValues = prepareCommand(INDEX_FIRST_PERSON, copyDescriptor);
        Assert.assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        Assert.assertTrue(standardCommand.equals(standardCommand));

        // one command preprocessed when previously equal -> returns false
        commandWithSameValues.preprocessUndoableCommand();
        assertFalse(standardCommand.equals(commandWithSameValues));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new CcaCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new CcaCommand(INDEX_FIRST_PERSON, DESC_BOB)));
    }

    /**
     * Returns an {@code CcaCommand} with parameters {@code index} and {@code descriptor}
     */
    private CcaCommand prepareCommand(Index index, EditPersonDescriptor descriptor) {
        CcaCommand ccaCommand = new CcaCommand(index, descriptor);
        ccaCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return ccaCommand;
    }
    //@@author
}
