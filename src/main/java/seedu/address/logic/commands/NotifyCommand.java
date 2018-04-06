//@@author ewaldhew
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.rule.Rule;
import seedu.address.model.rule.exceptions.DuplicateRuleException;

/**
 * Adds a new notification with the specified conditions.
 */
public class NotifyCommand extends Command {

    public static final String COMMAND_WORD = "notify";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new notification to be triggered "
            + "upon the specified rule. Rules are provided in the following format:\n"
            + "Parameters: TARGET OPTION/VALUE [...] \n"
            + "Example: " + COMMAND_WORD + " BTC p/15000";

    public static final String MESSAGE_SUCCESS = "Will notify when: %1$s";
    public static final String MESSAGE_DUPLICATE_RULE = "This notification rule already exists!";

    //private final Predicate predicate;
    private final String value;

    public NotifyCommand(String args) {
        this.value = args;
        //this.predicate = predicate;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        try {
            model.addRule(new Rule(value));
            return new CommandResult(String.format(MESSAGE_SUCCESS, value));
        } catch (DuplicateRuleException e) {
            throw new CommandException(MESSAGE_DUPLICATE_RULE);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NotifyCommand); // instanceof handles nulls
                //&& this.predicate.equals(((NotifyCommand) other).predicate)); // state check

    }
}
