package seedu.address.logic.commands;
//@@author crizyli
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code AddPhotoCommand}.
 */
public class AddPhotoCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void addPhotoSuccess() throws Exception {
        Person personToAddPhoto = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        AddPhotoCommand addPhotoCommand = prepareCommand(INDEX_FIRST_PERSON);
        Person newPerson = new PersonBuilder(personToAddPhoto).build();
        newPerson.setPhotoName("DefaultPerson.png");

        String expectedMessage = AddPhotoCommand.MESSAGE_SUCCESS;

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), newPerson);

        assertCommandSuccess(addPhotoCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Returns a {@code AddPhotoCommand} with the parameter {@code index}.
     */
    private AddPhotoCommand prepareCommand(Index index) {
        AddPhotoCommand addPhotoCommand = new AddPhotoCommand(index);
        addPhotoCommand.setTestMode();
        addPhotoCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return addPhotoCommand;
    }
}
