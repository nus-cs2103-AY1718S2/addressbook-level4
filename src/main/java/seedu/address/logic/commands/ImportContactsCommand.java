//@@author luca590

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

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

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.DateAdded;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;


/**
 * Imports contacts from a .csv file
 *
 * https://www.callicoder.com/java-read-write-csv-file-apache-commons-csv/
 * and https://github.com/callicoder/java-read-write-csv-file
 */
public final class ImportContactsCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "importcontacts";
    public static final String COMMAND_ALIAS = "ic";
    public static final String MESSAGE_SUCCESS = "Contacts successfully imported.\n";
    public static final String MESSAGE_FILE_SUCCESS_OPEN = "File was successfully opened.\n";
    public static final String MESSAGE_FILE_FAILED_OPEN =
            "File failed to open. Please try a different address "
                    + "or check if file may be corrupt.\n";
    public static final String MESSAGE_FILE_NOT_FOUND = "No file was found at the address provided. "
            + "Please provide anotehr address.\n";
    public static final String MESSAGE_NO_ADDRESS =
            "No address was provided, please provide an address to a csv, "
                    + "from which to import the file\n";

    private String fileAddress;
    private CSVParser csvParser;

    /**
     * Takes in an address to set to the global, private variable 'fileAddress'
     * checks to make sure the address passed in is non-null and trims whitespace
     *
     * @param fileAddressArg is the the file address passed into constructor
     *                       fileAddress is set to fileAddressArg after taking out
     *                       whitespaces and checking it is non-null
     * @throws Exception if fileAddressArg is null
     */
    public ImportContactsCommand(final String fileAddressArg) throws Exception {
        try {
            requireNonNull(fileAddressArg);
        } catch (Exception e) {
            throw new Exception("Address passed to "
                    + "ImportContactsCommand constructor is null");
        }
        fileAddress = fileAddressArg.trim();
    }

    /**
     * Function to isolate opening a file. If there is a problem with user input
     * will likely be caught by this function
     *
     * @return CommandResult object if successful
     * @throws Exception if cannot open file path, in this case
     * there is likely a problem with the user input file path
     */
    public CommandResult openFile() throws Exception {
        Reader reader;
        try {
            reader = Files.newBufferedReader(Paths.get(fileAddress));
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommandException("Reader failed in openFile()\n");
        }
        csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .withIgnoreHeaderCase()
                    .withTrim());
        return new CommandResult(MESSAGE_FILE_SUCCESS_OPEN
                + "from : " + fileAddress);
    }

    /**
     * Prints to the console as Persons are imported
     * Important for users to verify all correct contacts have been imported,
     * and helpful for debugging
     *
     * @param n is person's name
     * @param e is person's email
     * @param p is person's phone
     * @param a is person's address
     */
    public void printResult(final String n, final String e, final String p, final String a) {
        System.out.println("---------------");
        System.out.println("Name : " + n);
        System.out.println("Email : " + e);
        System.out.println("Phone : " + p);
        System.out.println("Address : " + a);
        System.out.println("---------------\n\n");
    }

    /**
     * Creates and writes Person contacts referenced by the csvRecords iterable
     *
     * The below parameters are declared in executeUndoableCommand
     * and initialized in writeContactsToFile
     * @param name
     * @param email
     * @param phone
     * @param address
     * @param addDate
     * @param formatter
     * @param tagSet
     * @param date
     * @param personToAdd
     * @param csvRecords
     * @throws CommandException
     */
    private void writeContactsToFile(String name, String email, String phone, String address,
                                     DateAdded addDate, SimpleDateFormat formatter, Set<Tag> tagSet,
                                     Date date, Person personToAdd, Iterable<CSVRecord> csvRecords)
            throws CommandException {
        for (CSVRecord csvRecord : csvRecords) {
            name = csvRecord.get("Name");
            email = csvRecord.get("Email");
            phone = csvRecord.get("Phone");
            address = csvRecord.get("Address");
            addDate = new DateAdded(formatter.format(date));

            printResult(name, email, phone, address);

            tagSet.add(new Tag("nonclient"));

            personToAdd = new Person(new Name(name),
                    new Phone(phone), new Email(email),
                    new Address(address), addDate, tagSet);

            try {
                requireNonNull(model);
            } catch (Exception e) {
                throw new CommandException("Model is null in ImportContactsCommand"
                        + " -> executeUndoableCommand");
            }

            try {
                model.addPerson(personToAdd);
                model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
            } catch (Exception e) {
                throw new CommandException("Failed to add person in ImportContactsCommand, execute()\n"
                        + e);
            }

        }
    }

    /**
     * Checks conditions before calling writeContactsToFile
     * initializes variables to null that are set in for-loop in writeContactsToFile
     *
     * @return CommandResult with success message if no exception is thrown
     * @throws CommandException if file cannot be opened (openFile will throw exception first)
     * or (more importantly) if Persons cannot be added to model
     */
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        Person personToAdd = null;
        String name = null;
        String email = null;
        String phone = null;
        String address = null;
        Set<Tag> tagSet = new HashSet<>();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        DateAdded addDate = null;

        try {
            System.out.println("Opening... " + fileAddress);
            openFile();
        } catch (Exception e) {
            throw new CommandException("We were not able to open your file.\n"
                    + "Make sure the file path contains \".csv\" at the end and is not in quotations.");
        }

        try {
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            requireNonNull(csvRecords);

            writeContactsToFile(name, email, phone, address, addDate,
                    formatter, tagSet, date, personToAdd, csvRecords);

            return new CommandResult(MESSAGE_SUCCESS);
        } catch (IOException ioe) {
            throw new CommandException(
                    "IOException caught in executeUndoableCommand in ImportContactsCommand");
        }
    }

    public String getFileAddress() {
        return fileAddress;
    }
}
//@@author
