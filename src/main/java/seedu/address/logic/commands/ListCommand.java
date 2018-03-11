package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.JsonUtil;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " -f [optional]: "
            + "List all persons. Use -f flag to list favourite persons only.";

    public static final String MESSAGE_SUCCESS = "Listed all persons";

    private static final Logger logger = LogsCenter.getLogger(JsonUtil.class); // To use during initial production of favourite feature

    private final boolean isFavOnly;

    public ListCommand(boolean isFavOnly) {
        this.isFavOnly = isFavOnly;
        logger.info("New list command created. isFavOnly = " + isFavOnly);
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Execute list command when -f flag is off, i.e. list all persons
     */
    public CommandResult execute_list_all() {
        // TODO: Implement this
        return null;
    }

    /**
     * Execute list command when -f flag is on, i.e. list favourite persons only
     */
    public CommandResult execute_list_favourites() {
        // TODO: Implement this
        return null;
    }
}
