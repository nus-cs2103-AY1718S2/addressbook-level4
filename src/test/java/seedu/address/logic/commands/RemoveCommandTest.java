package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalClients.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Category;
import seedu.address.model.person.Client;
import seedu.address.model.person.Subject;
import seedu.address.testutil.ClientBuilder;

//@@Author shookshire
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code RemoveCommand}.
 */
public class RemoveCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        RemoveCommand removeCommand = prepareCommand(outOfBoundIndex, "math");

        assertCommandFailure(removeCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Client personToRemove = model.getFilteredStudentList().get(INDEX_FIRST_PERSON.getZeroBased());
        RemoveCommand removeCommand = prepareCommand(INDEX_FIRST_PERSON, "math");

        Client editedPerson = new ClientBuilder(personToRemove).withSubject("chemistry physics").build();

        String expectedMessage = String.format(RemoveCommand.MESSAGE_REMOVE_CLIENT_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateClient(model.getFilteredStudentList().get(0), editedPerson, new Category("s"));

        assertCommandSuccess(removeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getStudentList().size());

        RemoveCommand removeCommand = prepareCommand(outOfBoundIndex, "math");

        assertCommandFailure(removeCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Client personToRemove = model.getFilteredStudentList().get(INDEX_FIRST_PERSON.getZeroBased());
        RemoveCommand removeCommand = prepareCommand(INDEX_FIRST_PERSON, "math");

        Client editedPerson = new ClientBuilder(personToRemove).withSubject("chemistry physics").build();

        String expectedMessage = String.format(RemoveCommand.MESSAGE_REMOVE_CLIENT_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateClient(model.getFilteredStudentList().get(0), editedPerson, new Category("s"));

        assertCommandSuccess(removeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidSubjectFormat_throwsCommandException() throws Exception {
        RemoveCommand removeCommand = prepareCommand(INDEX_FIRST_PERSON, "math chemistry");

        assertCommandFailure(removeCommand, model, RemoveCommand.MESSAGE_USAGE);
    }

    @Test
    public void execute_invalidSubjectNonExist_throwsCommandException() throws Exception {
        RemoveCommand removeCommand = prepareCommand(INDEX_FIRST_PERSON, "chinese");

        assertCommandFailure(removeCommand, model, RemoveCommand.MESSAGE_VALUE_DONT_EXIST);
    }

    @Test
    public void execute_invalidLastSubject_throwsCommandException() throws Exception {
        RemoveCommand removeCommand = prepareCommand(INDEX_SECOND_PERSON, "physics");

        assertCommandFailure(removeCommand, model, RemoveCommand.MESSAGE_LAST_VALUE);
    }

    /**
     * Returns a {@code RemoveCommand} with the parameter {@code index}.
     */
    private RemoveCommand prepareCommand(Index index, String subject) {
        RemoveCommand removeCommand = new RemoveCommand(index, new Subject(subject), new Category("s"));
        removeCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return removeCommand;
    }
}
