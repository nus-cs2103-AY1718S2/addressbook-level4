package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.cinema.Cinema;
import seedu.address.model.cinema.exceptions.CinemaNotFoundException;

/**
 * Deletes a cinema identified using it's last displayed index from the movie planner.
 */
public class DeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete";
    public static final String COMMAND_ALIAS = "d";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the cinema identified by the index number used in the last cinema listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_CINEMA_SUCCESS = "Deleted Cinema: %1$s";

    private final Index targetIndex;

    private Cinema cinemaToDelete;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(cinemaToDelete);
        try {
            model.deleteCinema(cinemaToDelete);
        } catch (CinemaNotFoundException cnfe) {
            throw new AssertionError("The target cinema cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_CINEMA_SUCCESS, cinemaToDelete));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Cinema> lastShownList = model.getFilteredCinemaList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CINEMA_DISPLAYED_INDEX);
        }

        cinemaToDelete = lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteCommand) other).targetIndex) // state check
                && Objects.equals(this.cinemaToDelete, ((DeleteCommand) other).cinemaToDelete));
    }
}
