package seedu.address.logic.commands;
import seedu.address.logic.commands.exceptions.CommandException;
import static java.util.Objects.requireNonNull;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;



public class ImportContactsCommand extends Command {

    private String fileAddress;

    public static final String COMMAND_WORD = "import_contacts"; //is there a list of added commands?
    public static final String COMMAND_ALIAS = "ic"; //is there a list of aliases??
    public static final String MESSAGE_SUCCESS = "Contacts successfully imported.\n"; //is there a list of aliases??
    public static final String MESSAGE_FILE_SUCCESS_OPEN = "File was successfully opened.\n";
    public static final String MESSAGE_FILE_FAILED_OPEN = "File failed to open. Please try a different address " +
            "or check if file may be corrupt.\n";
    public static final String MESSAGE_FILE_NOT_FOUND = "No file was found at the address provided. " +
            "Please provide anotehr address.\n";
    public static final String MESSAGE_NO_ADDRESS = "No address was provided, please provide an address to a csv, "
            + "from which to import the file\n";


    public ImportContactsCommand(String _fileAddress) throws IOException {
        requireNonNull(_fileAddress); //This throws IOException if _fileAddress is null
        fileAddress = _fileAddress;
    }

    public CommandResult openFile() throws IOException, CommandException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(fileAddress));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .withIgnoreHeaderCase()
                    .withTrim());
            return new CommandResult(MESSAGE_FILE_SUCCESS_OPEN + "from : " + fileAddress);
        }
        catch (NullPointerException npe) { //file won't open, null ptr
            throw new CommandException(MESSAGE_FILE_FAILED_OPEN);
        }
        catch (FileNotFoundException fnf) {
            throw new CommandException(MESSAGE_FILE_NOT_FOUND);
        }
        catch(IOException ioe) {
            throw new CommandException("IOException thrown in ImportContactsCommand.");
        }
    }


    try (
    Reader reader = Files.newBufferedReader(Paths.get(fileAddress));
    CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
            .withFirstRecordAsHeader()
            .withIgnoreHeaderCase()
            .withTrim());
        ) {
        Iterable<CSVRecord> csvRecords = csvParser.getRecords();

        for (CSVRecord csvRecord : csvRecords) {
            // Accessing values by Header names

            String name = csvRecord.get("Name");
            String email = csvRecord.get("Email");
            String phone = csvRecord.get("Phone");
            String country = csvRecord.get("Country");

            System.out.println("Record No - " + csvRecord.getRecordNumber());
            System.out.println("---------------");
            System.out.println("Name : " + name);
            System.out.println("Email : " + email);
            System.out.println("Phone : " + phone);
            System.out.println("Country : " + country);
            System.out.println("---------------\n\n");
        }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

    }
*/
}

