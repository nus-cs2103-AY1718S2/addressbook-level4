//@@author A0143487X
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
public class ExportListedPersonsCommand extends Command {
    public static final String COMMAND_WORD = "exportListedPersons";
    public static final String COMMAND_ALIAS = "exLP";

    //EDIT START
    public static final String COMMAND_SYNTAX = COMMAND_WORD + " "
            + "FILENAME";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports all listed persons as a CSV file"
            + " with file name FILENAME as specified\n"
            + "Parameters: FILENAME\n"
            + "Example: " + COMMAND_WORD + " FILENAME";

    public static final String MESSAGE_SUCCESS = "Export success! Listed persons exported to";
    //public static final String MESSAGE_FAILURE = "No more commands to redo!";

    //EDIT END

    private final String pathName;

    public ExportListedPersonsCommand(String inputPath) {
        this.pathName = inputPath;
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

        return new CommandResult(MESSAGE_SUCCESS + " " + pathName);
    }
}
