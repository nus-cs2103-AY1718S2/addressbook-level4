package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
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
import seedu.address.model.person.*;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;


/**
 * This class borrows from.
 *https://www.callicoder.com/java-read-write-csv-file-apache-commons-csv/
 *and https://github.com/callicoder/java-read-write-csv-file
*/
public final class ImportContactsCommand extends UndoableCommand {

    /**
     * Self explanitory.
     */
    public static final String COMMAND_WORD = "import_contacts";
    /**
     * Self explanitory.
     */
    public static final String COMMAND_ALIAS = "ic";
    /**
     * Self explanitory.
     */
    public static final String MESSAGE_SUCCESS =
            "Contacts successfully imported.\n";
    /**
     * Self explanitory.
     */
    public static final String MESSAGE_FILE_SUCCESS_OPEN =
            "File was successfully opened.\n";
    /**
     * Self explanitory.
     */
    public static final String MESSAGE_FILE_FAILED_OPEN =
            "File failed to open. Please try a different address "
            + "or check if file may be corrupt.\n";
    /**
     * Self explanitory.
     */
    public static final String MESSAGE_FILE_NOT_FOUND =
            "No file was found at the address provided. "
            + "Please provide anotehr address.\n";
    /**
     * Self explanitory.
     */
    public static final String MESSAGE_NO_ADDRESS =
            "No address was provided, please provide an address to a csv, "
            + "from which to import the file\n";


    /**
     * Self explanitory.
     */
    private String fileAddress;
    /**
     * Self explanitory.
     */
    private CSVParser csvParser;


    /**
     * Self explanitory.
     */
    public ImportContactsCommand(final String fa) throws Exception {
        //This throws IOException if _fileAddress is null
        try {
            requireNonNull(fa);
        } catch (Exception e) {
            throw new Exception("Address passed to "
                    + "ImportContactsCommand constructor is null");
        }
        fileAddress = fa;
    }

    /**
     * Tries to open csv file, throws exception if problem
     */
    public CommandResult openFile() throws Exception {
        Reader reader;
        try {
            reader = Files.newBufferedReader(Paths.get(fileAddress));
        } catch (Exception e) {
            throw new CommandException("Reader failed in openFile()");
        }

        try {
            csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .withIgnoreHeaderCase()
                    .withTrim());
            return new CommandResult(MESSAGE_FILE_SUCCESS_OPEN
                    + "from : " + fileAddress);
        } catch (NullPointerException npe) { //file won't open, null ptr
            throw new CommandException(MESSAGE_FILE_FAILED_OPEN);
        } catch (FileNotFoundException fnf) {
            throw new CommandException(MESSAGE_FILE_NOT_FOUND);
        } catch (IOException ioe) {
            throw new CommandException("IOException thrown in "
                    + "ImportContactsCommand, openFile.\n" + MESSAGE_FILE_FAILED_OPEN);
        }
    }

    /**
     * Self explanitory.
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

    /**
     * If file has been opened successfully, it iterates
     * over the rows of the csv after finding the headers
     * "Name", "Email", etc
     * Makes a Person out of each row
     * calls executeUndoableCommand() on new Person
     */
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        Person personToAdd;
        String name;
        String email;
        String phone;
        String address;
        Set<Tag> tagSet = new HashSet<>();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        DateAdded addDate;

        try {
            openFile(); //open the file to add users
        } catch (Exception e) {
            throw new CommandException("Cannot open file in execute");
        }

        try {
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            requireNonNull(csvRecords);

            for (CSVRecord csvRecord : csvRecords) { //iterate through the
                // Accessing values by Header names
                try {
                    name = csvRecord.get("Name");
                    email = csvRecord.get("Email");
                    phone = csvRecord.get("Phone");
                    address = csvRecord.get("Address");
                    addDate = new DateAdded(formatter.format(date));
                } catch (Exception e) {
                    throw new CommandException("csvRecord failed. Check csv file to make sure" +
                            "fields are separated correctly along with headers\n" +
                            e);
                }

                printResult(name, email, phone, address); //mainly for debugging

                tagSet.add(new Tag("friend")); //temporary tag, fix later

                personToAdd = new Person(new Name(name),
                        new Phone(phone), new Email(email),
                        new Address(address), addDate, tagSet);

                UniquePersonList upl = new UniquePersonList(); //need to change this later to get current model


                try {
                    upl.add(personToAdd);

 //                   AddCommand addMe = new AddCommand(personToAdd); //not the most efficient...
 //                   addMe.executeUndoableCommand();

//                requireNonNull(model);
//                try {
//                    model.addPerson(personToAdd);
//                    return new CommandResult(String.format(MESSAGE_SUCCESS, personToAdd));
//                } catch (DuplicatePersonException e) {
//                    throw new CommandException(MESSAGE_DUPLICATE_PERSON);
//                }

                } catch (Exception e) {
                    throw new CommandException("Failed to add person in ImportContactsCommand, execute()\n"
                    + e);
                }

            }
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (IOException ioe) {
            throw new CommandException(
                    "Aw Fuck."); //Obviously need to change this
        } catch (NullPointerException npe) { //file won't open, null ptr
            throw new CommandException(MESSAGE_FILE_FAILED_OPEN);
        }
    }
}
