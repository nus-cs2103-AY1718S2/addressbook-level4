package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalClients.getTypicalAddressBookNew;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandNotAvailableInClosedViewException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Category;
import seedu.address.ui.util.ListPanelController;

//@@author olimhc
/**
 * Contains integration tests (interaction with the Model) for {@code CloseCommandTest}.
 */
public class CloseCommandTest {
    private static ListPanelController listPanelController = ListPanelController.getInstance();

    private Model model = new ModelManager(getTypicalAddressBookNew(), new UserPrefs());

    private final Category studentCategory = new Category("s");
    private final Category tutorCategory = new Category("t");

    /**
     * Ensure that the list is always displaying active clients.
     */
    @Before
    public void setUp() {
        listPanelController.setDefault();
    }

    /**
     * Tests if a particular tutor is closed properly.
     * @throws Exception
     */
    @Test
    public void execute_closeClientTutorFilteredList_success() throws Exception {
        CloseCommand closeCommand = prepareCommand(Index.fromZeroBased(0), tutorCategory);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.deleteClient(expectedModel.getFilteredTutorList().get(0), tutorCategory);

        String expectedMessage = String.format(CloseCommand.MESSAGE_CLOSE_TUTOR_SUCCESS,
                model.getFilteredTutorList().get(0));

        expectedModel.addClosedTutor(model.getFilteredTutorList().get(0));
        assertCommandSuccess(closeCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Tests if a particular student is closed properly.
     * @throws Exception
     */
    @Test
    public void execute_closeClientStudentFilteredList_success() throws Exception {
        CloseCommand closeCommand = prepareCommand(Index.fromZeroBased(0), studentCategory);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.deleteClient(expectedModel.getFilteredStudentList().get(0), studentCategory);

        String expectedMessage = String.format(CloseCommand.MESSAGE_CLOSE_STUDENT_SUCCESS,
                model.getFilteredStudentList().get(0));

        expectedModel.addClosedStudent(model.getFilteredStudentList().get(0));
        assertCommandSuccess(closeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void assertsRestoreNotAvailableInClosedList() throws Exception {
        ListPanelController listPanelController = ListPanelController.getInstance();
        listPanelController.switchDisplay();

        CloseCommand closeCommand = prepareCommand(Index.fromZeroBased(0), studentCategory);
        try {
            closeCommand.execute();
        } catch (CommandNotAvailableInClosedViewException cna) {
            assertsRestoreNotAvailableInClosedList();
        }
    }

    /**
     * Returns an {@code CloseCommand} with parameters {@code index} and {@code category}
     */
    private CloseCommand prepareCommand(Index index, Category category) {
        CloseCommand closeCommand = new CloseCommand(index, category);
        closeCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return closeCommand;
    }
}
