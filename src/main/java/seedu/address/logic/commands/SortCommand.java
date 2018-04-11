//@@author chenchongsong
package seedu.address.logic.commands;

/**
 * Sort all persons in address book in order.
 * Keywords will be given by user through arguments.
 * Both ascending and descending order is supported.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_SHORTCUT = "so";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all persons in ascendingly or descendingly, "
            + "ordering by the specified keywords.\n"
            + "Parameters: KEYWORD_PREFIX/ORDER ...\n"
            + "Example1: " + COMMAND_WORD + " n/desc\n"
            + "Example2: " + COMMAND_WORD + " m/asc";

    public static final String MESSAGE_SUCCESS = "Sorted all persons";

    public static final String SORT_ORDER_ASCENDING = "asc";
    public static final String SORT_ORDER_DESCENDING = "desc";

    public final String sortKey;
    public final String sortOrder;

    public SortCommand(String key, String order) {
        this.sortKey = key;
        this.sortOrder = order;
    }

    @Override
    public CommandResult execute() {
        model.sortUniquePersonList(sortKey, sortOrder);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
