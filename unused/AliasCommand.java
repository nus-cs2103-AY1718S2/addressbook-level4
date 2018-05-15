package seedu.address.logic.commands;

import seedu.address.model.Aliases;
import seedu.address.model.UserPrefs;

//@@author fishTT-unused
/**
 * Creates an alias for other commands.
 */
public class AliasCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "alias";
    public static final String MESSAGE_ADD_SUCCESS = "The alias \"%1$s\" now points to \"%2$s\".";
    public static final String MESSAGE_LIST_SUCCESS = "Aliases:\n%1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates an alias for other commands."
            + "Parameters: [ALIAS COMMAND]\n"
            + "Example: " + COMMAND_WORD + " create add\n"
            + "Example: " + COMMAND_WORD + " friends find t/friends\n";

    private final String alias;
    private final String command;

    public AliasCommand() {
        this.alias = null;
        this.command = null;
    }

    public AliasCommand(String alias, String command) {
        this.alias = alias;
        this.command = command;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        Aliases aliases = UserPrefs.getInstance().getAliases();

        if (alias == null && command == null) {
            StringBuilder output = new StringBuilder();

            for (String alias : aliases.getAllAliases()) {
                String command = aliases.getCommand(alias);
                output.append(String.format("%1$s=%2$s\n", alias, command));
            }
            return new CommandResult(String.format(MESSAGE_LIST_SUCCESS, output));
        }

        aliases.addAlias(alias, command);

        return new CommandResult(String.format(MESSAGE_ADD_SUCCESS, alias, command));
    }

    @Override
    protected String undo() {
        return null;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AliasCommand // instanceof handles nulls
                && (this.alias == null || this.alias.equals(((AliasCommand) other).alias)) // state check
                && (this.command == null || this.command.equals(((AliasCommand) other).command))); // state check
    }

}
