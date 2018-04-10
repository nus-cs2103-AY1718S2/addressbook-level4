# luca590
###### \java\seedu\address\logic\commands\ExportContactsCommandTest.java
``` java

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
```
###### \java\seedu\address\logic\commands\ImportContactsCommandTest.java
``` java

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.FindCommandParser;
import seedu.address.logic.parser.ImportContactsCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ModelManager;
import seedu.address.model.person.DateAdded;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;



public class ImportContactsCommandTest {
    private static ImportContactsCommandParser imparse;
    private static ImportContactsCommand importValidPath;
    private static ImportContactsCommand importIllegalPath;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();
    private FindCommandParser parser = new FindCommandParser();

    static {
        try {
            importValidPath = new ImportContactsCommand("data/Test_contacts.csv");
            importIllegalPath = new ImportContactsCommand("aaa");

            imparse = new ImportContactsCommandParser();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Used for debugging
     */
    public void printResult(final String n, final String e,
                            final String p, final String a) {
        System.out.println("---------------");
        System.out.println("Name : " + n);
        System.out.println("Email : " + e);
        System.out.println("Phone : " + p);
        System.out.println("Address : " + a);
        System.out.println("---------------\n\n");
    }

    //featureUnderTest_testScenario_expectedBehavior()

    @Test(expected = Exception.class)
    public void constructor_passingNullFileAddress_throwException() throws Exception {
        final String x = null;
        ImportContactsCommand a = new ImportContactsCommand(x);
    }

    @Test(expected = Exception.class)
    public void executeUndoableCommand_giveIllegalPath_throwException() throws Exception {
        ImportContactsCommand a = new ImportContactsCommand("error");
        a.openFile();
    }

    @Test(expected = Exception.class)
    public void executeUndoableCommand_giveNullModel_throwException() throws Exception {
        importValidPath.model = null;
        importValidPath.executeUndoableCommand();
    }

    @Test(expected = Exception.class)
    public void importContactsCommandParserparse_giveNullArgument_throwException() throws ParseException {
        ImportContactsCommandParser cp = new ImportContactsCommandParser();
        cp.parse(null);
    }

    @Test
    public void addressBookParser_giveCorrectImport_returnImportContactCommand() throws Exception {
        AddressBookParser abp = new AddressBookParser();
        assertNotNull(abp.parseCommand("import_contacts ..."));
        assertNotNull(abp.parseCommand("ic ..."));
    }

    @Test
    public void execute_validPathToTestFile_printCsvContents() throws IOException {
        Reader reader = Files.newBufferedReader(Paths.get("data/Test_contacts.csv"));

        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withIgnoreHeaderCase()
                .withTrim());

        Iterable<CSVRecord> csvRecords = csvParser.getRecords();
        requireNonNull(csvRecords);

        String name = null;
        String email = null;
        String phone = null;
        String address = null;

        for (CSVRecord csvRecord : csvRecords) { //iterate through the
            // Accessing values by Header names
            name = csvRecord.get("Name");
            email = csvRecord.get("Email");
            phone = csvRecord.get("Phone");
            address = csvRecord.get("Address");

            printResult(name, email, phone, address);
        }
    }

    @Test
    public void execute_validPersonToAdd_addPeople() throws CommandException {
        PersonBuilder pb = new PersonBuilder();
        Person personToAdd = pb.build();

        requireNonNull(personToAdd);

        String name;
        String email;
        String phone;
        String address;
        Set<Tag> tagSet = new HashSet<>();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        DateAdded addDate;

        //initialize UniquePersonList for test case
        UniquePersonList upl = new UniquePersonList();

        try {
            upl.add(personToAdd);
        } catch (Exception e) {
            throw new CommandException("Failed to add person in ImportContactsCommand, execute()\n"
                    + e);
        }

    }

    @Test
    public void parse_inputCorrectPath_returnsImportContactsCommandWithCorrectFilePath() throws Exception {
        ImportContactsCommand icc = imparse.parse("data/Test_contacts.csv");
        assertEquals(icc.getFileAddress(), "data/Test_contacts.csv");
    }

    @Test
    public void openFile_inputCorrectPath_returnsCommandResult() throws Exception {
        ImportContactsCommand icc = new ImportContactsCommand("data/Test_contacts.csv");
        CommandResult cr = icc.openFile();
        requireNonNull(cr);
        System.out.println(cr.equals(importValidPath.openFile()));
    }

    @Test
    public void openFile_inputIllegalPath_throwsException() throws Exception { //what type of exception
        thrown.expect(CommandException.class);
        importIllegalPath.openFile();
    }


    /*
     * This test will fail if contacts are imported from a file containing
     * different contacts
     */
    @Test
    public void execute_inputValidFileAddress_addsPersonsToAddressBook() throws CommandException {
        importValidPath.model = new ModelManager();
        importValidPath.executeUndoableCommand();
        ObservableList<Person> plist = importValidPath.model.getFilteredPersonList();

        requireNonNull(importValidPath.model);
        assertFalse(plist.isEmpty());
        Object[] parray = plist.toArray();

        //substrings are used in testing to avoid the Date Added parameter
        //Date Added changes everyday (not surprising), so cannot statically type it
        assertEquals(parray[1].toString().substring(0, 65),
                "Bob Phone: 32134558 Email: bob@badasbob.com Address: "
                        + "324 green st");
        assertEquals(parray[2].toString().substring(0, 65), "Carol Phone: 91442333 Email: "
                + "carol@carlcaronna.com Address: 509 b");
        assertEquals(parray[3].toString().substring(0, 65), "Dave Phone: 77004352 Email: "
                + "dave@doggod.com Address: 409 yellow s");
        assertEquals(parray[4].toString().substring(0, 65), "Edward Phone: 15432349 Email: "
                + "ed@edible.com Address: 909 grey st ");
    }
}
```
