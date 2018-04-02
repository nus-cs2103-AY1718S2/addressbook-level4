package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;

public class PrintCommand extends Command {
    public static final String COMMAND_WORD = "print";
    public static final String COMMAND_ALIAS = "p";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Formats and prints out a patient's "
            + "medical records.\n"
            + "Example: " + COMMAND_WORD + " INDEX";

    private final Index targetIndex;

    public PrintCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() {
        return new CommandResult("Print records of patient " + targetIndex.getOneBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PrintCommand // instanceof handles nulls
                && this.targetIndex.equals(((PrintCommand) other).targetIndex)); // state check
    }
}
