package seedu.address.logic.commands;

//@@author lohtianwei
import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.exceptions.NoPlayerException;

/**
 * Sorts all players in address book by field. Can be done in asc or desc order.
 */

public class SortCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_ALIAS = "so";
    public static final String BY_ASCENDING = "asc";
    public static final String BY_DESCENDING = "desc";


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts players in address book "
            + "by fields in either ascending or descending order.\n"
            + "Parameters: FIELD ORDER\n"
            + "Accepted fields for sort: name, email, address, rating, jersey, pos\n"
            + "Example: " + COMMAND_WORD
            + " name " + BY_ASCENDING;

    public static final String MESSAGE_PARAMETERS = "FIELD ORDER";

    public static final String MESSAGE_SUCCESS = "Players in address book have been sorted.";
    public static final String MESSAGE_EMPTY_BOOK = "No player(s) to sort.";

    private final String field;
    private final String order;

    public SortCommand(String field, String order) {
        requireNonNull(field);
        requireNonNull(order);

        this.field = field;
        this.order = order;
    }

    public String getField() {
        return this.field;
    }

    public String getOrder() {
        return this.order;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.sortPlayers(getField(), getOrder());
        } catch (NoPlayerException npe) {
            throw new CommandException(MESSAGE_EMPTY_BOOK);
        }
        return new CommandResult(MESSAGE_SUCCESS);

    }
}
