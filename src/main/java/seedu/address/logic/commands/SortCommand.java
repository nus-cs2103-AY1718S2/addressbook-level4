package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_ORDER;

import seedu.address.logic.commands.exceptions.CommandException;

//@@author kexiaowen
/**
 * Sorts and lists all students in HR+ based on name, rating or gpa
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String SORT_ORDER_ASC = "asc";
    public static final String SORT_ORDER_DESC = "desc";
    public static final String SORT_FIELD_GPA = "gpa";
    public static final String SORT_FIELD_NAME = "name";
    public static final String SORT_FIELD_RATING = "rating";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts current list of students in HR+ based on a specified field in descending or ascending order.\n"
            + "Sorting order can only be either desc or asc. The field must be either gpa, name or rating.\n"
            + "Parameters: FIELD (must be either gpa, name or rating) "
            + PREFIX_SORT_ORDER + "SORTING ORDER\n"
            + "Example: " + COMMAND_WORD + " rating "
            + PREFIX_SORT_ORDER + SORT_ORDER_ASC;

    public static final String MESSAGE_INVALID_SORT_ORDER =
            "The sort order can only be asc or desc";
    public static final String MESSAGE_INVALID_SORT_FIELD =
            "The field must be either gpa, name or rating";
    public static final String MESSAGE_SORT_SUCCESS =
            "Sorted students based on %1$s in %2$s order";
    private static final String SORT_ORDER_ASC_FULL = "ascending";
    private static final String SORT_ORDER_DESC_FULL = "descending";
    private static final String SORT_FIELD_GPA_FULL = "gpa";
    private static final String SORT_FIELD_NAME_FULL = "name";
    private static final String SORT_FIELD_RATING_FULL = "rating";

    /**
     * Enumeration of acceptable sort orders
     */
    public enum SortOrder {
        ASC, DESC
    }

    /**
     * Enumeration of acceptable sort fields
     */
    public enum SortField {
        GPA, NAME, RATING
    }

    private final SortOrder sortOrder;
    private final SortField sortField;

    public SortCommand(SortOrder sortOrder, SortField sortField) {
        requireAllNonNull(sortOrder, sortField);
        this.sortOrder = sortOrder;
        this.sortField = sortField;
    }

    /**
     * Returns true if a given string is a valid sort order
     */
    public static boolean isValidSortOrder(String sortOrder) {
        requireNonNull(sortOrder);
        return sortOrder.equals(SORT_ORDER_DESC) || sortOrder.equals(SORT_ORDER_ASC);
    }

    /**
     * Returns true if a given string is a valid sort field
     */
    public static boolean isValidSortField(String sortField) {
        requireNonNull(sortField);
        return sortField.equals(SORT_FIELD_GPA)
                || sortField.equals(SORT_FIELD_NAME)
                || sortField.equals(SORT_FIELD_RATING);
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

    /**
     * Returns the string representation of a {@cod SortField}
     */
    private String getSortFieldString(SortField sortField) {
        switch (sortField) {
        case GPA:
            return SORT_FIELD_GPA_FULL;

        case NAME:
            return SORT_FIELD_NAME_FULL;

        case RATING:
            return SORT_FIELD_RATING_FULL;

        default:
            return null;
        }
    }

    @Override
    public CommandResult execute() throws CommandException {
        switch (sortOrder) {
        case ASC:
            model.sortPersonListAscOrder(sortField);
            break;

        case DESC:
            model.sortPersonListDescOrder(sortField);
            break;

        default:
            throw new CommandException(MESSAGE_INVALID_SORT_ORDER);
        }
        return new CommandResult(String.format(MESSAGE_SORT_SUCCESS,
                getSortFieldString(sortField), getSortOrderString(sortOrder)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && this.sortOrder.equals(((SortCommand) other).sortOrder))
                && this.sortField.equals(((SortCommand) other).sortField); // state check
    }

}
