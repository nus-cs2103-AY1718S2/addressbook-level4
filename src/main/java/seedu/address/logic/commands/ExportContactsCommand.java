package seedu.address.logic.commands;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Iterator;
import javafx.collections.ObservableList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;


import static java.util.Objects.requireNonNull;


public class ExportContactsCommand extends UndoableCommand {

    public static Path WRITE_TO_PATH; // This path must include filename at end

    public static final String SUCCESS = "Contacts successfully exported.\n";

    public static final String COMMAND_WORD = "export_contacts";
    public static final String COMMAND_ALIAS = "ec";


    public ExportContactsCommand() {
        WRITE_TO_PATH = getDefaultPath();
        System.out.println(WRITE_TO_PATH);
    }

    public ExportContactsCommand(String filePath) {
        requireNonNull(filePath);
        //check path validity
        WRITE_TO_PATH = FileSystems.getDefault().getPath(filePath);

        //create file if doesn't exist
        
    }

    /*
     * Takes a valid input file (if invalid getCSVToWriteTo will throw error)
     * and loops through the Persons in current address book, exporting
     * each one to file specified
     */
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        CSVPrinter csvPrinter;

        //Write file to path and specify name
        try {
            csvPrinter = getCSVToWriteTo();
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
    public CSVPrinter getCSVToWriteTo() throws IOException {
        CSVPrinter csvPrinter;

        try {
            BufferedWriter writer = Files.newBufferedWriter(WRITE_TO_PATH);
            csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                    .withHeader("Name", "Email", "Phone", "Address"));
        } catch (IOException ioe) {
            throw new IOException("Failed to create writable path. Probably incorrect file path.");
        }

        return csvPrinter;
    }

    public Path getDefaultPath() {
        Path defaultPath = FileSystems.getDefault().getPath("data/exportTo.csv");
        return defaultPath;
    }
}
