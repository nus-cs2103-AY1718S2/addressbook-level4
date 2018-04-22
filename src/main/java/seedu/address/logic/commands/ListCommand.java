package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ASSOCIATION;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CLIENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TECHNICIAN;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.ChangeListTabEvent;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_ALIAS = "ls";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists the specified type\n"
            + "Parameters: TYPE (must be either client, pet or vettech)\n"
            + "Example: " + COMMAND_WORD + " pet";

    public static final String MESSAGE_SUCCESS = "Listed all %1$s" + "s";

    private final String targetType;

    public ListCommand(String targetType) {
        this.targetType = targetType;
    }

    //@@author purplepers0n
    @Override
    public CommandResult execute() throws CommandException {

        switch (targetType) {
        case "client":
            model.updateFilteredClientList(PREDICATE_SHOW_ALL_CLIENTS);
            EventsCenter.getInstance().post(new ChangeListTabEvent(0));
            break;

        case "pet":
            model.updateFilteredClientOwnPetAssocation(PREDICATE_SHOW_ALL_ASSOCIATION);
            EventsCenter.getInstance().post(new ChangeListTabEvent(1));
            break;

        case "vettech":
            model.updateFilteredVetTechnicianList(PREDICATE_SHOW_ALL_TECHNICIAN);
            EventsCenter.getInstance().post(new ChangeListTabEvent(2));
            break;

        default:
            throw new CommandException(Messages.MESSAGE_INVALID_COMMAND_FORMAT);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, targetType));
    }
}
