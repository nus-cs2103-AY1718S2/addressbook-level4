package seedu.address.logic.commands;
//@@author jas5469

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.Group;
import seedu.address.model.group.exceptions.DuplicateGroupException;

/**
 * Adds a group to the address book.
 */
public class AddGroupCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addGroup";
    public static final String COMMAND_ALIAS = "aG";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a group to the address book. "
            + "Parameters: "
            + "GROUP NAME "
            + "Example: " + COMMAND_WORD + " "
            + "CS1010 project";

    public static final String MESSAGE_SUCCESS = "New group added: %1$s";
    public static final String MESSAGE_DUPLICATE_GROUP = "This group already exists in the address book";

    private final Group addGroup;

    /**
     * Creates an AddGroupCommand to add the specified {@code Group}
     */
    public AddGroupCommand(Group group) {
        requireNonNull(group);
        addGroup = group;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addGroup(addGroup);
            return new CommandResult(String.format(MESSAGE_SUCCESS, addGroup));
        } catch (DuplicateGroupException e) {
            throw new CommandException(MESSAGE_DUPLICATE_GROUP);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddGroupCommand // instanceof handles nulls
                && addGroup.equals(((AddGroupCommand) other).addGroup));
    }
}
