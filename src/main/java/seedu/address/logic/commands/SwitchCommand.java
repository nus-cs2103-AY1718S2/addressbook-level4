//@@author jaronchan
package seedu.address.logic.commands;

/**
 * Switches user interface to the feature requested.
 */
public class SwitchCommand extends Command {

    public static final String COMMAND_WORD = "switch";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Switches to the user interface feature identified by the user.\n"
            + "Parameters: FEATURE (must be either \"details\", \"calendar\" or \"scheduler\")\n"
            + "Example: " + COMMAND_WORD + " calendar";

    public static final String MESSAGE_SUCCESS = "Switched to %1$s tab";

    private final String featureTarget;

    public SwitchCommand(String featureTarget) {
        this.featureTarget = featureTarget;
    }

    @Override
    public CommandResult execute() {

        return new CommandResult(String.format(MESSAGE_SUCCESS, featureTarget));
    }

}
