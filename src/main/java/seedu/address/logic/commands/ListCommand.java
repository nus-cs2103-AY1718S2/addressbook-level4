package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_FAVOURITE_STUDENTS;

/**
 * Lists all students in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " -f [optional]: "
            + "List all students. Use -f flag to list favourite students only.";

    public static final String MESSAGE_SUCCESS_LIST_ALL = "Listed all students";
    public static final String MESSAGE_SUCCESS_LIST_FAVOURITES = "Listed all favourite students";

    private final boolean isFavOnly;

    public ListCommand(boolean isFavOnly) {
        this.isFavOnly = isFavOnly;
    }

    @Override
    public CommandResult execute() {
        return isFavOnly ? execute_list_favourites() : execute_list_all();
    }

    /**
     * Execute list command when -f flag is off, i.e. list all students
     */
    public CommandResult execute_list_all() {
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        return new CommandResult(MESSAGE_SUCCESS_LIST_ALL);
    }

    /**
     * Execute list command when -f flag is on, i.e. list favourite students only
     */
    public CommandResult execute_list_favourites() {
        model.updateFilteredStudentList(PREDICATE_SHOW_FAVOURITE_STUDENTS);
        return new CommandResult(MESSAGE_SUCCESS_LIST_FAVOURITES);
    }
}
