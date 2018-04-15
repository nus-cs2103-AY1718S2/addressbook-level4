package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalClients.getTypicalClosedClientsAddressBook;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandNotAvailableInActiveViewException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Category;
import seedu.address.ui.util.ListPanelController;

//@@author olimhc
/**
 * Contains integration tests (interaction with the Model) for {@code RestoreCommandTest}.
 */
public class RestoreCommandTest {
    private static ListPanelController listPanelController;

    private Model model = new ModelManager(getTypicalClosedClientsAddressBook(), new UserPrefs());

    private final Category studentCategory = new Category("s");
    private final Category tutorCategory = new Category("t");

    /**
     * Ensure display is displaying closed client list.
     */
    @BeforeClass
    public static void setup() {
        listPanelController = ListPanelController.getInstance();
        if (ListPanelController.isCurrentDisplayActiveList()) {
            listPanelController.switchDisplay();
        }
    }

    /**
     *Ensure display is at active client list after this class test.
     */
    @AfterClass
    public static void tearDownAfterClass() {
        listPanelController.setDefault();
    }

    /**
     * Tests if a particular tutor is restored properly.
     * @throws Exception
     */
    @Test
    public void execute_restoreClientTutorFilteredList_success() throws Exception {
        RestoreCommand restoreCommand = prepareCommand(Index.fromZeroBased(0), tutorCategory);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.deleteClosedClient(expectedModel.getFilteredClosedTutorList().get(0), tutorCategory);

        String expectedMessage = String.format(RestoreCommand.MESSAGE_RESTORE_TUTOR_SUCCESS,
                model.getFilteredClosedTutorList().get(0));

        expectedModel.addTutor(model.getFilteredClosedTutorList().get(0));
        assertCommandSuccess(restoreCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Tests if a particular student is restored properly.
     * @throws Exception
     */
    @Test
    public void execute_closeClientStudentFilteredList_success() throws Exception {
        RestoreCommand restoreCommand = prepareCommand(Index.fromZeroBased(0), studentCategory);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.deleteClosedClient(expectedModel.getFilteredClosedStudentList().get(0), studentCategory);

        String expectedMessage = String.format(RestoreCommand.MESSAGE_RESTORE_STUDENT_SUCCESS,
                model.getFilteredClosedStudentList().get(0));

        expectedModel.addStudent(model.getFilteredClosedStudentList().get(0));
        assertCommandSuccess(restoreCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void assertsRestoreNotAvailableInActiveList() throws Exception {
        listPanelController.switchDisplay();
        RestoreCommand restoreCommand = prepareCommand(Index.fromZeroBased(0), studentCategory);
        try {
            restoreCommand.execute();
        } catch (CommandNotAvailableInActiveViewException cna) {
            assertsRestoreNotAvailableInActiveList();
        }
    }

    /**
     * Returns an {@code CloseCommand} with parameters {@code index} and {@code category}
     */
    private RestoreCommand prepareCommand(Index index, Category category) {
        RestoreCommand restoreCommand = new RestoreCommand(index, category);
        restoreCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return restoreCommand;
    }
}
