package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    /*
    @Test
    public void execute_newPerson_success() throws Exception {
        Person validPerson = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(validPerson);

        assertCommandSuccess(prepareCommand(validPerson, model), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validPerson), expectedModel);
    }
    */

    @Test
    public void execute_duplicateNric_throwsCommandException() throws IOException {
        Person personInList = model.getAddressBook().getPersonList().get(0);
        assertCommandFailure(prepareCommand(personInList, model), model,
                "This NRIC already exists in the address book");
    }

    /**
     * Generates a new {@code AddCommand} which upon execution, adds {@code person} into the {@code model}.
     */
    private AddCommand prepareCommand(Person person, Model model) {
        AddCommand command = new AddCommand(person);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
