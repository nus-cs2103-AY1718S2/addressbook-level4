# luca590
###### \java\seedu\address\logic\commands\ExportContactsCommand.java
``` java

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Iterator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import javafx.collections.ObservableList;

import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;

/**
 * Class provides main functionality for ExportContactsCommand,
 * specifically executeUndoableCommand() provides main functionality
 */
public class ExportContactsCommand extends UndoableCommand {

    public static final String SUCCESS = "Contacts successfully exported.\n";

    public static final String COMMAND_WORD = "export_contacts";
    public static final String COMMAND_ALIAS = "ec";

    private Path writeToPath; // This path must include filename at end

    /*
     * Constructor, one constructor takes no arguments (default file path)
     * The other constructor takes the file path provided by user as argument
     */
    public ExportContactsCommand() {
        //System.out.println("Contstructor called without argument");
        writeToPath = getDefaultPath();
        System.out.println(writeToPath);
    }

    public ExportContactsCommand(String filePath) {
        //System.out.println("Contstructor called WTIH argument");
        requireNonNull(filePath);
        writeToPath = FileSystems.getDefault().getPath(filePath.trim());
    }

    /*
     * Takes a valid input file (if invalid getCsvToWriteTo will throw error)
     * and loops through the Persons in current address book, exporting
     * each one to file specified
     */
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        CSVPrinter csvPrinter;

        //Write file to path and specify name
        try {
            requireNonNull(model);
            csvPrinter = getCsvToWriteTo();
        } catch (java.lang.Exception e) {
            throw new CommandException(e.getMessage());
        }

        try {
            ReadOnlyAddressBook myBook = model.getAddressBook();
            ObservableList<Person> myPersonList = myBook.getPersonList();
            Iterator personIterator = myPersonList.iterator();
            //iterator over the Persons in AddressBook and write them to csv
            Person p;
            while (personIterator.hasNext()) {
                p = (Person) personIterator.next();
                csvPrinter.printRecord(p.getName(), p.getEmail(), p.getPhone(), p.getAddress());
            }

            csvPrinter.flush();

        } catch (Exception e) {
            throw new CommandException("Failed in exporting Persons.\n"
                    + e.getStackTrace());
        }

        return new CommandResult(SUCCESS);
    }

    /**
     * @return gives the csv file to write to. The exported contacts will be in this file
     */
    public CSVPrinter getCsvToWriteTo() throws IOException {
        CSVPrinter csvPrinter;

        System.out.println("File path is: " + writeToPath);
        BufferedWriter writer = Files.newBufferedWriter(writeToPath);
        csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                .withHeader("Name", "Email", "Phone", "Address"));

        return csvPrinter;
    }

    public Path getDefaultPath() {
        Path defaultPath = FileSystems.getDefault().getPath("data/exportToExisting.csv");
        return defaultPath;
    }

    public Path getWriteToPath() {
        return writeToPath;
    }
}
```
###### \java\seedu\address\logic\commands\ImportContactsCommand.java
``` java

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
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.Tag;


/**
 * This class borrows from.
 *https://www.callicoder.com/java-read-write-csv-file-apache-commons-csv/
 *and https://github.com/callicoder/java-read-write-csv-file
 */
public final class ImportContactsCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "import_contacts";
    public static final String COMMAND_ALIAS = "ic";
    public static final String MESSAGE_SUCCESS = "Contacts successfully imported.\n";
    public static final String MESSAGE_FILE_SUCCESS_OPEN = "File was successfully opened.\n";
    public static final String MESSAGE_FILE_FAILED_OPEN =
            "File failed to open. Please try a different address "
                    + "or check if file may be corrupt.\n";
    public static final String MESSAGE_FILE_NOT_FOUND = "No file was found at the address provided. "
            + "Please provide anotehr address.\n";
    public static final String MESSAGE_NO_ADDRESS =
            "No address was provided, please provide an address to a csv, from which to import the file\n";
    /*
     * private global variables used for reading CSV
     */
    private String fileAddress;
    private CSVParser csvParser;

    /**
     * Constructor takes file to read (@param fa)
     */
    public ImportContactsCommand(final String fa) throws Exception {
        //This throws IOException if _fileAddress is null
        try {
            requireNonNull(fa);
        } catch (Exception e) {
            throw new Exception("Address passed to "
                    + "ImportContactsCommand constructor is null");
        }
        fileAddress = fa.trim();
    }

    /**
     * Tries to open csv file, throws exception if problem
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
        return new CommandResult(MESSAGE_FILE_SUCCESS_OPEN + "from : " + fileAddress);
    }

    /**
     * Used for printing to console when contacts are added
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
            System.out.println("Opening... " + fileAddress);
            openFile(); //open the file to add users
        } catch (Exception e) {
            throw new CommandException("Cannot open file in executeUndoableCommand in "
                    + "ImportContactsCommand Class\n"
                    + "Make sure the path is not in quotations and contains .csv at the end"
                    + e.getMessage());
        }

        try {
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            requireNonNull(csvRecords);

            for (CSVRecord csvRecord : csvRecords) { //iterate through the
                // Accessing values by Header names
                name = csvRecord.get("Name");
                email = csvRecord.get("Email");
                phone = csvRecord.get("Phone");
                address = csvRecord.get("Address");
                addDate = new DateAdded(formatter.format(date));

                printResult(name, email, phone, address); //mainly for debugging

                tagSet.add(new Tag("friend")); //temporary tag, fix later

                personToAdd = new Person(new Name(name),
                        new Phone(phone), new Email(email),
                        new Address(address), addDate, tagSet);

                UniquePersonList upl = new UniquePersonList(); //need to change this later to get current model

                try {
                    requireNonNull(model);
                } catch (Exception e) {
                    throw new CommandException("Model is null in ImportContactsCommand -> executeUndoableCommand");
                }

                try {
                    model.addPerson(personToAdd);
                    model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
                } catch (Exception e) {
                    throw new CommandException("Failed to add person in ImportContactsCommand, execute()\n"
                            + e);
                }

            }
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
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case ImportContactsCommand.COMMAND_WORD: //import contacts from csv
            return new ImportContactsCommandParser().parse(arguments);

        case ImportContactsCommand.COMMAND_ALIAS:
            return new ImportContactsCommandParser().parse(arguments);
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case ExportContactsCommand.COMMAND_WORD: //export contacts from csv
            return new ExportContactsCommandParser().parse(arguments);

        case ExportContactsCommand.COMMAND_ALIAS:
            return new ExportContactsCommandParser().parse(arguments);
```
###### \java\seedu\address\logic\parser\ExportContactsCommandParser.java
``` java

package seedu.address.logic.parser;

import seedu.address.logic.commands.ExportContactsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Class to parse the export_contacts command
 */
public class ExportContactsCommandParser implements Parser<ExportContactsCommand> {

    public static final String FAILED_TO_PARSE =
            "Failed to parse import_contacts command";

    /**
     * Parses the given {@code String} of arguments in the context of the ExportContactsCommand
     * and returns an ExportContactsCommand object for execution
     * Can recieve 1 or 0 arguments
     */
    @Override
    public ExportContactsCommand parse(String args) throws ParseException {
        System.out.println("args is:" + args);
        args = args.trim();

        return (args.length() > 1) ? new ExportContactsCommand(args) : new ExportContactsCommand();
    }
}
```
###### \java\seedu\address\logic\parser\ImportContactsCommandParser.java
``` java

package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.ImportContactsCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Class to parse the import_contacts command
 */
public class ImportContactsCommandParser implements Parser<ImportContactsCommand> {
    /**
     * Self explanitory.
     */
    public static final String FAILED_TO_PARSE =
            "Failed to parse import_contacts command";

    /**
     * Parses the given {@code String} of arguments in the context of the ImportContactsCommand
     * and returns an ImportContactsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ImportContactsCommand parse(String args) throws ParseException {
        try {
            requireNonNull(args);
            //only 1 argument supported now
            return new ImportContactsCommand(args);
        } catch (Exception e) {
            throw new ParseException(FAILED_TO_PARSE, e);
        }
    }
}
```
