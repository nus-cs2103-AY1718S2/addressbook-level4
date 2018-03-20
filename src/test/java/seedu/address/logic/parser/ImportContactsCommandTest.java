package seedu.address.logic.parser;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.ImportContactsCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.UniquePersonListTest;
import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.Assert;
import seedu.address.testutil.EditPersonDescriptorBuilder;

import seedu.address.testutil.PersonBuilder;

import seedu.address.model.person.Person;


import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertFalse;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;


public class ImportContactsCommandTest {

    private FindCommandParser parser = new FindCommandParser();
    private static ImportContactsCommand importValidPath;
    private static ImportContactsCommand importIllegalPath;
    private static ImportContactsCommandParser imparse;

    static {
        try {
            importValidPath = new ImportContactsCommand("/Users/lucasgaylord/Desktop/Test_contacts.csv");
            importIllegalPath = new ImportContactsCommand("...");

            imparse = new ImportContactsCommandParser();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //featureUnderTest_testScenario_expectedBehavior()
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void execute_validPathToTestFileWithoutPerson_printCSVContents() throws IOException {
        Reader reader = Files.newBufferedReader(Paths.get("/Users/lucasgaylord/Desktop/Test_contacts.csv"));

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

            importValidPath.printResult(name, email, phone, address);
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

        UniquePersonList upl = new UniquePersonList();

        try {
//            addMe.executeUndoableCommand();

            upl.add(personToAdd);
        //AddCommand addMe = new AddCommand(personToAdd); //not the most efficient...
        //addMe.executeUndoableCommand();

        } catch (Exception e) {
            throw new CommandException("Failed to add person in ImportContactsCommand, execute()\n"
            + e);
        }

    }

    @Test
    public void parse_inputCorrectPath_returnsImportContactsCommand() throws Exception {
        imparse.parse("/Users/lucasgaylord/Desktop/Test_contacts.csv");
    }

    @Test
    public void openFile_inputCorrectPath_returnsCommandResult() throws Exception {
        importValidPath.openFile();
    }

    @Test
    public void openFile_inputIllegalPath_throwsException() throws Exception { //what type of exception
        thrown.expect(CommandException.class);
        importIllegalPath.openFile();
    }

    @Test
    public void printResult_giveValidInputs_printsToConsole() throws Exception {
        importValidPath.printResult("First Last", "last@gmail.com", "+12 3456 7891", "123 address st.");
    }

    @Test
    public void execute_inputValidFileAddress_addsPersonsToAddressBook() throws CommandException {
        importValidPath.executeUndoableCommand();
    }

    @Test
    public void execute_inputIllegalFileAddress_throwException() { //what type of exception

    }

}
