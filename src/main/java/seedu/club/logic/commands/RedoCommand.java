package seedu.club.logic.commands;

import static seedu.club.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.club.logic.CommandHistory;
import seedu.club.logic.UndoRedoStack;
import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.Model;

/**
 * Redo the previously undone command.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final ArrayList<String> COMMAND_ALIASES = new ArrayList<>(
            Arrays.asList(COMMAND_WORD, "r")
    );
    public static final String MESSAGE_SUCCESS = "Redo successful.";
    public static final String MESSAGE_FAILURE = "No more commands to redo.";

    @Override
    public CommandResult execute() throws CommandException {
        requireAllNonNull(model, undoRedoStack);

        if (!undoRedoStack.canRedo()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        undoRedoStack.popRedo().redo();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public void setData(Model model, CommandHistory commandHistory, UndoRedoStack undoRedoStack) {
        this.model = model;
        this.undoRedoStack = undoRedoStack;
    }
}
