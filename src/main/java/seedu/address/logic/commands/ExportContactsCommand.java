//@@author luca590

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.BufferedWriter;
import java.io.File;
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
 * Provides main functionality for ExportContactsCommand,
 * specifically executeUndoableCommand() is the primary function
 */
public class ExportContactsCommand extends UndoableCommand {

    public static final String FS = File.separator;
    public static final String MESSAGE_SUCCESS = "Contacts successfully exported.\n";
    public static final String COMMAND_WORD = "exportcontacts";
    public static final String COMMAND_ALIAS = "ec";

    private Path writeToPath;

    public ExportContactsCommand() {
        writeToPath = getDefaultPath();
        System.out.println(writeToPath);
    }

    public ExportContactsCommand(String filePath) {
        requireNonNull(filePath);
        writeToPath = FileSystems.getDefault().getPath(filePath.trim());
    }

    /**
     * Returns a CommandResult object which will be notify the calling function
     * of the status of execution (success or fail)
     *
     * @return CommandResult if function is successfully executed and throws no error
     * @throws CommandException if csvPrinter is unable to write to file
     */
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        CSVPrinter csvPrinter;

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

            Person p;
            while (personIterator.hasNext()) {
                p = (Person) personIterator.next();
                csvPrinter.printRecord(p.getName(), p.getEmail(), p.getPhone(), p.getAddress());
            }

            csvPrinter.flush();
            csvPrinter.close();

        } catch (Exception e) {
            throw new CommandException("Failed in exporting Persons.\n"
                    + e.getStackTrace());
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Returns a CSVPrinter object which can be used to write contacts to
     *
     * @return gives the csv file to write to. The exported contacts will be in this file
     * @throws IOException if cannot write create CSVPrinter to write to 'writeToPath'
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
        Path defaultPath = FileSystems.getDefault().getPath("data" + FS + "exportToExisting.csv");
        return defaultPath;
    }

    public Path getWriteToPath() {
        return writeToPath;
    }
}
//@@author
