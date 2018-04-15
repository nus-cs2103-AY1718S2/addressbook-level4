package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.util.FileUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.Password;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.AddressBookBuilder;

//@@author Caijun7
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code ExportCommand}.
 */
public class ExportCommandTest {

    private static final String TEST_DATA_FILE = FileUtil.getPath("src/test/data/sandbox/temp.xml");
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/ExportCommandTest/");
    private static final String TEST_DATA_FILE_ALICE = TEST_DATA_FOLDER + "aliceAddressBook.xml";
    private static final String TEST_DATA_FILE_ALICE_BENSON = TEST_DATA_FOLDER + "aliceBensonAddressBook.xml";
    private static final String TEST_PASSWORD = "test";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBookWithAliceAndBenson = new AddressBookBuilder().withPerson(ALICE)
            .withPerson(BENSON).build();

    private Model model = new ModelManager(new AddressBook(), new UserPrefs());

    private final ExportCommand standardCommand = prepareCommand(TEST_DATA_FILE_ALICE_BENSON, model);

    @Test
    public void execute_emptyAddressBookExportIntoValidFilepathUnencrypted_success() throws Exception {
        String filepath = TEST_DATA_FILE;
        ExportCommand exportCommand = prepareCommand(filepath, model, null);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.exportAddressBook(filepath, null);
        assertCommandSuccess(exportCommand, model, ExportCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBookExportIntoValidFilepathUnencrypted_success() throws Exception {
        String filepath = TEST_DATA_FILE;
        ModelManager model = new ModelManager(addressBookWithAliceAndBenson, new UserPrefs());
        ExportCommand exportCommand = prepareCommand(filepath, model, null);

        ModelManager expectedModel = new ModelManager(addressBookWithAliceAndBenson, new UserPrefs());
        expectedModel.exportAddressBook(filepath, null);
        assertCommandSuccess(exportCommand, model, ExportCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_emptyAddressBookExportIntoValidFilepathEncrypted_success() throws Exception {
        String filepath = TEST_DATA_FILE;
        ExportCommand exportCommand = prepareCommand(filepath, model, TEST_PASSWORD);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.exportAddressBook(filepath, new Password(TEST_PASSWORD));
        assertCommandSuccess(exportCommand, model, ExportCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBookExportIntoValidFilepathEncrypted_success() throws Exception {
        String filepath = TEST_DATA_FILE;
        ModelManager model = new ModelManager(addressBookWithAliceAndBenson, new UserPrefs());
        ExportCommand exportCommand = prepareCommand(filepath, model, TEST_PASSWORD);

        ModelManager expectedModel = new ModelManager(addressBookWithAliceAndBenson, new UserPrefs());
        expectedModel.exportAddressBook(filepath, new Password(TEST_PASSWORD));
        assertCommandSuccess(exportCommand, model, ExportCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_emptyAddressBookExportIntoInvalidFilepath_throwsCommandException() throws Exception {
        String invalidFilepath = "";
        ExportCommand exportCommand = prepareCommand(invalidFilepath, model, null);

        assertCommandFailure(exportCommand, model, ExportCommand.MESSAGE_FILE_UNABLE_TO_SAVE);
    }

    @Test
    public void equals_sameValues_true() {
        ExportCommand commandWithSameValues = prepareCommand(TEST_DATA_FILE_ALICE_BENSON, model);
        assertTrue(standardCommand.equals(commandWithSameValues));
    }

    @Test
    public void equals_sameObject_true() {
        assertTrue(standardCommand.equals(standardCommand));
    }

    @Test
    public void equals_sameType_true() {
        assertTrue(standardCommand.equals(new ExportCommand(TEST_DATA_FILE_ALICE_BENSON, TEST_PASSWORD)));
    }

    @Test
    public void equals_nullInstance_false() {
        assertFalse(standardCommand.equals(null));
    }

    @Test
    public void equals_differentTypes_false() {
        assertFalse(standardCommand.equals(new ClearCommand()));
    }

    @Test
    public void equals_differentAddressBook_false() {
        assertFalse(standardCommand.equals(prepareCommand(TEST_DATA_FILE_ALICE, model)));
    }

    /**
     * Returns a {@code ExportCommand} with the parameter {@code filepath} with password as TEST_PASSWORD.
     */
    private ExportCommand prepareCommand(String filepath, Model model) {
        return prepareCommand(filepath, model, TEST_PASSWORD);
    }

    /**
     * Returns a {@code ExportCommand} with the parameter {@code filepath} and {@code password}.
     */
    private ExportCommand prepareCommand(String filepath, Model model, String password) {
        ExportCommand exportCommand;
        if (password == null) {
            exportCommand = new ExportCommand(filepath);
        } else {
            exportCommand = new ExportCommand(filepath, password);
        }
        exportCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return exportCommand;
    }

}
