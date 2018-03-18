package seedu.progresschecker.logic.commands;

import static seedu.progresschecker.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.progresschecker.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.progresschecker.testutil.TypicalPersons.getTypicalProgressChecker;

import org.junit.Before;
import org.junit.Test;

import seedu.progresschecker.logic.CommandHistory;
import seedu.progresschecker.logic.UndoRedoStack;
import seedu.progresschecker.model.Model;
import seedu.progresschecker.model.ModelManager;
import seedu.progresschecker.model.UserPrefs;
import seedu.progresschecker.model.person.Person;
import seedu.progresschecker.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalProgressChecker(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() throws Exception {
        Person validPerson = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getProgressChecker(), new UserPrefs());
        expectedModel.addPerson(validPerson);

        assertCommandSuccess(prepareCommand(validPerson, model), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validPerson), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = model.getProgressChecker().getPersonList().get(0);
        assertCommandFailure(prepareCommand(personInList, model), model, AddCommand.MESSAGE_DUPLICATE_PERSON);
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
