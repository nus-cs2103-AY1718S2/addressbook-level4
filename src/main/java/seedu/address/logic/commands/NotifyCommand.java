package seedu.address.logic.commands;

/**
 * Adds a new notification with the specified conditions.
 */
public class NotifyCommand extends Command {

    public static final String COMMAND_WORD = "notify";
    public static final String COMMAND_ALIAS = "n";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new notification to be triggered "
            + "upon the specified rule. Rules are provided in the following format:\n"
            + "Parameters: INDEX/NAME/CODE OPTION/VALUE [...] \n"
            + "Example: " + COMMAND_WORD + " BTC p/15000";

    //private final Predicate predicate;

    public NotifyCommand() {
        //this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        return new CommandResult("Coming soon..");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NotifyCommand); // instanceof handles nulls
                //&& this.predicate.equals(((NotifyCommand) other).predicate)); // state check

    }
}
