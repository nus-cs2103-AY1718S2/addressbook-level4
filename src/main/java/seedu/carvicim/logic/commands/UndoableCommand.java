package seedu.carvicim.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.carvicim.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.carvicim.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.carvicim.logic.commands.exceptions.CommandException;
import seedu.carvicim.model.Carvicim;
import seedu.carvicim.model.ReadOnlyCarvicim;

/**
 * Represents a command which can be undone and redone.
 */
public abstract class UndoableCommand extends Command {
    private ReadOnlyCarvicim previousAddressBook;
    private CommandWords previousCommandWords;

    protected abstract CommandResult executeUndoableCommand() throws CommandException;

    /**
     * Stores the current state of {@code model#carvicim}.
     */
    private void saveAddressBookSnapshot() {
        requireNonNull(model);
        this.previousAddressBook = new Carvicim(model.getCarvicim());
        this.previousCommandWords = new CommandWords(model.getCommandWords());
    }

    /**
     * This method is called before the execution of {@code UndoableCommand}.
     * {@code UndoableCommand}s that require this preprocessing step should override this method.
     */
    protected void preprocessUndoableCommand() throws CommandException {}

    /**
     * Reverts the Carvicim to the state before this command
     * was executed and updates the filtered employee list to
     * show all persons.
     */
    protected final void undo() {
        requireAllNonNull(model, previousAddressBook);
        model.resetData(previousAddressBook, previousCommandWords);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    /**
     * Executes the command and updates the filtered employee
     * list to show all persons.
     */
    protected final void redo() {
        requireNonNull(model);
        try {
            executeUndoableCommand();
        } catch (CommandException ce) {
            throw new AssertionError("The command has been successfully executed previously; "
                    + "it should not fail now");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public final CommandResult execute() throws CommandException {
        saveAddressBookSnapshot();
        preprocessUndoableCommand();
        return executeUndoableCommand();
    }
}
