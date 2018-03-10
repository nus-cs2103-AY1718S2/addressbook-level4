package seedu.address.logic.commands;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.JsonUtil;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Remove a Person from favourites
 */
public class UnfavouriteCommand extends UndoableCommand{
    public static final String COMMAND_WORD = "unfav";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Remove a person from favourites. "
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Person removed from favourites: %1$s";

    private static final Logger logger = LogsCenter.getLogger(JsonUtil.class); // To use during initial production of favourite feature


    private final Index targetIndex;

    public UnfavouriteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        // TODO: to implement
        logger.info("Executing favourite command in logic");
        return null;
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        // TODO: to implement
    }
}
