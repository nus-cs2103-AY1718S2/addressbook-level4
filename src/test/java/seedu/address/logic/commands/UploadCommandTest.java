package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import org.junit.AfterClass;
import org.junit.BeforeClass;
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
import seedu.address.storage.GoogleDriveStorage;
import seedu.address.testutil.AddressBookBuilder;

//@@author Caijun7
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code UploadCommand}.
 */
public class UploadCommandTest {

    private static final String TEST_DATA_FILE = FileUtil.getPath("src/test/data/sandbox/temp.xml");
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/UploadCommandTest/");
    private static final String TEST_DATA_FILE_ALICE = TEST_DATA_FOLDER + "aliceAddressBook.xml";
    private static final String TEST_DATA_FILE_ALICE_BENSON = TEST_DATA_FOLDER + "aliceBensonAddressBook.xml";
    private static final String TEST_PASSWORD = "test";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBookWithAliceAndBenson = new AddressBookBuilder().withPerson(ALICE)
            .withPerson(BENSON).build();

    private Model model = new ModelManager(new AddressBook(), new UserPrefs());

    private final UploadCommand standardCommand = prepareCommand(TEST_DATA_FILE_ALICE_BENSON, model);

    @BeforeClass
    public static void setTestEnvironment() {
        GoogleDriveStorage.setTestEnvironment();
    }

    @Test
    public void execute_emptyAddressBookUploadIntoValidFilepathUnencrypted_success() throws Exception {
        String filepath = TEST_DATA_FILE;
        UploadCommand uploadCommand = prepareCommand(filepath, model, null);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.uploadAddressBook(filepath, null);
        assertCommandSuccess(uploadCommand, model, UploadCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBookUploadIntoValidFilepathUnencrypted_success() throws Exception {
        String filepath = TEST_DATA_FILE;
        ModelManager model = new ModelManager(addressBookWithAliceAndBenson, new UserPrefs());
        UploadCommand uploadCommand = prepareCommand(filepath, model, null);

        ModelManager expectedModel = new ModelManager(addressBookWithAliceAndBenson, new UserPrefs());
        expectedModel.uploadAddressBook(filepath, null);
        assertCommandSuccess(uploadCommand, model, UploadCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_emptyAddressBookUploadIntoValidFilepathEncrypted_success() throws Exception {
        String filepath = TEST_DATA_FILE;
        UploadCommand uploadCommand = prepareCommand(filepath, model, TEST_PASSWORD);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.uploadAddressBook(filepath, new Password(TEST_PASSWORD));
        assertCommandSuccess(uploadCommand, model, UploadCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBookUploadIntoValidFilepathEncrypted_success() throws Exception {
        String filepath = TEST_DATA_FILE;
        ModelManager model = new ModelManager(addressBookWithAliceAndBenson, new UserPrefs());
        UploadCommand uploadCommand = prepareCommand(filepath, model, TEST_PASSWORD);

        ModelManager expectedModel = new ModelManager(addressBookWithAliceAndBenson, new UserPrefs());
        expectedModel.uploadAddressBook(filepath, new Password(TEST_PASSWORD));
        assertCommandSuccess(uploadCommand, model, UploadCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_addressBookUploadIntoInvalidFilepath_throwsCommandException() throws Exception {
        String invalidFilepath = "";
        UploadCommand uploadCommand = prepareCommand(invalidFilepath, model, null);

        assertCommandFailure(uploadCommand, model, UploadCommand.MESSAGE_FILE_UNABLE_TO_SAVE);
    }

    @Test
    public void execute_addressBookUploadNoUserResponse_throwsCommandException() throws Exception {
        String filepath = TEST_DATA_FILE;
        ModelManager model = new ModelManager(addressBookWithAliceAndBenson, new UserPrefs());
        UploadCommand uploadCommand = prepareCommand(filepath, model, TEST_PASSWORD);

        GoogleDriveStorage.resetTestEnvironment();
        assertCommandFailure(uploadCommand, model, UploadCommand.MESSAGE_REQUEST_TIMEOUT);
        GoogleDriveStorage.setTestEnvironment();
    }

    @Test
    public void equals_sameValues_true() {
        UploadCommand commandWithSameValues = prepareCommand(TEST_DATA_FILE_ALICE_BENSON, model);
        assertTrue(standardCommand.equals(commandWithSameValues));
    }

    @Test
    public void equals_sameObject_true() {
        assertTrue(standardCommand.equals(standardCommand));
    }

    @Test
    public void equals_sameType_true() {
        assertTrue(standardCommand.equals(new UploadCommand(TEST_DATA_FILE_ALICE_BENSON, TEST_PASSWORD)));
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
     * Returns a {@code UploadCommand} with the parameter {@code filepath} with password as TEST_PASSWORD.
     */
    private UploadCommand prepareCommand(String filepath, Model model) {
        return prepareCommand(filepath, model, TEST_PASSWORD);
    }

    /**
     * Returns a {@code UploadCommand} with the parameter {@code filepath} and {@code password}.
     */
    private UploadCommand prepareCommand(String filepath, Model model, String password) {
        UploadCommand uploadCommand;
        if (password == null) {
            uploadCommand = new UploadCommand(filepath);
        } else {
            uploadCommand = new UploadCommand(filepath, password);
        }
        uploadCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return uploadCommand;
    }

    @AfterClass
    public static void resetTestEnvironment() {
        GoogleDriveStorage.resetTestEnvironment();
    }
}
