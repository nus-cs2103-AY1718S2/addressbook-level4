package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_TUTOR_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalClients.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Client;
import seedu.address.testutil.ClientBuilder;

//@@author shookshire
/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddClientCommandIntegrationTest {


    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() throws Exception {
        Client validStudent = new ClientBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addStudent(validStudent);

        assertCommandSuccess(prepareCommand(validStudent, model), model,
                String.format(AddClientCommand.MESSAGE_SUCCESS_STUDENT, validStudent), expectedModel);

        Client validTutor = new ClientBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_FRIEND)
                .withLocation(VALID_LOCATION_BOB).withGrade(VALID_GRADE_BOB).withSubject(VALID_GRADE_BOB)
                .withCategory(VALID_CATEGORY_TUTOR_BOB).build();

        expectedModel.addTutor(validTutor);

        assertCommandSuccess(prepareCommand(validTutor, model), model,
                String.format(AddClientCommand.MESSAGE_SUCCESS_TUTOR, validTutor), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Client personInList = model.getAddressBook().getStudentList().get(0);
        assertCommandFailure(prepareCommand(personInList, model), model, AddClientCommand.MESSAGE_DUPLICATE_PERSON);
    }

    /**
     * Generates a new {@code AddCommand} which upon execution, adds {@code person} into the {@code model}.
     */
    private AddClientCommand prepareCommand(Client client, Model model) {
        AddClientCommand command = new AddClientCommand(client);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
