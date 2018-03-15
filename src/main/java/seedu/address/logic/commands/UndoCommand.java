package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.SwitchToBookListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Undo the previous {@code UndoableCommand}.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";

    @Override
    public CommandResult execute() throws CommandException {
        requireAllNonNull(model, undoStack);

        if (!undoStack.canUndo()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        undoStack.popUndo().undo();
        EventsCenter.getInstance().post(new SwitchToBookListRequestEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public void setData(Model model, CommandHistory commandHistory, UndoStack undoStack) {
        this.model = model;
        this.undoStack = undoStack;
    }
}
