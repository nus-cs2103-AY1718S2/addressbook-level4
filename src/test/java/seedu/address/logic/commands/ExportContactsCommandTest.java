package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import java.nio.file.Path;

import org.apache.commons.csv.CSVPrinter;

import org.junit.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.ExportContactsCommandParser;
import seedu.address.model.ModelManager;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.PersonBuilder;

public class ExportContactsCommandTest {

    public static final String VALID_NEW_FILE_PATH = "data/exportToNew.csv";
    public static final String VALID_EXISTING_FILE_PATH = "data/exportToExisting.csv";

    //featureUnderTest_testScenario_expectedBehavior()

    private ExportContactsCommand exportDefaultPath = new ExportContactsCommand();
    private ExportContactsCommand exportExistingPath = new ExportContactsCommand(VALID_EXISTING_FILE_PATH);
    private ExportContactsCommand exportNewPath = new ExportContactsCommand(VALID_NEW_FILE_PATH);

    @Test
    public void exportCommandParse_giveValidArguments_returnCorrectExportContactCommandPath() throws Exception {
        ExportContactsCommandParser eccp = new ExportContactsCommandParser();
        ExportContactsCommand a = eccp.parse("");
        ExportContactsCommand b = eccp.parse("exampleFile.csv");

        assertEquals(a.getWriteToPath().toString(), "data/exportToExisting.csv");
        assertEquals(b.getWriteToPath().toString(), "exampleFile.csv");
    }

    @Test
    public void addressBookParser_inputCommandAndAlias_returnsExportContactsCommand() throws Exception {
        AddressBookParser ap = new AddressBookParser();
        ExportContactsCommand e1 = (ExportContactsCommand) ap.parseCommand ("ec");
        ExportContactsCommand e2 = (ExportContactsCommand) ap.parseCommand("export_contacts");

        assertNotNull(e1);
        assertNotNull(e2);
    }

    @Test (expected = Exception.class)
    public void executeUndoableCommand_invalidPath_throwException() throws Exception {
        ExportContactsCommand e = new ExportContactsCommand("...");
        e.getCsvToWriteTo();
        e.executeUndoableCommand();
    }

    @Test (expected = Exception.class)
    public void executeUndoableCommand_nullModel_throwException() throws Exception {
        ExportContactsCommand e = new ExportContactsCommand("...");
        e.model = null;
        e.executeUndoableCommand();
    }

    @Test
    public void getDefaultPath_callWithoutArgs_returnsCorrectString() throws Exception {
        Path x = exportDefaultPath.getDefaultPath();
        assertEquals(x.toString(), "data/exportToExisting.csv");
    }

    @Test
    public void getCsvToWriteTo_workingDirectoryNewFile_noExceptionThrown() throws IOException {
        CSVPrinter csvpDefault = null;
        CSVPrinter csvpNew = null;
        CSVPrinter csvpExisting = null;

        try {
            csvpDefault = exportDefaultPath.getCsvToWriteTo();
            csvpNew = exportNewPath.getCsvToWriteTo();
            csvpExisting = exportExistingPath.getCsvToWriteTo();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertNotNull(csvpDefault);
        assertNotNull(csvpNew);
        assertNotNull(csvpExisting);
    }

    @Test
    public void getDefaultPath_noPathGiven_throwsNoExceptionsAndReturnsPath() {
        Path path = null;
        path = exportDefaultPath.getDefaultPath();
        assertEquals(path.toString(), "data/exportToExisting.csv");
    }

    @Test
    public void executeUndoableCommand_validNewFilePath_createsNewFileAndWritesToIt() throws
            CommandException, DuplicatePersonException, IOException {
        CommandResult cr = null;
        exportNewPath.model = new ModelManager();
        PersonBuilder pb = new PersonBuilder();
        exportNewPath.model.addPerson(pb.build());

        try {
            cr = exportNewPath.executeUndoableCommand();
        } catch (CommandException e) {
            e.printStackTrace();
        }

        assertEquals(cr.feedbackToUser, "Contacts successfully exported.\n");
    }

    @Test
    public void executeUndoableCommand_validExistingFilePath_writesToExistingFile() throws
            CommandException, DuplicatePersonException, IOException {
        CommandResult cr = null;
        exportExistingPath.model = new ModelManager();
        PersonBuilder pb = new PersonBuilder();
        exportExistingPath.model.addPerson(pb.build());

        try {
            cr = exportExistingPath.executeUndoableCommand();
        } catch (CommandException e) {
            System.out.print("Error in executeUndoableTest");
            e.printStackTrace();
        }

        assertEquals("Contacts successfully exported.\n", cr.feedbackToUser);
    }

    @Test
    public void executeUndoableCommand_validDefaultFilePath_writesToDefaultFile() throws
            CommandException, DuplicatePersonException, IOException {
        CommandResult cr = null;
        exportDefaultPath.model = new ModelManager();
        PersonBuilder pb = new PersonBuilder();
        exportDefaultPath.model.addPerson(pb.build());

        try {
            cr = exportDefaultPath.executeUndoableCommand();
        } catch (CommandException e) {
            e.printStackTrace();
        }

        assertEquals("Contacts successfully exported.\n", cr.feedbackToUser);
    }
}
