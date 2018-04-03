//@@author luca590

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
//@@author
