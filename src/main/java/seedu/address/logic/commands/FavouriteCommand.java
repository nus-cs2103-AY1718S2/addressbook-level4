package seedu.address.logic.commands;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.JsonUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;

/**
 * Add a person to favourites
 */
public class FavouriteCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "fav";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add a person to favourites. "
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Person added favourites: %1$s";

    private static final Logger logger = LogsCenter.getLogger(JsonUtil.class); // To use during initial production of favourite feature

    private final Index targetIndex;

    private Person personToFavourite;

    public FavouriteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        // TODO: implement this method
        logger.info("Executing favourite command in logic");
        return null;
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToFavourite = lastShownList.get(targetIndex.getZeroBased());

        logger.info("Person to favourite: " + personToFavourite.toString());
    }
}
