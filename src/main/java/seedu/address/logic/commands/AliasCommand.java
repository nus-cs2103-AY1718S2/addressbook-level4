package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.alias.Alias;
import seedu.address.model.alias.exceptions.DuplicateAliasException;

//@@author jingyinno
/**
 * Adds an alias pair to the address book.
 */
public class AliasCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "alias";
    public static final String LIST_ALIAS_COMMAND_WORD = "list";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows list of alias or creates new alias.\n"
            + "Parameters: [COMMAND] [NEW_ALIAS]\n"
            + "Example: " + COMMAND_WORD + " add a";

    public static final String MESSAGE_SUCCESS = "New alias added";
    public static final String MESSAGE_DUPLICATE_ALIAS = "This alias is already used";
    public static final String MESSAGE_INVALID_ALIAS = "Invalid alias word! \n%1$s";
    public static final String MESSAGE_INVALID_ALIAS_DESCRIPTION = "Alias word is a command word. \n"
            + "Please choose another alias";
    public static final String MESSAGE_INVALID_COMMAND = "Invalid command word! \n%1$s";
    public static final String MESSAGE_INVALID_COMMAND_DESCRIPTION = "There is no such command to alias to.";
    private static final List<String> commands = Arrays.asList(AddCommand.COMMAND_WORD, EditCommand.COMMAND_WORD,
            SelectCommand.COMMAND_WORD, DeleteCommand.COMMAND_WORD, ClearCommand.COMMAND_WORD, FindCommand.COMMAND_WORD,
            ListCommand.COMMAND_WORD, HistoryCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD, HelpCommand.COMMAND_WORD,
            UndoCommand.COMMAND_WORD, RedoCommand.COMMAND_WORD, AliasCommand.COMMAND_WORD, ImportCommand.COMMAND_WORD,
            PasswordCommand.COMMAND_WORD, BirthdaysCommand.COMMAND_WORD, ExportCommand.COMMAND_WORD,
            MapCommand.COMMAND_WORD, RemovePasswordCommand.COMMAND_WORD, UnaliasCommand.COMMAND_WORD,
            VacantCommand.COMMAND_WORD);

    private final Alias toAdd;

    /**
     * Creates an AliasCommand to add the specified {@code Alias}
     */
    public AliasCommand(Alias alias) {
        requireNonNull(alias);
        toAdd = alias;
    }

    /**
     * Creates a default AliasCommand
     */
    public AliasCommand() {
        toAdd = null;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        if (toAdd == null) {
            return new CommandResult(model.getAliasList().toString());
        }
        if (!commands.contains(toAdd.getCommand())) {
            throw new CommandException(
                    String.format(AliasCommand.MESSAGE_INVALID_COMMAND,
                            AliasCommand.MESSAGE_INVALID_COMMAND_DESCRIPTION));
        } else if (commands.contains(toAdd.getAlias())) {
            throw new CommandException(
                    String.format(AliasCommand.MESSAGE_INVALID_ALIAS,
                            AliasCommand.MESSAGE_INVALID_ALIAS_DESCRIPTION));
        }

        try {
            model.addAlias(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateAliasException e) {
            throw new CommandException(MESSAGE_DUPLICATE_ALIAS);
        }

    }

    public static List<String> getCommands() {
        return commands;
    }

    @Override
    public boolean equals(Object other) {
        return other == (this)
                || (other instanceof AliasCommand // instanceof handles nulls
                && toAdd.equals(((AliasCommand) other).toAdd));
    }
}
