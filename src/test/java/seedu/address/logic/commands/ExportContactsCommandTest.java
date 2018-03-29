package seedu.address.logic.commands;

import static org.junit.Assert.*;

import org.apache.commons.csv.CSVFormat;
import org.junit.*;

import java.io.IOException;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import org.apache.commons.csv.CSVPrinter;

import org.junit.rules.ExpectedException;
import seedu.address.logic.commands.exceptions.CommandException;

public class ExportContactsCommandTest {

    public static final String VALID_NEW_FILE_PATH = "./writeToThisFile.csv";
    public static final String VALID_EXISTING_FILE_PATH = "~/Desktop/testContacts.csv";

    //featureUnderTest_testScenario_expectedBehavior()

    ExportContactsCommand exportDefaultPath = new ExportContactsCommand(); //test later
    ExportContactsCommand exportExistingPath = new ExportContactsCommand(VALID_EXISTING_FILE_PATH); //test later
    ExportContactsCommand exportNewPath = new ExportContactsCommand(VALID_NEW_FILE_PATH);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getCSVToWriteTo_workingDirectoryNewFile_noExceptionThrown() throws IOException {
        CSVPrinter csvp = null;

        try {
            csvp = exportNewPath.getCSVToWriteTo();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertNotNull(csvp);
    }

    @Test
    public void getDefaultPath_noPathGiven_throwsNoExceptionsAndReturnsPath() {
        String path = null;
        path = exportDefaultPath.getDefaultPath();
        assertNotNull(path);
    }

    @Test
    public void executeUndoableCommand_validNewFilePath_createsNewFileAndWritesToIt() throws CommandException {
        CommandResult cr = null;
        try {
            cr = exportNewPath.executeUndoableCommand();
        } catch (CommandException e) {
            e.printStackTrace();
        }

        assertEquals(cr.toString(), "Contacts successfully exported.\n");
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
