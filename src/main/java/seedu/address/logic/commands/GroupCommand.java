package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.AddressBook;

/**
 * Clears the address book.
 */
public class GroupCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "group";

    public static final String COMMAND_WORD_ALIAS = "g";

    public static final String MESSAGE_SUCCESS = "Groups listed!";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose group attribute is"
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " Priority";

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        model.resetData(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
