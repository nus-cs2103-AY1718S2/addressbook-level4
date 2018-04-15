package seedu.progresschecker.logic;

import javafx.collections.ObservableList;
import seedu.progresschecker.logic.commands.CommandResult;
import seedu.progresschecker.logic.commands.exceptions.CommandException;
import seedu.progresschecker.logic.parser.exceptions.ParseException;
import seedu.progresschecker.model.exercise.Exercise;
import seedu.progresschecker.model.issues.Issue;
import seedu.progresschecker.model.person.Person;

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

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered list of exercises */
    ObservableList<Exercise> getFilteredExerciseList();

    /** Returns an unmodifiable view of the filtered list of issue */
    ObservableList<Issue> getFilteredIssueList();

    /** Returns the list of input entered by the user, encapsulated in a {@code ListElementPointer} object */
    ListElementPointer getHistorySnapshot();
}
