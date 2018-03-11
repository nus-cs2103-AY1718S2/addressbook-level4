package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_FAVOURITE_PERSONS;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " -f [optional]: "
            + "List all persons. Use -f flag to list favourite persons only.";

    public static final String MESSAGE_SUCCESS_LIST_ALL = "Listed all persons";
    public static final String MESSAGE_SUCCESS_LIST_FAVOURITES = "Listed all favourite persons";

    private final boolean isFavOnly;

    public ListCommand(boolean isFavOnly) {
        this.isFavOnly = isFavOnly;
    }

    @Override
    public CommandResult execute() {
        return isFavOnly ? execute_list_favourites() : execute_list_all();
    }

    /**
     * Execute list command when -f flag is off, i.e. list all persons
     */
    public CommandResult execute_list_all() {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS_LIST_ALL);
    }

    /**
     * Execute list command when -f flag is on, i.e. list favourite persons only
     */
    public CommandResult execute_list_favourites() {
        model.updateFilteredPersonList(PREDICATE_SHOW_FAVOURITE_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS_LIST_FAVOURITES);
    }
}
