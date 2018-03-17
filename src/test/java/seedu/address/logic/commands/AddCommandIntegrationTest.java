package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.Patient;
import seedu.address.testutil.PatientBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() throws Exception {
        Patient validPatient = new PatientBuilder().build();

        Model expectedModel = new ModelManager(model.getIMDB(), new UserPrefs());
        expectedModel.addPerson(validPatient);

        assertCommandSuccess(prepareCommand(validPatient, model), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validPatient), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Patient patientInList = model.getIMDB().getPersonList().get(0);
        assertCommandFailure(prepareCommand(patientInList, model), model, AddCommand.MESSAGE_DUPLICATE_PERSON);
    }

    /**
     * Generates a new {@code AddCommand} which upon execution, adds {@code patient} into the {@code model}.
     */
    private AddCommand prepareCommand(Patient patient, Model model) {
        AddCommand command = new AddCommand(patient);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
