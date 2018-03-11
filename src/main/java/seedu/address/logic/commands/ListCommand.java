package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BOOKS;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowBookListRequestEvent;

/**
 * Lists all books in the book shelf to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all books";


    @Override
    public CommandResult execute() {
        model.updateFilteredBookList(PREDICATE_SHOW_ALL_BOOKS);
        EventsCenter.getInstance().post(new ShowBookListRequestEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
