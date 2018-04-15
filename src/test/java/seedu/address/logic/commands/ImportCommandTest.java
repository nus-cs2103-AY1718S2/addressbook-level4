package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.SecurityUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.AddressBookBuilder;

//@@author Caijun7
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code ImportCommand}.
 */
public class ImportCommandTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/ImportCommandTest/");
    private static final String TEST_DATA_FILE_ALICE = TEST_DATA_FOLDER + "aliceAddressBook.xml";
    private static final String TEST_DATA_FILE_ALICE_BENSON = TEST_DATA_FOLDER + "aliceBensonAddressBook.xml";
    private static final String TEST_PASSWORD = "test";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBookWithAliceAndBenson = new AddressBookBuilder().withPerson(ALICE)
            .withPerson(BENSON).build();

    private Model model = new ModelManager(new AddressBook(), new UserPrefs());

    private final ImportCommand standardCommand = prepareCommand(TEST_DATA_FILE_ALICE_BENSON, model);

    @Test
    public void execute_validFileImportIntoEmptyAddressBook_success() throws Exception {
        String filepath = TEST_DATA_FOLDER + "aliceBensonAddressBook.xml";
        ImportCommand importCommand = prepareCommand(filepath, model);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.importAddressBook(filepath, SecurityUtil.hashPassword(""));
        assertCommandSuccess(importCommand, model, ImportCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_validFileImportIntoNonEmptyAddressBook_success() throws Exception {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        String filepath = TEST_DATA_FOLDER + "aliceBensonAddressBook.xml";
        ImportCommand importCommand = prepareCommand(filepath, model);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.importAddressBook(filepath, SecurityUtil.hashPassword(""));
        assertCommandSuccess(importCommand, model, ImportCommand.MESSAGE_SUCCESS, expectedModel);
    }
    //@@author

    //@@author yeggasd
    @Test
    public void execute_encryptedAddressBook_success() throws Exception {
        String encryptedFile = TEST_DATA_FOLDER + "encryptedAliceBensonAddressBook.xml";

        ImportCommand importCommand = prepareCommand(encryptedFile, model, TEST_PASSWORD);
        importCommand.executeUndoableCommand();
        SecurityUtil.encrypt(encryptedFile, TEST_PASSWORD);
        assertEquals(model.getAddressBook(), addressBookWithAliceAndBenson);
    }

    @Test
    public void execute_wrongPasswordEncryptedAddressBook_throwsCommandException() throws Exception {
        String encryptedFile = TEST_DATA_FOLDER + "encryptedAliceBensonAddressBook.xml";
        ImportCommand importCommand = prepareCommand(encryptedFile, model, TEST_PASSWORD + "1");
        thrown.expect(CommandException.class);
        importCommand.executeUndoableCommand();
    }
    //@@author

    //@@author Caijun7
    @Test
    public void execute_nonExistentFileImportIntoAddressBook_throwsCommandException() throws Exception {
        String nonExistentFile = TEST_DATA_FOLDER + "nonExistentFile.xml";
        ImportCommand importCommand = prepareCommand(nonExistentFile, model);

        assertCommandFailure(importCommand, model, ImportCommand.MESSAGE_FILE_NOT_FOUND);
    }

    @Test
    public void execute_invalidFileFormatImportIntoAddressBook_throwsCommandException() throws Exception {
        String invalidFileFormat = TEST_DATA_FOLDER + "invalidFileFormatAddressBook.xml";
        ImportCommand importCommand = prepareCommand(invalidFileFormat, model);

        assertCommandFailure(importCommand, model, ImportCommand.MESSAGE_DATA_CONVERSION_ERROR);
    }

    @Test
    public void execute_importDuplicateAddressBook_noChange() throws Exception {
        Model model = new ModelManager(addressBookWithAliceAndBenson, new UserPrefs());
        String filepath = TEST_DATA_FOLDER + "aliceBensonAddressBook.xml";
        ImportCommand importCommand = prepareCommand(filepath, model);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.importAddressBook(filepath, SecurityUtil.hashPassword(""));
        assertCommandSuccess(importCommand, model, ImportCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_importDuplicatePerson_noChange() throws Exception {
        Model model = new ModelManager(addressBookWithAliceAndBenson, new UserPrefs());
        String filepath = TEST_DATA_FOLDER + "aliceAddressBook.xml";
        ImportCommand importCommand = prepareCommand(filepath, model);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.importAddressBook(filepath, SecurityUtil.hashPassword(""));
        assertCommandSuccess(importCommand, model, ImportCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_validAddressBookFile_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        String filepath = TEST_DATA_FOLDER + "aliceBensonAddressBook.xml";
        ImportCommand importCommand = prepareCommand(filepath, model);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // import -> address book imported
        importCommand.execute();
        undoRedoStack.push(importCommand);

        // undo -> reverts address book back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> address book imported again
        expectedModel.importAddressBook(filepath, SecurityUtil.hashPassword(""));
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidAddressBookFile_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        String filepath = TEST_DATA_FOLDER + "invalidFileFormatAddressBook.xml";
        ImportCommand importCommand = prepareCommand(filepath, model);

        // execution failed -> importCommand not pushed into undoRedoStack
        assertCommandFailure(importCommand, model, importCommand.MESSAGE_DATA_CONVERSION_ERROR);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void executeUndoRedo_nonExistentAddressBookFile_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        String filepath = TEST_DATA_FOLDER + "nonExistentAddressBook.xml";
        ImportCommand importCommand = prepareCommand(filepath, model);

        // execution failed -> importCommand not pushed into undoRedoStack
        assertCommandFailure(importCommand, model, importCommand.MESSAGE_FILE_NOT_FOUND);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals_sameValues_true() {
        ImportCommand commandWithSameValues = prepareCommand(TEST_DATA_FILE_ALICE_BENSON, model);
        assertTrue(standardCommand.equals(commandWithSameValues));
    }

    @Test
    public void equals_sameObject_true() {
        assertTrue(standardCommand.equals(standardCommand));
    }

    @Test
    public void equals_sameType_true() {
        assertTrue(standardCommand.equals(new ImportCommand(TEST_DATA_FILE_ALICE_BENSON, TEST_PASSWORD)));
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
     * Returns a {@code ImportCommand} with the parameter {@code filepath} with password as TEST_PASSWORD.
     */
    private ImportCommand prepareCommand(String filepath, Model model) {
        return prepareCommand(filepath, model, TEST_PASSWORD);
    }

    /**
     * Returns a {@code ImportCommand} with the parameter {@code filepath} and {@code password}.
     */
    private ImportCommand prepareCommand(String filepath, Model model, String password) {
        ImportCommand importCommand = new ImportCommand(filepath, password);
        importCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return importCommand;
    }

}
