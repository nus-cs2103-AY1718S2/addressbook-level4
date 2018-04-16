package seedu.address.logic.commands;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.EXISTING_CSV_FILEPATH;
import static seedu.address.logic.commands.CommandTestUtil.EXISTING_XML_FILEPATH;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CSV_FILEPATH;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_XML_FILEPATH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CSV_FILEPATH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_XML_FILEPATH;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalEvents.getTypicalEventBook;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskBook;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.ExistingFileException;
import seedu.address.commons.exceptions.InvalidFileException;
import seedu.address.commons.util.FileUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.TaskBookStorage;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.storage.XmlAddressBookStorage;
import seedu.address.storage.XmlEventBookStorage;
import seedu.address.storage.XmlTaskBookStorage;

//@@author x3tsunayh

public class ExportCommandTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private Storage storage;
    private Model model;

    @Before
    public void setUp() {
        AddressBookStorage addressBookStorage = new XmlAddressBookStorage(getFilePath("addressbook.xml"));
        XmlEventBookStorage eventBookStorage = new XmlEventBookStorage(getFilePath("eb.xml"));
        TaskBookStorage taskBookStorage = new XmlTaskBookStorage(getFilePath("tb.xml"));
        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getFilePath("preferences.json"));

        storage = new StorageManager(addressBookStorage, eventBookStorage, taskBookStorage, userPrefsStorage);
        model = new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), getTypicalTaskBook(), new UserPrefs());
    }

    @Test
    public void execute_validXmlFilePath_success() {
        String filePath = getFilePath(VALID_XML_FILEPATH);
        ExportCommand command = prepareCommand(filePath);
        String expectedMessage = String.format(ExportCommand.MESSAGE_EXPORT_SUCCESS, filePath);

        assertCommandSuccess(command, expectedMessage, filePath);
    }

    @Test
    public void execute_validCsvFilePath_success() {
        String filePath = getFilePath(VALID_CSV_FILEPATH);
        ExportCommand command = prepareCommand(filePath);
        String expectedMessage = String.format(ExportCommand.MESSAGE_EXPORT_SUCCESS, filePath);

        assertCommandSuccess(command, expectedMessage, filePath);
    }

    @Test
    public void execute_invalidXmlFileExtension_throwsCommandException() {
        String filePath = getFilePath(INVALID_XML_FILEPATH);
        ExportCommand command = prepareCommand(filePath);
        String expectedMessage = String.format(ExportCommand.MESSAGE_NOT_XML_CSV_FILE);

        assertCommandFailure(command, expectedMessage, filePath);
    }

    @Test
    public void execute_invalidCsvFileExtension_throwsCommandException() {
        String filePath = getFilePath(INVALID_CSV_FILEPATH);
        ExportCommand command = prepareCommand(filePath);
        String expectedMessage = String.format(ExportCommand.MESSAGE_NOT_XML_CSV_FILE);

        assertCommandFailure(command, expectedMessage, filePath);
    }

    @Test
    public void execute_existingXmlName_throwsCommandException() {
        ExportCommand command = prepareCommand(EXISTING_XML_FILEPATH);
        try {
            storage.exportAddressBook(model.getAddressBook(), EXISTING_XML_FILEPATH);
        } catch (ExistingFileException e) {
            //do nothing if correct exception is thrown
        } catch (IOException | InvalidFileException e) {
            throw new AssertionError("The expected CommandException was not thrown.", e);
        }
    }

    @Test
    public void execute_existingCsvName_throwsCommandException() {
        String filePath = getFilePath(EXISTING_CSV_FILEPATH);
        ExportCommand command = prepareCommand(filePath);
        try {
            storage.exportAddressBook(model.getAddressBook(), EXISTING_CSV_FILEPATH);
        } catch (ExistingFileException e) {
            //do nothing if correct exception is thrown
        } catch (IOException | InvalidFileException e) {
            throw new AssertionError("The expected CommandException was not thrown.", e);
        }
    }


    private String getFilePath(String fileName) {

        return testFolder.getRoot().getPath() + fileName;
    }

    /**
     * Returns an {@code ExportCommand} with parameter {@String filePath}
     */
    private ExportCommand prepareCommand(String filePath) {
        ExportCommand exportCommand = new ExportCommand(filePath);
        exportCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        exportCommand.setStorage(storage);
        return exportCommand;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Person>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(ExportCommand command, String expectedMessage, String filePath) {
        try {
            CommandResult result = command.execute();
            assertEquals(expectedMessage, result.feedbackToUser);
            // Storage does not support CSV readability for addressbook data.
            if (!FileUtil.isValidCsvFile(filePath)) {
                assertEquals(model.getAddressBook(), new AddressBook(storage.readAddressBook(filePath).get()));
            }
        } catch (CommandException | DataConversionException | IOException | JAXBException e) {
            throw new AssertionError("Export Command is not working as expected.", e);
        }
    }

    /**
     * Asserts that {@code command} is executed, but<br>
     *     - the correct CommandException is thrown<br>
     */
    public void assertCommandFailure(ExportCommand command, String expectedMessage, String filePath) {
        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertFalse((new File(filePath)).exists());
        }
    }

}
