package seedu.address.logic.commands;

import static org.junit.Assert.*;

import org.junit.*;

import java.io.IOException;

import java.nio.file.Path;

import org.apache.commons.csv.CSVPrinter;

import org.junit.rules.ExpectedException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ExportContactsCommandParser;
import seedu.address.model.ModelManager;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.PersonBuilder;

public class ExportContactsCommandTest {

    public static final String VALID_NEW_FILE_PATH = "data/exportToNew.csv";
    public static final String VALID_EXISTING_FILE_PATH = "data/exportToExisting.csv";

    //featureUnderTest_testScenario_expectedBehavior()

    ExportContactsCommand exportDefaultPath = new ExportContactsCommand();
    ExportContactsCommand exportExistingPath = new ExportContactsCommand(VALID_EXISTING_FILE_PATH);
    ExportContactsCommand exportNewPath = new ExportContactsCommand(VALID_NEW_FILE_PATH);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void exportCommandParse_giveValidArguments_returnExportContactCommand() throws Exception {
        ExportContactsCommandParser eccp = new ExportContactsCommandParser();
        ExportContactsCommand a = eccp.parse();
        ExportContactsCommand b = eccp.parse("exampleFile.csv");

        assertEquals(a.getWRITE_TO_PATH().toString(), "data/exportToExisting.csv");
        assertEquals(b.getWRITE_TO_PATH().toString(), "exampleFile.csv");
    }

    @Test
    public void getDefaultPath_callWithoutArgs_returnsCorrectString() throws Exception {
        Path x = exportDefaultPath.getDefaultPath();
        assertEquals(x.toString(), "data/exportToExisting.csv");
    }

    @Test
    public void getCSVToWriteTo_workingDirectoryNewFile_noExceptionThrown() throws IOException {
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
