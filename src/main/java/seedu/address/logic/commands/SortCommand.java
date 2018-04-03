//@@author luca590

package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.exceptions.DuplicatePersonException;

import static java.util.Objects.requireNonNull;


/**
 * SortCommand is called from AddressBookParser and implements the a sorting mechanism
 * so that the user may sort contacts in the addressBook by name, alphabetically
 */
public class SortCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "sort_by_name";
    public static final String COMMAND_ALIAS = "sort";

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.sortAddressBookAlphabeticallyByName();
        } catch (DuplicatePersonException e) {
            e.printStackTrace();
        }
        return new CommandResult("Contacts successfully sorted alphabetically by name.");
    }
}

//@@author
