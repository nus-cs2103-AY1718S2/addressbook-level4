package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_ORDER;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Sorts and lists all students in HR+ based on overall rating
 */
public class RatingSortCommand extends Command {

    public static final String COMMAND_WORD = "rating-sort";
    public static final String SORT_ORDER_ASC = "asc";
    public static final String SORT_ORDER_DESC = "desc";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts current list of students in HR+ based on overall rating in descending or ascending order.\n"
            + "Sorting order can only be either desc or asc.\n"
            + "Parameters: "
            + PREFIX_SORT_ORDER + "SORTING ORDER\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_SORT_ORDER + SORT_ORDER_ASC;

    public static final String MESSAGE_INVALID_SORT_ORDER =
            "The sort order can only be asc or desc";
    public static final String MESSAGE_RATING_SORT_SUCCESS =
            "Sorted all students based on overall rating in %1$s order";
    private static final String SORT_ORDER_ASC_FULL = "ascending";
    private static final String SORT_ORDER_DESC_FULL = "descending";
    /**
     * Enumeration of acceptable sort orders
     */
    public enum SortOrder {
        ASC, DESC
    }

    private final SortOrder sortOrder;

    public RatingSortCommand(SortOrder sortOrder) {
        requireNonNull(sortOrder);
        this.sortOrder = sortOrder;
    }

    /**
     * Returns true if a given string is a valid sort order
     */
    public static boolean isValidSortOrder(String sortOrder) {
        requireNonNull(sortOrder);
        return sortOrder.equals(SORT_ORDER_DESC) || sortOrder.equals(SORT_ORDER_ASC);
    }

    /**
     * Returns the string representation of a {@cod SortOrder}
     */
    private String getSortOrderString(SortOrder sortOrder) {
        switch (sortOrder) {
        case ASC:
            return SORT_ORDER_ASC_FULL;

        case DESC:
            return SORT_ORDER_DESC_FULL;

        default:
            return null;
        }
    }

    @Override
    public CommandResult execute() throws CommandException {
        switch (sortOrder) {
        case ASC:
            model.sortPersonListAscOrder();
            break;

        case DESC:
            model.sortPersonListDescOrder();
            break;

        default:
            throw new CommandException(MESSAGE_INVALID_SORT_ORDER);
        }
        return new CommandResult(String.format(MESSAGE_RATING_SORT_SUCCESS, getSortOrderString(sortOrder)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RatingSortCommand // instanceof handles nulls
                && this.sortOrder.equals(((RatingSortCommand) other).sortOrder)); // state check
    }

}
