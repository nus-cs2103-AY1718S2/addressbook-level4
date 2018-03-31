package seedu.address.logic.commands;

/**
 * Finds and lists all coins in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_ALIAS = "f";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all coins specified by the conditions.\n"
            + "Conditions: e.g. n/name, p/price, t/tags...\n"
            + "Example: " + COMMAND_WORD + " n/BTC AND p/>50";

    //@@author Eldon-Chung
    public FindCommand() {
        ;
    }

    @Override
    public CommandResult execute() {
        return new CommandResult("Command acknowledged! Results are still a WIP");
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof FindCommand); // short circuit if same class
    }
}
