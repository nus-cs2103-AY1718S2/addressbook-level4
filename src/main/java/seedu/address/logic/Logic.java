package seedu.address.logic;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Parse the command and returns the real time parsing result.
     * @param commandText The command as entered by the user.
     * @return the result of the parsing.
     */
    Command parse(String commandText);

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an active view of the filtered list of persons (With active predicate change listener) */
    ObservableList<Person> getActivePersonList();

    /** Set the currently selected person */
    void setSelectedPerson(Person selectedPerson);

    /** Returns the list of input entered by the user, encapsulated in a {@code ListElementPointer} object */
    ListElementPointer getHistorySnapshot();

    void cleanUnusedResumeAndProfilePic(String addressBookXmlFilePath);
}
