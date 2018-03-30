package seedu.address.logic.commands;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import java.util.Iterator;
import javafx.collections.ObservableList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;


import static java.util.Objects.requireNonNull;


public class ExportContactsCommand extends UndoableCommand {

    public static final String SUCCESS = "Contacts successfully exported.\n";

    public static final String COMMAND_WORD = "export_contacts";
    public static final String COMMAND_ALIAS = "ec";

    private Path WRITE_TO_PATH; // This path must include filename at end

    public ExportContactsCommand() {
        System.out.println("Contstructor called without argument");
        WRITE_TO_PATH = getDefaultPath();
        System.out.println(WRITE_TO_PATH);
    }

    public ExportContactsCommand(String filePath) {
        System.out.println("Contstructor called WTIH argument");
        requireNonNull(filePath);
        WRITE_TO_PATH = FileSystems.getDefault().getPath(filePath.trim());
    }

    /*
     * Takes a valid input file (if invalid getCsvToWriteTo will throw error)
     * and loops through the Persons in current address book, exporting
     * each one to file specified
     */
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        CSVPrinter csvPrinter;

        //Write file to path and specify name
        try {
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

        } catch (IOException ioe) {
            throw new CommandException("Failed in exporting Persons.\n"
                    + ioe.getStackTrace());
        }

        return new CommandResult(SUCCESS);
    }

    /**
     * @return gives the csv file to write to. The exported contacts will be in this file
     */
    public CSVPrinter getCsvToWriteTo() throws IOException {
        CSVPrinter csvPrinter;

        try {
            System.out.println("File path is: " + WRITE_TO_PATH);
            BufferedWriter writer = Files.newBufferedWriter(WRITE_TO_PATH);
            csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                    .withHeader("Name", "Email", "Phone", "Address"));
        } catch (IOException ioe) {
            throw new IOException("Failed to create writable path - in getCsvToWriteTo");
        }

        return csvPrinter;
    }

    public Path getDefaultPath() {
        Path defaultPath = FileSystems.getDefault().getPath("data/exportToExisting.csv");
        return defaultPath;
    }

    public Path getWRITE_TO_PATH() {
        return WRITE_TO_PATH;
    }
}
