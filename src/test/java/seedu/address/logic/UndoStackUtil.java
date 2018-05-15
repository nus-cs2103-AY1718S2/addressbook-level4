package seedu.address.logic;

import java.util.List;

import seedu.address.logic.commands.UndoableCommand;

/**
 * Contains helper methods for testing {@code UndoStack}.
 */
public class UndoStackUtil {
    /**
     * Adds {@code undoElements} into {@code UndoStack#undoStack}.
     * The first element in {@code undoElements} will be the bottommost element
     * in the stack in {@code undoStack}, while the last element will
     * be the topmost element.
     */
    public static UndoStack prepareStack(List<UndoableCommand> undoElements) {
        UndoStack undoStack = new UndoStack();
        undoElements.forEach(undoStack::push);

        return undoStack;
    }
}
