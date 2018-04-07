package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_COINS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.CoinBook;
import seedu.address.model.ReadOnlyCoinBook;

/**
 * Represents a command which can be undone and redone.
 */
public abstract class UndoableCommand extends Command {
    private ReadOnlyCoinBook previousCoinBook;

    protected abstract CommandResult executeUndoableCommand() throws CommandException;

    /**
     * Stores the current state of {@code model#addressBook}.
     */
    private void saveCoinBookSnapshot() {
        requireNonNull(model);
        this.previousCoinBook = new CoinBook(model.getCoinBook());
    }

    /**
     * This method is called before the execution of {@code UndoableCommand}.
     * {@code UndoableCommand}s that require this preprocessing step should override this method.
     */
    protected void preprocessUndoableCommand() throws CommandException {}

    /**
     * Reverts the CoinBook to the state before this command
     * was executed and updates the filtered coin list to
     * show all coins.
     */
    protected final void undo() {
        requireAllNonNull(model, previousCoinBook);
        model.resetData(previousCoinBook);
        model.updateFilteredCoinList(PREDICATE_SHOW_ALL_COINS);
    }

    /**
     * Executes the command and updates the filtered coin
     * list to show all coins.
     */
    protected final void redo() {
        requireNonNull(model);
        try {
            executeUndoableCommand();
        } catch (CommandException ce) {
            throw new AssertionError("The command has been successfully executed previously; "
                    + "it should not fail now");
        }
        model.updateFilteredCoinList(PREDICATE_SHOW_ALL_COINS);
    }

    @Override
    public final CommandResult execute() throws CommandException {
        saveCoinBookSnapshot();
        preprocessUndoableCommand();
        return executeUndoableCommand();
    }
}
