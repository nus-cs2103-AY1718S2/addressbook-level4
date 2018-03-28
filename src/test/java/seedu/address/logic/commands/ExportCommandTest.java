package seedu.address.logic.commands;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalEvents.getTypicalEventBook;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.ExistingFileException;
import seedu.address.commons.exceptions.InvalidFileException;
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
import seedu.address.storage.UserPrefsStorage;
import seedu.address.storage.XmlAddressBookStorage;

public class ExportCommandTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private Storage storage;
    private Model model;

    @Before
    public void setUp() {
        AddressBookStorage addressBookStorage = new XmlAddressBookStorage(getFilePath("addressbook.xml"));
        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getFilePath("preferences.json"));
        storage = new StorageManager(addressBookStorage, userPrefsStorage);
        model = new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), new UserPrefs());
    }

    @Test
    public void execute_validFilePath_success() {
        String filePath = getFilePath("validXmlExport.xml");
        ExportCommand command = prepareCommand(filePath);
        String expectedMessage = String.format(ExportCommand.MESSAGE_EXPORT_SUCCESS, filePath);

        assertCommandSuccess(command, expectedMessage, filePath);
    }

    @Test
    public void execute_invalidFileExtension_throwsCommandException() {
        String filePath = getFilePath("invalidXmlExport.csv");
        ExportCommand command = prepareCommand(filePath);
        String expectedMessage = String.format(ExportCommand.MESSAGE_NOT_XML_FILE);

        assertCommandFailure(command, expectedMessage, filePath);
    }

    @Test
    public void execute_existingName_throwsCommandException() {
        String filePath = getFilePath("existingXmlFile.xml");
        ExportCommand command = prepareCommand(filePath);
        try {
            storage.exportAddressBook(model.getAddressBook(), "existingXmlFile.xml");
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
            assertEquals(model.getAddressBook(), new AddressBook(storage.readAddressBook(filePath).get()));
        } catch (CommandException | DataConversionException | IOException e) {
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
