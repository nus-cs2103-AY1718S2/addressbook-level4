package seedu.address.logic.commands;

import static org.junit.Assert.*;

import org.apache.commons.csv.CSVFormat;
import org.junit.*;

import java.io.IOException;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.Arrays;

import org.apache.commons.csv.CSVPrinter;

import org.junit.rules.ExpectedException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ExportContactsCommandParser;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.PersonBuilder;

public class ExportContactsCommandTest {

    public static final String VALID_NEW_FILE_PATH = "data/exportToNew.csv";
    public static final String VALID_EXISTING_FILE_PATH = "data/exportTo.csv";

    //featureUnderTest_testScenario_expectedBehavior()

    ExportContactsCommand exportDefaultPath = new ExportContactsCommand(); //test later
    ExportContactsCommand exportExistingPath = new ExportContactsCommand(VALID_EXISTING_FILE_PATH); //test later
    ExportContactsCommand exportNewPath = new ExportContactsCommand(VALID_NEW_FILE_PATH);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void exportCommandParse_giveValidArguments_returnExportContactCommand() throws Exception {
        ExportContactsCommandParser eccp = new ExportContactsCommandParser();
        ExportContactsCommand a = eccp.parse();
        ExportContactsCommand b = eccp.parse("data/exportTo");

        assertNotNull(a);
        assertNotNull(b);
    }

    @Test
    public void getDefaultPath_callWithoutArgs_returnsCorrectString() throws Exception {
        Path x = exportDefaultPath.getDefaultPath();
        assertEquals(x.toString(), "data/exportTo.csv");
    }

    @Test
    public void getCSVToWriteTo_workingDirectoryNewFile_noExceptionThrown() throws IOException {
        CSVPrinter csvpDefault = null;
        CSVPrinter csvpNew = null;
        CSVPrinter csvpExisting = null;

        try {
            csvpNew = exportNewPath.getCSVToWriteTo();
            csvpDefault = exportNewPath.getCSVToWriteTo();
            csvpExisting = exportNewPath.getCSVToWriteTo();
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
        assertNotNull(path);
    }

    @Test
    public void executeUndoableCommand_validNewFilePath_createsNewFileAndWritesToIt() throws
            CommandException, DuplicatePersonException {
        CommandResult cr = null;
        exportExistingPath.model = new ModelManager();
        PersonBuilder pb = new PersonBuilder();
        exportExistingPath.model.addPerson(pb.build());

        try {
            cr = exportNewPath.executeUndoableCommand();
        } catch (CommandException e) {
            e.printStackTrace();
        }

        assertEquals(cr.feedbackToUser, "Contacts successfully exported.\n");
    }

    @Test
    public void executeUndoableCommand_validExistingFilePath_writesToExistingFile() throws
            CommandException, DuplicatePersonException {
        CommandResult cr = null;
        exportExistingPath.model = new ModelManager();
        PersonBuilder pb = new PersonBuilder();
        exportExistingPath.model.addPerson(pb.build());

        try {
            cr = exportExistingPath.executeUndoableCommand();
        } catch (CommandException e) {
            e.printStackTrace();
        }

        assertEquals("Contacts successfully exported.\n", cr.feedbackToUser);
    }

    @Test
    public void writingToValidPath_newFileNameAtCurrentPath_createsNewFileAndWritesContacts() throws Exception {
        String SAMPLE_CSV_FILE = "./sample.csv";

        try (
                BufferedWriter writer = Files.newBufferedWriter(Paths.get(SAMPLE_CSV_FILE));

                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                        .withHeader("ID", "Name", "Designation", "Company"));
        ) {
            csvPrinter.printRecord("1", "Sundar Pichai ♥", "CEO", "Google");
            csvPrinter.printRecord("2", "Satya Nadella", "CEO", "Microsoft");
            csvPrinter.printRecord("3", "Tim cook", "CEO", "Apple");

            csvPrinter.printRecord(Arrays.asList("4", "Mark Zuckerberg", "CEO", "Facebook"));

            csvPrinter.flush();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
