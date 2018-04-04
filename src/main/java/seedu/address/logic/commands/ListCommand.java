package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BOOKS;

import seedu.address.model.Model;
import seedu.address.model.account.PrivilegeLevel;

/**
 * Lists all books in the catalogue to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all books";

    public static final PrivilegeLevel PRIVILEGE_LEVEL = Model.PRIVILEGE_LEVEL_GUEST;


    @Override
    public CommandResult execute() {
        model.updateFilteredBookList(PREDICATE_SHOW_ALL_BOOKS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public PrivilegeLevel getPrivilegeLevel() {
        return PRIVILEGE_LEVEL;
    }
}
