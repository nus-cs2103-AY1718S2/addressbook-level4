package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.pair.Pair;
import seedu.address.model.pair.exceptions.PairNotFoundException;

//@@author alexawangzi
/**
 * Unmatch a pair listed in STUtor
 */
public class UnmatchCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "unmatch";
    public static final String COMMAND_WORD_ALIAS = "um";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unmatch an existing pair.\n "
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_UNMATCH_PAIR_SUCCESS = "Unmatched Pair: %1$s";
    public static final String MESSAGE_NOT_UNMATCHED = "At least one field to unmatch must be provided.";

    private final Index targetIndex;
    private Pair pairToUnmatch;

    /**
     * @param targetIndex of the pair in the filtered pair list to unmatch
     */
    public UnmatchCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(pairToUnmatch);
        try {
            model.deletePair(pairToUnmatch);
        } catch (PairNotFoundException pnfe) {
            throw new AssertionError("The target pair cannot be missing");
        }
        return new CommandResult(String.format(MESSAGE_UNMATCH_PAIR_SUCCESS, pairToUnmatch));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Pair> lastShownList = model.getFilteredPairList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PAIR_DISPLAYED_INDEX);
        }

        pairToUnmatch = lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnmatchCommand // instanceof handles nulls
                && this.targetIndex.equals(((UnmatchCommand) other).targetIndex) // state check
                && Objects.equals(this.pairToUnmatch, ((UnmatchCommand) other).pairToUnmatch));
    }
}
