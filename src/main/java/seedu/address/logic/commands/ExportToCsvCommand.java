package seedu.address.logic.commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.collections.ObservableList;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;

/**
 * Exports listed person from the address book into file with specified filename.
 */
public class ExportToCsvCommand extends Command {
    public static final String COMMAND_WORD = "exportCSV";
    public static final String COMMAND_ALIAS = "exCSV";

    //EDIT START
    public static final String COMMAND_SYNTAX = COMMAND_WORD + " "
            + "FILENAME";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports all listed persons to "
            + "/data/exported/.. folder as a CSV file"
            + "Parameters: FILENAME\n"
            + "Example: " + COMMAND_WORD + " FILENAME";

    public static final String MESSAGE_SUCCESS = "Export success! Listed persons exported to";
    //public static final String MESSAGE_FAILURE = "No more commands to redo!";

    //EDIT END

    private final String pathName;
    private final int fExistedNameChanged;

    public ExportToCsvCommand(String inputPath, int fExistedNameChanged) {
        this.pathName = inputPath;
        this.fExistedNameChanged = fExistedNameChanged;
    }

    @Override
    public CommandResult execute() throws CommandException {

        //
        try {
            File outputFile = new File(pathName);

            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile.getPath()));

            writer.write("Name,Phone,Email,Address" + System.getProperty("line.separator")); // headings

            //get data

            ObservableList<Person> peopleList = model.getFilteredPersonList();
            int numOfEntries = peopleList.size();
            String entryDetails;

            for (int currIdx = 0; currIdx < numOfEntries; currIdx++) {
                Person currPerson = peopleList.get(currIdx);

                entryDetails = "\"" + currPerson.getName() + "\",\"" + currPerson.getPhone() + "\",\""
                        + currPerson.getEmail() + "\",\"" + currPerson.getAddress() + "\"";
                writer.append(entryDetails + System.getProperty("line.separator"));
            }

            writer.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
        //

        String msgFileNameChanged = "";
        // do action
        if (fExistedNameChanged == 1) {
            msgFileNameChanged = "\n\"(1)\" appended to filename as file with input file name previously existed";
        }

        return new CommandResult(MESSAGE_SUCCESS + " " + pathName + msgFileNameChanged);
    }
}
